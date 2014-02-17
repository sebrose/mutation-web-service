package checkout;

import com.google.gson.Gson;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import support.KnowsTheDomain;

import java.util.Map;

import static org.junit.Assert.assertTrue;

public class BasketAnswerSteps {
    private final KnowsTheDomain helper;

    private Gson json = new Gson();

    private class MyResults {
        String errorCode;
        MyBatchResults batch;
    }

    private static class MyBatchResults {
        public Map<Integer, String> incorrectBaskets;

        @Override
        public String toString() {
            String result =  "baskets: [";

            for (Integer basketId : incorrectBaskets.keySet())  {
                result += String.format("%d : %s, ", basketId, incorrectBaskets.get(basketId));
            }

            return result + "]";
        }
    }

    private static class MyBasketResult {
        public int basketId;
        public boolean wasResultCorrect;

        @Override
        public String toString() {
            return String.format("Basket Id (%d) -> %s", basketId, wasResultCorrect ? "correct" : "INCORRECT");
        }
    }

    public BasketAnswerSteps(KnowsTheDomain helper){
        this.helper = helper;
    }

    @When("^I submit the correct totals$")
    public void I_submit_the_correct_totals() throws Throwable {
        KnowsTheDomain.ResponseWrapper returned = helper.submitTotals("{\"batch\":{\"baskets\":{\"1\":{\"dollars\":0,\"cents\":25}}}}");

        helper.storeResponse(returned);
    }

    @When("^I submit empty totals$")
    public void I_submit_empty_totals() throws Throwable {
        KnowsTheDomain.ResponseWrapper returned = helper.submitTotals("{\"batch\":{\"baskets\":{}}}");

        helper.storeResponse(returned);
    }

    @When("^I submit incorrect totals$")
    public void I_submit_incorrect_totals() throws Throwable {
        KnowsTheDomain.ResponseWrapper returned = helper.submitTotals("{\"batch\":{\"baskets\":{\"1\":{\"dollars\":0,\"cents\":50}}}}");

        helper.storeResponse(returned);
    }

    @When("^I submit totals for an unexpected basket$")
    public void I_submit_totals_for_an_unexpected_basket() throws Throwable {
        KnowsTheDomain.ResponseWrapper returned = helper.submitTotals("{\"batch\":{\"baskets\":{\"1\":{\"dollars\":0,\"cents\":25},\"2\":{\"dollars\":0,\"cents\":25}}}}");

        helper.storeResponse(returned);
    }

    @And("^I know the basket ID of the incorrect total$")
    public void I_know_the_basket_ID_of_the_incorrect_total() throws Throwable {
        MyResults results = json.fromJson(helper.getJsonResponse(), MyResults.class);

        assertTrue(results.batch.incorrectBaskets.containsValue(BatchPriceComparator.INCORRECT_VALUE_SUBMITTED));
    }

    @And("^I know the basket ID of the missing basket$")
    public void I_know_the_basket_ID_of_the_missing_basket() throws Throwable {
        MyResults results = json.fromJson(helper.getJsonResponse(), MyResults.class);

        assertTrue(results.batch.incorrectBaskets.containsValue(BatchPriceComparator.MISSING_BASKET_ID));
    }

    @And("^I know the basket ID of the unexpected basket$")
    public void I_know_the_basket_ID_of_the_unexpected_basket() throws Throwable {
        MyResults results = json.fromJson(helper.getJsonResponse(), MyResults.class);

        assertTrue(results.batch.incorrectBaskets.containsValue(BatchPriceComparator.UNEXPECTED_BASKET_ID_SUBMITTED));
    }

}
