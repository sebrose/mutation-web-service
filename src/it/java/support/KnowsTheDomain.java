package support;

import checkout.*;
import checkout.data.BatchBuilder;
import checkout.data.PriceListBuilder;
import checkout.data.SpecialOfferCollectionBuilder;
import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class KnowsTheDomain {

    public static class ResponseWrapper {
        public Integer httpResponseCode;
        public String jsonBody;

        public ResponseWrapper(Integer httpResponseCode, String jsonBody) {
            this.httpResponseCode = httpResponseCode;
            this.jsonBody = jsonBody;
        }
    }

    private Team team;
    private ResponseWrapper response;
    private Gson json = new Gson();

    public KnowsTheDomain() {
    }

    public void storeResponse(int status, String json) {
        response = new ResponseWrapper(status, json);
    }

    public void storeResponse(ResponseWrapper response) {
        this.response = response;
    }

    public int getHttpResponseCode() {
        return response.httpResponseCode;
    }

    public String getJsonResponse() {
        return response.jsonBody;
    }

    public void receiveSuccessfulTotalsForRound(int roundNumber) {
        final PersistentEntity<Integer, Integer> roundEntity = new PersistentEntity<Integer, Integer>() {
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

        Scoring.getScoreForRound(roundNumber, roundEntity);
    }

    public void createTeam(String testTeam, int currentRound) {
        team = new Team(testTeam);
        team.setCurrentRound(currentRound);
        team.saveIt();
    }

    public void registeredTeamIs(String name) {
        team = Team.findFirst("Name = ?", name);
    }

    public String getTeamName() {
        if (team == null) {
            return "UnknownTeam";
        }

        return team.getName();
    }

    public int getTeamRound() {
        if (team == null) {
            return -1;
        }

        team.refresh();
        return team.getCurrentRound();

    }

    public void requestBatch() {
        try {

            Client client = Client.create();

            WebResource webResource = client
                    .resource("http://localhost:" + ServerHooks.PORT + "/Checkout/Batch/" + getTeamName());

            ClientResponse response = webResource.type("application/json")
                    .get(ClientResponse.class);

            storeResponse(response.getStatus(), response.getEntity(String.class));
        } catch (RuntimeException r) {
            throw r;
        } catch (Exception e) {
            System.out.println("Exception caught");
            e.printStackTrace();

        }
    }

    public void requestRequirements() {
        try {

            Client client = Client.create();

            WebResource webResource = client
                    .resource("http://localhost:" + ServerHooks.PORT + "/Checkout/Requirements/" + getTeamName());

            ClientResponse response = webResource.type("application/json")
                    .get(ClientResponse.class);

            storeResponse(response.getStatus(), response.getEntity(String.class));
        } catch (RuntimeException r) {
            throw r;
        } catch (Exception e) {
            System.out.println("Exception caught");
            e.printStackTrace();

        }
    }

    public void requestPriceList() {
        try {

            Client client = Client.create();

            WebResource webResource = client
                    .resource("http://localhost:" + ServerHooks.PORT + "/Checkout/PriceList/" + getTeamName());

            ClientResponse response = webResource.type("application/json")
                    .get(ClientResponse.class);

            storeResponse(response.getStatus(), response.getEntity(String.class));
        } catch (RuntimeException r) {
            throw r;
        } catch (Exception e) {
            System.out.println("Exception caught");
            e.printStackTrace();

        }
    }

    public void submitTotals(String input) {
        try {

            Client client = Client.create();

            WebResource webResource = client
                    .resource("http://localhost:" + ServerHooks.PORT + "/Checkout/Batch/" + getTeamName());

            ClientResponse response = webResource.type("application/json")
                    .put(ClientResponse.class, input);

            int httpReturnCode = response.getStatus();

            String jsonString = response.getEntity(String.class);

            storeResponse(httpReturnCode, jsonString);
        } catch (RuntimeException r) {
            throw r;
        } catch (Exception e) {

            e.printStackTrace();

            storeResponse(500, e.getMessage());
        }
    }

    public ResponseWrapper registerTeam(String requestedName) {
        try {

            Client client = Client.create();

            WebResource webResource = client
                    .resource("http://localhost:" + ServerHooks.PORT + "/Checkout/Team");

            String input = "{\"name\":\"" + requestedName + "\"}";
            ClientResponse clientResponse = webResource.type("application/json")
                    .put(ClientResponse.class, input);

            String jsonString = clientResponse.getEntity(String.class);
            storeResponse(clientResponse.getStatus(), jsonString);

            return response;
        } catch (RuntimeException r) {
            throw r;
        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;
    }

    public void setSimplePriceList() {
        try {

            Client client = Client.create();

            WebResource webResource = client
                    .resource("http://localhost:" + ServerHooks.PORT + "/TestCheckout/PriceList/0");

            PriceListBuilder builder = new PriceListBuilder();
            builder.addEntry("foobar").withUnitPrice("0.50");
            String input = json.toJson(builder.build());

            ClientResponse clientResponse = webResource.type("application/json")
                    .put(ClientResponse.class, input);
        } catch (RuntimeException r) {
            throw r;
        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    public void setSimpleBatch() {
        try {

            Client client = Client.create();

            WebResource webResource = client
                    .resource("http://localhost:" + ServerHooks.PORT + "/TestCheckout/Batch/0");

            BatchBuilder builder = new BatchBuilder();
            builder.addNewBasket().withItem("foobar").withQuantity(2);
            String input = json.toJson(builder.build());

            ClientResponse clientResponse = webResource.type("application/json")
                    .put(ClientResponse.class, input);
        } catch (RuntimeException r) {
            throw r;
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void setSimpleData() {
        setSimpleBatch();
        setSimplePriceList();
    }

    public void setBOGOFData() {
        setSimpleBatch();
        setSimplePriceList();
        setBOGOFSpecialOffers();
    }

    public void setBOGOFSpecialOffers() {
        try {

            Client client = Client.create();

            WebResource webResource = client
                    .resource("http://localhost:" + ServerHooks.PORT + "/TestCheckout/SpecialOffers/0");

            SpecialOfferCollectionBuilder builder = new SpecialOfferCollectionBuilder();
            builder.addOffer(BogofTracker.OFFER_CODE).withDescription("Buy one get one free").forItemCode("foobar");
            String input = json.toJson(builder.build());

            ClientResponse clientResponse = webResource.type("application/json")
                    .put(ClientResponse.class, input);
        } catch (RuntimeException r) {
            throw r;
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

}

