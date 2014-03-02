package checkout;

import checkout.data.DatabaseConnectionInitialiser;
import checkout.handlers.*;
import com.google.gson.Gson;
import org.javalite.activejdbc.Base;
import org.webbitserver.*;
import org.webbitserver.netty.NettyWebServer;
import org.webbitserver.rest.Rest;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class CheckoutServer {
    private final WebServer webServer;
    private final Rest rest;
    private Gson json = new Gson();
    private static final RoundEntity roundEntity = new RoundEntity();


    public CheckoutServer(int port) {
        new DatabaseConnectionInitialiser();

        Scoring.initialise(roundEntity);

        webServer  = new NettyWebServer(port);
        rest = new Rest(webServer);

        MyDataSource dataSource = new FileDataSource();

        addPutHandler("/Checkout/Team", new TeamRegistrationHandler(json));

        addGetHandler("/Checkout/Requirements/{teamName}", new RequirementsRequestHandler(json, new MyReader("requirements.txt", dataSource)));

        addGetHandler("/Checkout/Batch/{teamName}", new BatchRequestHandler(json, new MyReader("batch.json", dataSource)));

        addGetHandler("/Checkout/Score/{teamName}", new ScoreRequestHandler(json));

        addGetHandler("/Checkout/PriceList/{teamName}", new PricelistRequestHandler(json, new MyReader("price_list.json", dataSource)));

        addPutHandler("/Checkout/Batch/{teamName}", new BatchSubmissionHandler(json, roundEntity,
                new MyReader("batch.json", dataSource),
                new MyReader("price_list.json", dataSource)));
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
                            .header("Content-Type", "application/json")
                            .content(response.jsonResponse)
                            .end();
                }
                catch (Exception ie) {
                    ErrorResponse out = new ErrorResponse(ie.getMessage());
                    res
                            .status(400)
                            .header("Content-Type", "application/json")
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
                            .header("Content-Type", "application/json")
                            .content(response.jsonResponse)
                            .end();
                } catch (Exception ie) {
                    ErrorResponse out = new ErrorResponse(ie.getMessage());
                    res
                            .status(400)
                            .header("Content-Type", "application/json")
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
