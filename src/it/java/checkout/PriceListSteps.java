package checkout;

import com.google.gson.Gson;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import support.KnowsTheDomain;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PriceListSteps {
    private static class MyResults {
        String errorMessage;
        MyPriceList priceList;
    }

    private static class MyPriceList{
        Map<String, MyEntry> entries;
    }

    private static class MyEntry {
        String itemCode;
        MyMoney unitPrice;
    }

    private static class MyMoney {
        String dollars;
        String cents;
    }

    private final KnowsTheDomain helper;
    private Gson json = new Gson();

    public PriceListSteps(KnowsTheDomain helper) {
        this.helper = helper;
    }

    @When("^I ask for a price list$")
    public void I_ask_for_a_price_list() throws Throwable {
        helper.requestPriceList();
    }

    @And("^the price list contains (\\d+) items?$")
    public void the_price_list_contains_item(int count) throws Throwable {
        MyResults results = json.fromJson(helper.getJsonResponse(), MyResults.class);
        assertEquals(count, results.priceList.entries.size());

    }
}
