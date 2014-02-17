package checkout;

import com.google.gson.Gson;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import support.KnowsTheDomain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BasketSteps {

    private static class MyResults {
        MyBatch batch;
        String errorMessage;

        @Override
        public String toString() {

            return String.format("Error: %s, Batch: %s}", errorMessage, batch);
        }
    }

    private static class MyBatch {
        public MyBasket[] baskets;

        @Override
        public String toString() {
            String result =  "baskets: [";

            for (MyBasket b : baskets)  {
                result += b.toString() + ", ";
            }

            return result + "]";
        }
    }

    private static class MyBasket {
        public MyItem[] items;


        @Override
        public String toString() {
            String result =  "items: [";

            for (MyItem i : items)  {
                result += i.toString() + ", ";
            }

            return result + "]";
        }
    }

    private static class MyItem {
        public String itemCode;
        public int quantity;


        @Override
        public String toString() {

            return String.format("{code: %s, qty: %d}", itemCode, quantity);
        }

    }

    private final KnowsTheDomain helper;

    private Gson json = new Gson();

    public BasketSteps(KnowsTheDomain helper){
        this.helper = helper;
    }

    @When("^I ask for a batch")
    public void I_ask_for_baskets() throws Throwable {
        helper.requestBatch();
    }

    @Then("^the batch contains (\\d+) basket[s]?$")
    public void the_batch_contains_baskets(int count) throws Throwable {
        MyResults results = json.fromJson(helper.getJsonResponse(), MyResults.class);
        assertEquals(count, results.batch.baskets.length);
    }

    @Then("^the batch contains multiple baskets$")
    public void the_batch_contains_multiple_baskets() throws Throwable {
        MyResults results = json.fromJson(helper.getJsonResponse(), MyResults.class);
        assertTrue(results.batch.baskets.length > 1);
    }

    @Then("^the basket contains (\\d+) item[s]?$")
    public void the_basket_contains_item(int count) throws Throwable {
        MyResults results = json.fromJson(helper.getJsonResponse(), MyResults.class);
        assertEquals(count, results.batch.baskets[0].items.length);
    }

}
