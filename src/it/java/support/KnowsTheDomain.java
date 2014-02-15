package support;

import checkout.ServerHooks;
import checkout.Team;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class KnowsTheDomain{

    public static class ResponseWrapper {
        public Integer httpResponseCode;
        public String jsonBody;

        public ResponseWrapper(Integer httpResponseCode, String jsonBody){
            this.httpResponseCode = httpResponseCode;
            this.jsonBody = jsonBody;
        }
    }

    private Team team;

    public KnowsTheDomain() {
    }

    public void createTeam(String testTeam, int currentRound) {
        team = new Team(testTeam);
        team.setCurrentRound(currentRound);
        team.saveIt();
    }

    public String getTeamName() {
        if (team == null){
            return "UnknownTeam";
        }

        return team.getName();
    }

    public int getTeamRound() {
        if (team == null){
            return -1;
        }

        team.refresh();
        return team.getCurrentRound();

    }

    public ResponseWrapper submitTotals(String input) {
        try {

            Client client = Client.create();

            WebResource webResource = client
                    .resource("http://localhost:"+ ServerHooks.PORT +"/Checkout/Batch/"  + getTeamName() );

            ClientResponse response = webResource.type("application/json")
                    .put(ClientResponse.class, input);

            int httpReturnCode = response.getStatus();

            String jsonString = response.getEntity(String.class);
            if (jsonString != null){
                System.out.println(String.format("JSON: %s", jsonString));
            }

            return new ResponseWrapper(httpReturnCode, jsonString);
        }
        catch (RuntimeException r) {
            throw r;
        }
        catch (Exception e) {

            e.printStackTrace();

            return new ResponseWrapper(500, e.getMessage());
        }
    }
}

