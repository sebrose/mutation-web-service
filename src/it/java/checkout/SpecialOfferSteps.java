package checkout;

import cucumber.api.java.en.And;
import support.KnowsTheDomain;

public class SpecialOfferSteps {
    KnowsTheDomain helper;

    public SpecialOfferSteps(KnowsTheDomain helper) {
        this.helper = helper;
    }

    @And("^I ask for special offers$")
    public void I_ask_for_special_offers() throws Throwable {
        helper.requestSpecialOffers();
    }
}
