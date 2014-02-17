package checkout;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
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
    private int httpReturnCode;

    private Gson json = new Gson();
    private MyResults results;
    
    public BasketSteps(KnowsTheDomain helper){
        this.helper = helper;
    }

    @When("^I ask for a batch")
    public void I_ask_for_baskets() throws Throwable {
        try {

            Client client = Client.create();

            WebResource webResource = client
                    .resource("http://localhost:" + ServerHooks.PORT + "/Checkout/Batch/" + helper.getTeamName());

            ClientResponse response = webResource.type("application/json")
                    .get(ClientResponse.class);

            httpReturnCode = response.getStatus();

            String jsonString = response.getEntity(String.class);
            if (jsonString != null){
                results = json.fromJson(jsonString, MyResults.class);
            }
        }
        catch (RuntimeException r) {
            throw r;
        }
        catch (Exception e) {
            System.out.println("Exception caught");
            e.printStackTrace();

        }
    }

    @Then("^I successfully receive a batch$")
    public void I_successfully_receive_a_batch() {
        assertEquals(200, httpReturnCode);
    }

    @Then("^my batch request is rejected$")
    public void my_batch_request_is_rejected() {
        assertEquals(400, httpReturnCode);
    }

    @Then("^the batch contains (\\d+) basket[s]?$")
    public void the_batch_contains_baskets(int count) throws Throwable {
        assertEquals(count, results.batch.baskets.length);
    }

    @Then("^the batch contains multiple baskets$")
    public void the_batch_contains_multiple_baskets() throws Throwable {
        assertTrue(results.batch.baskets.length > 1);
    }

    @Then("^the basket contains (\\d+) item[s]?$")
    public void the_basket_contains_item(int count) throws Throwable {
        assertEquals(count, results.batch.baskets[0].items.length);
    }

}
