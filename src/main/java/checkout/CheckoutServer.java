package checkout;

import checkout.data.DatabaseConnectionInitialiser;
import checkout.data.RequirementsGenerator;
import com.google.gson.Gson;
import org.javalite.activejdbc.Base;
import org.webbitserver.*;
import org.webbitserver.netty.NettyWebServer;
import org.webbitserver.rest.Rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class CheckoutServer {
    private final WebServer webServer;
    private final Rest rest;
    private Gson json = new Gson();
    private static final PersistentEntity<Integer, Integer> roundEntity = new PersistentEntity<Integer, Integer>() {
        @Override
            public void update(Integer roundNumber, Integer pointsAvailable) {
            Round round = Round.findFirst("number = ?", roundNumber);
            if (round == null) {
                round = new Round(roundNumber, pointsAvailable);
            } else {
                round.setPoints(pointsAvailable);
            }
            round.saveIt();
        }

        @Override
        public Integer get(Integer roundNumber) {
            Round round = Round.findFirst("number = ?", roundNumber);
            return (round == null ? 99999 : round.getPoints());
        }
    };


    public static class ErrorResponse {
        String errorMessage;

        public ErrorResponse(String message){
            errorMessage = message;
        }
    }

    public static class BatchPrice {
        public BatchTotals batch = new BatchTotals();
    }

    public static class BatchTotals {
        public Map<Integer, Money> baskets = new HashMap<Integer, Money>();
    }


    public static class BatchTotalsDataOut {
        BatchPriceComparisonResult batch;
        String errorMessage;
    }

    public static class BatchPriceComparisonResult {
        Map<Integer, String> incorrectBaskets = new HashMap<Integer, String>();

        public boolean allResultsCorrect() {
             return incorrectBaskets.isEmpty();
        }

        public String getResult(Integer basketId) {
            return incorrectBaskets.get(basketId);
        }
    }

    public static class RegistrationDataIn {
        String name;
    }

    public static class RegistrationDataOut {
        long id;
        String acceptedName;
        String errorMessage;
    }

    public static class BatchDataOut {
        Batch batch;
        String errorMessage;
    }

    public static class PriceListDataOut {
        PriceList priceList;
        String errorMessage;
    }

    public static class RequirementsOut {
        String requirements;
        String errorMessage;
    }



    public class JsonProcessorResultWrapper {
        public int httpStatus;
        public String jsonResponse;

        public JsonProcessorResultWrapper(int httpStatus, String jsonResponse){
            this.httpStatus = httpStatus;
            this.jsonResponse = jsonResponse;
        }
    }

    public interface JsonProcessor {
        JsonProcessorResultWrapper execute(HttpRequest req);
    }

    public CheckoutServer(int port) {
        new DatabaseConnectionInitialiser();

        Scoring.initialise(roundEntity);

        webServer  = new NettyWebServer(port);
        rest = new Rest(webServer);

        addPutHandler("/Checkout/Team", new JsonProcessor() {
            @Override
            public JsonProcessorResultWrapper execute(HttpRequest reqJson) {
                RegistrationDataIn body = json.fromJson(reqJson.body(), RegistrationDataIn.class);
                RegistrationDataOut out = new RegistrationDataOut();
                Team team = TeamFactory.create(body.name);
                out.id = ((Long) team.getId());
                out.acceptedName = team.getName();
                return new JsonProcessorResultWrapper(201, json.toJson(out));
            }
        });

        addGetHandler("/Checkout/Requirements/{teamName}", new JsonProcessor() {
            @Override
            public JsonProcessorResultWrapper execute(HttpRequest req) {
                RequirementsOut out = new RequirementsOut();
                String teamName = Rest.param(req, "teamName");
                Team team = Team.findFirst("name=?", teamName);
                if (team == null) {
                    throw new IllegalArgumentException(String.format("Unregistered team name supplied '%s'", teamName));
                }

                team.refresh();
                out.requirements = RequirementsGenerator.forRound(team.getCurrentRound());
                return new JsonProcessorResultWrapper(200, json.toJson(out));
            }
        });

        addGetHandler("/Checkout/Batch/{teamName}", new JsonProcessor() {
            @Override
            public JsonProcessorResultWrapper execute(HttpRequest req) {
                BatchDataOut out = new BatchDataOut();
                String teamName = Rest.param(req, "teamName");
                Team team = Team.findFirst("name=?", teamName);
                if (team == null) {
                    throw new IllegalArgumentException(String.format("Unregistered team name supplied '%s'", teamName));
                }

                team.refresh();
                out.batch = BatchFactory.create(team.getCurrentRound());
                return new JsonProcessorResultWrapper(200, json.toJson(out));
            }
        });

        addGetHandler("/Checkout/Score/{teamName}", new JsonProcessor() {
            @Override
            public JsonProcessorResultWrapper execute(HttpRequest req) {
                String teamName = Rest.param(req, "teamName");
                Team team = Team.findFirst("name=?", teamName);
                if (team == null) {
                    throw new IllegalArgumentException(String.format("Unregistered team name supplied '%s'", teamName));
                }

                team.refresh();
                return new JsonProcessorResultWrapper(200, String.format("{\"score\": %d}", team.getScore()));
            }
        });

        addGetHandler("/Checkout/PriceList/{teamName}", new JsonProcessor() {
            @Override
            public JsonProcessorResultWrapper execute(HttpRequest req) {
                String teamName = Rest.param(req, "teamName");
                Team team = Team.findFirst("name=?", teamName);
                if (team == null) {
                    throw new IllegalArgumentException(String.format("Unregistered team name supplied '%s'", teamName));
                }

                team.refresh();
                PriceListDataOut out = new PriceListDataOut();
                out.priceList = PriceListFactory.create(team.getCurrentRound());
                return new JsonProcessorResultWrapper(200, json.toJson(out));
            }
        });


        addPutHandler("/Checkout/Batch/{teamName}", new JsonProcessor() {
            @Override
            public JsonProcessorResultWrapper execute(HttpRequest req) {

                String teamName = Rest.param(req, "teamName");
                Team team = Team.findFirst("name=?", teamName);
                if (team == null) {
                    throw new IllegalArgumentException(String.format("Unregistered team name supplied '%s'", teamName));
                }

                int pointsScored = Scoring.INCORRECT_RESPONSE_POINTS;

                try {
                    int currentRound = team.getCurrentRound();

                    BatchPrice submittedTotals = json.fromJson(req.body(), BatchPrice.class);
                    Batch batch = BatchFactory.create(currentRound);
                    PriceList priceList = PriceListFactory.create(currentRound);
                    BatchPrice expectedTotals = BatchPriceCalculator.calculate(batch, priceList);

                    BatchTotalsDataOut out = new BatchTotalsDataOut();
                    BatchPriceComparisonResult verification = checkout.BatchPriceComparator.check(expectedTotals, submittedTotals);
                    out.batch = verification;

                    int responseStatus = 400;
                    if (verification.allResultsCorrect()) {
                        responseStatus = 201;
                        pointsScored = Scoring.getScoreForRound(currentRound, roundEntity);
                        team.setCurrentRound(currentRound + 1);
                    }
                    return new JsonProcessorResultWrapper(responseStatus, json.toJson(out));
                } finally {
                    team.addPoints(pointsScored);
                    team.saveIt();
                }
            }
        });
    }

    private void addPutHandler(String path, final JsonProcessor operation) {
        rest.PUT(path, new HttpHandler() {
            @Override
            public void handleHttpRequest(HttpRequest req, HttpResponse res, HttpControl ctl) {
                new DatabaseConnectionInitialiser();

                try {
                    JsonProcessorResultWrapper response = operation.execute(req);
                    res
                            .status(response.httpStatus)
                            .content(response.jsonResponse)
                            .end();
                }
                catch (Exception ie) {
                    ErrorResponse out = new ErrorResponse(ie.getMessage());
                    res
                            .status(400)
                            .content(json.toJson(out))
                            .end();
                }
            }
        });
    }

    private void addGetHandler(String path, final JsonProcessor operation) {
        rest.GET(path, new HttpHandler() {
            @Override
            public void handleHttpRequest(HttpRequest req, HttpResponse res, HttpControl ctl) {
                new DatabaseConnectionInitialiser();

                try {
                    JsonProcessorResultWrapper response = operation.execute(req);
                    res
                            .status(response.httpStatus)
                            .content(response.jsonResponse)
                            .end();
                } catch (Exception ie) {
                    ErrorResponse out = new ErrorResponse(ie.getMessage());
                    res
                            .status(400)
                            .content(json.toJson(out))
                            .end();
                }
            }
        });
    }

    public void start() throws IOException, InterruptedException, ExecutionException {
        webServer.start().get();
    }

    public void stop() throws IOException, InterruptedException, ExecutionException {
        webServer.stop();
    }

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException  {
        Base.open(
                DatabaseCredentials.driver,
                DatabaseCredentials.connectionString,
                DatabaseCredentials.user,
                DatabaseCredentials.password);

        new CheckoutServer(9988).start();
    }
}
