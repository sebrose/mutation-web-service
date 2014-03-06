package checkout;

import com.google.gson.Gson;
import cucumber.api.java.en.And;
import support.KnowsTheDomain;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SpecialOfferSteps {
    private static class MyResults {
        String errorMessage;
        MySpecialOfferCollection specialOffers;
    }

    class MySpecialOfferCollection {
        public List<MySpecialOffer> offers = new ArrayList<MySpecialOffer>();
    }

    class MySpecialOffer {
        public String offerCode;
        public String description;
        public String eligibleItemCode;
        public String eligibleCategoryCode;
    }

    KnowsTheDomain helper;
    private Gson json = new Gson();

    public SpecialOfferSteps(KnowsTheDomain helper) {
        this.helper = helper;
    }

    @And("^I ask for special offers$")
    public void I_ask_for_special_offers() throws Throwable {
        helper.requestSpecialOffers();
    }

    @And("^there are (\\d+) special offers$")
    public void there_are_special_offers(int count) throws Throwable {
        MyResults results = json.fromJson(helper.getJsonResponse(), MyResults.class);
        assertEquals(count, results.specialOffers.offers.size());
    }

    @And("^there is (\\d+) special offer$")
    public void there_is_special_offers(int count) throws Throwable {
        MyResults results = json.fromJson(helper.getJsonResponse(), MyResults.class);
        assertEquals(count, results.specialOffers.offers.size());
    }
}
