package checkout;


import cucumber.api.java.en.Then;
import support.KnowsTheDomain;

import static org.junit.Assert.assertEquals;

public class GeneralSteps {
    private KnowsTheDomain helper;

    public GeneralSteps(KnowsTheDomain helper){
        this.helper = helper;
    }

    @Then("^I receive an OK response$")
    public void I_receive_a_success_confirmation() throws Throwable {
        assertEquals(200, helper.getHttpResponseCode());
    }

    @Then("^I receive a CREATED response$")
    public void I_receive_a_created_confirmation() throws Throwable {
        assertEquals(201, helper.getHttpResponseCode());
    }

    @Then("^I receive an ERROR response$")
    public void I_receive_a_failure_notification() throws Throwable {
        assertEquals(400, helper.getHttpResponseCode());
    }

}
