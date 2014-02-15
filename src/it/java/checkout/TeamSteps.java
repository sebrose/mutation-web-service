package checkout;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import support.KnowsTheDomain;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TeamSteps {
    private KnowsTheDomain helper;
    private Gson json = new Gson();
    private String requestedName;
    
    private static class RegistrationData {
        public long id;
        public String acceptedName;
        public String errorMessage;
    }
    
    RegistrationData registrationData;
    int httpReturnCode;
    
    public TeamSteps(KnowsTheDomain helper) {
        this.helper = helper;
    }

    @Given("^my team is unregistered$")
    public void my_team_is_unregistered() throws Throwable {
        // Do nothing
    }

    @Given("^my team is in round (\\d+)$")
    public void my_team_is_in_round(int currentRound) throws Throwable {
        helper.createTeam("TestTeam", currentRound);
    }

    @Given("^team \"([^\"]*)\" is already registered$")
    public void team_is_already_registered(String name) throws Throwable {
        helper.createTeam(name, 0);
    }
    
    @Given("^my chosen team name is \"([^\"]*)\"$")
    public void my_chosen_team_name_is(String teamName) throws Throwable {
        requestedName = teamName;
    }

    @Given("^I choose a really long name$")
    public void my_chosen_team_name_is_really_long() throws Throwable {
        int n = 300;
        char[] chars = new char[n];
        Arrays.fill(chars, 'c');
        requestedName = new String(chars);
    }
    
    @When("^I register$")
    public void I_register() throws Throwable {
        try {

            Client client = Client.create();

            WebResource webResource = client
                .resource("http://localhost:"+ ServerHooks.PORT +"/Checkout/Team");

            String input = "{\"name\":\"" + requestedName + "\"}";
            ClientResponse response = webResource.type("application/json")
                       .put(ClientResponse.class, input);

            httpReturnCode = response.getStatus();

            String jsonString = response.getEntity(String.class);
            if (jsonString != null){
                registrationData = json.fromJson(jsonString, RegistrationData.class);
            }
        }
        catch (RuntimeException r) {
            throw r;
        } 
        catch (Exception e) {

            e.printStackTrace();

        }
    }

    @Then("^my choice of name should be confirmed$")
    public void my_registration_should_be_successful() throws Throwable {
        assertEquals(201, httpReturnCode);
        assertEquals(requestedName, registrationData.acceptedName);
    }

    @Then("^my choice of name should be rejected$")
    public void my_registration_should_be_rejected() throws Throwable {
        assertEquals(400, httpReturnCode);
        assertTrue(registrationData.errorMessage.length() > 0);
    }

    @Then("^my team is now in round (\\d+)$")
    public void my_team_is_now_in_round(int round) throws Throwable {
        assertEquals(round, helper.getTeamRound());
    }

    @Then("^my team is still in round (\\d+)$")
    public void my_team_is_still_in_round(int round) throws Throwable {
        assertEquals(round, helper.getTeamRound());
    }


}