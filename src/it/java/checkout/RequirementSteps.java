package checkout;

import com.google.gson.Gson;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import support.KnowsTheDomain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class RequirementSteps {
    private static class MyResults {
        String requirements;
        String errorMessage;

        @Override
        public String toString() {

            return String.format("Error: %s, Requirements: %s}", errorMessage, requirements);
        }
    }

    private final KnowsTheDomain helper;
    private Gson json = new Gson();

    public RequirementSteps(KnowsTheDomain helper){
        this.helper = helper;
    }

    @When("^I ask for requirements$")
    public void I_ask_for_requirements() throws Throwable {
        helper.requestRequirements();
    }

    @And("^some requirements$")
    public void some_requirements() throws Throwable {
        MyResults results = json.fromJson(helper.getJsonResponse(), MyResults.class);
        assertNotNull(results.requirements);
        assertTrue(results.requirements.length() > 0);
    }
}
