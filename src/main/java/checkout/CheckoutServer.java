package checkout;

import checkout.data.DatabaseConnectionInitialiser;
import checkout.data.PriceList;
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

    public CheckoutServer(int port) {
        webServer  = new NettyWebServer(port);
        rest = new Rest(webServer);
        
        rest.PUT("/Checkout/Team", new HttpHandler() {
            @Override
            public void handleHttpRequest(HttpRequest req, HttpResponse res, HttpControl ctl) {
                new DatabaseConnectionInitialiser();

                String jsonBody = req.body();
                RegistrationDataIn body = json.fromJson(jsonBody, RegistrationDataIn.class);
                
                RegistrationDataOut out = new RegistrationDataOut();
                try {
                    Team team = TeamFactory.create(body.name);
                    out.id = ((Long)team.getId());
                    out.acceptedName = team.getName();

                    res
                        .status(201)
                        .content(json.toJson(out))
                        .end();
                }
                catch (IllegalArgumentException ie) {
                    out.errorMessage = ie.getMessage();
                    res
                        .status(400)
                        .content(json.toJson(out))
                        .end();
                }
            }
        });

        rest.GET("/Checkout/Batch/{teamName}", new HttpHandler() {
            @Override
            public void handleHttpRequest(HttpRequest req, HttpResponse res, HttpControl ctl) {
                new DatabaseConnectionInitialiser();

                BatchDataOut out = new BatchDataOut();
                try {
                    String teamName = Rest.param(req, "teamName");
                    Team team = Team.findFirst("name=?", teamName);
                    if (team == null) {
                        throw new IllegalArgumentException(String.format("Unregistered team name supplied '%s'", teamName));
                    }

                    out.batch = BatchFactory.create(team.getCurrentRound());

                    res
                            .status(200)
                            .content(json.toJson(out))
                            .end();
                } catch (IllegalArgumentException ie) {
                    out.errorMessage = ie.getMessage();
                    res
                            .status(400)
                            .content(json.toJson(out))
                            .end();
                }
            }
        });

        rest.PUT("/Checkout/Batch/{teamName}", new HttpHandler() {
            @Override
            public void handleHttpRequest(HttpRequest req, HttpResponse res, HttpControl ctl) {
                new DatabaseConnectionInitialiser();

                String jsonBody = req.body();
                System.out.println(String.format("RECEIVED: %s", jsonBody));
                BatchPrice submittedTotals = json.fromJson(jsonBody, BatchPrice.class);

                BatchTotalsDataOut out = new BatchTotalsDataOut();
                try {
                    String teamName = Rest.param(req, "teamName");
                    Team team = Team.findFirst("name=?", teamName);
                    if (team == null) {
                        throw new IllegalArgumentException(String.format("Unregistered team name supplied '%s'", teamName));
                    }

                    Batch batch = BatchFactory.create(team.getCurrentRound());
                    PriceList priceList = PriceListFactory.create(team.getCurrentRound());
                    BatchPrice expectedTotals = BatchPriceCalculator.calculate(batch,priceList);

                    BatchPriceComparisonResult verification = checkout.BatchPriceComparator.check(expectedTotals, submittedTotals);
                    out.batch = verification;

                    int responseStatus = 400;
                    if (verification.allResultsCorrect()) {
                        responseStatus = 201;
                        team.setCurrentRound(team.getCurrentRound()+1);
                        team.saveIt();
                    }

                    res
                            .status(responseStatus)
                            .content(json.toJson(out))
                            .end();
                } catch (IllegalArgumentException ie) {
                    out.errorMessage = ie.getMessage();
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
