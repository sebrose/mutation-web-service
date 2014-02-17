package checkout;


import cucumber.api.java.en.Then;
import support.KnowsTheDomain;

import static org.junit.Assert.assertEquals;

public class GeneralSteps {
    private KnowsTheDomain helper;

    public GeneralSteps(KnowsTheDomain helper){
        this.helper = helper;
    }

    @Then("^I receive a success confirmation$")
    public void I_receive_a_success_confirmation() throws Throwable {
        assertEquals(201, helper.getHttpResponseCode());
    }

    @Then("^I receive a failure notification$")
    public void I_receive_a_failure_notification() throws Throwable {
        assertEquals(400, helper.getHttpResponseCode());
    }

}
