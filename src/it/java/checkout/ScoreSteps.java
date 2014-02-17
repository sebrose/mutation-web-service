package checkout;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import support.KnowsTheDomain;

import static org.junit.Assert.assertEquals;

public class ScoreSteps {
    private final KnowsTheDomain helper;
    private Gson json = new Gson();

    private class MyScore {
        int score;
    }

    public ScoreSteps(KnowsTheDomain helper){
        this.helper = helper;
    }

    @When("^I retrieve my score$")
    public void I_retrieve_my_score() throws Throwable {
        try {

            Client client = Client.create();

            WebResource webResource = client
                    .resource("http://localhost:" + ServerHooks.PORT + "/Checkout/Score/" + helper.getTeamName());

            ClientResponse response = webResource.type("application/json")
                    .get(ClientResponse.class);

            helper.storeResponse(response.getStatus(), response.getEntity(String.class));
        }
        catch (RuntimeException r) {
            throw r;
        }
        catch (Exception e) {
            System.out.println("Exception caught");
            e.printStackTrace();

        }
    }

    @When("^the server is restarted$")
    public void the_server_is_restarted() throws Throwable {
        Scoring.reset();
    }

    @And("^another team has already submitted correct round (\\d+) totals$")
    public void another_team_has_already_submitted_correct_round_totals(int round) throws Throwable {
        helper.receiveSuccessfulTotalsForRound(round);
    }

    @Then("^my score is (-?\\d+) points$")
    public void my_score_is_points(int expectedScore) throws Throwable {
        MyScore actual = json.fromJson(helper.getJsonResponse(), MyScore.class);

        assertEquals(expectedScore, actual.score);
    }
}
