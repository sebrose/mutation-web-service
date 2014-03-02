package checkout;


import checkout.handlers.ErrorResponse;
import com.google.gson.Gson;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import support.KnowsTheDomain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GeneralSteps {
    private KnowsTheDomain helper;
    private Gson json = new Gson();

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

    @And("^the error message mentions \"([^\"]*)\"$")
    public void the_error_message_mentions(String fragment) throws Throwable {
        ErrorResponse error = json.fromJson(helper.getJsonResponse(), ErrorResponse.class);
        assertTrue(error.errorMessage.contains(fragment));
    }

    @And("^the error message is \"([^\"]*)\"$")
    public void the_error_message_is(String msg) throws Throwable {
        ErrorResponse error = json.fromJson(helper.getJsonResponse(), ErrorResponse.class);
        assertEquals(msg, error.errorMessage);
    }
}
