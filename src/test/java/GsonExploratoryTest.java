import checkout.Entry;
import checkout.Money;
import checkout.PriceList;
import checkout.data.BatchPrice;
import com.google.gson.Gson;
import org.junit.Test;

import static com.thoughtworks.selenium.SeleneseTestNgHelper.assertEquals;

public class GsonExploratoryTest {
    private Gson json = new Gson();

    @Test
    public void serialisePriceListToJson() {
        PriceList priceList = new PriceList();

        priceList.addEntry(new Entry("banana", new Money(1, 23), null));

        System.out.println(json.toJson(priceList));
    }

    @Test
    public void serialiseBatchToJson() {
    }

    @Test
    public void whyDoesntThisDeserialise() {
        String data = "{\"batch\":{\"baskets\":{\"1\":{\"dollars\":0,\"cents\":25}}}}";

        BatchPrice batchPrice = json.fromJson(data, BatchPrice.class);
        assertEquals(25, batchPrice.batch.baskets.get(1).cents());
    }

    @Test
    public void serialiseBatchPriceToJson() {
        BatchPriceBuilder builder = new BatchPriceBuilder();

        builder.withBasketTotal("0.25");

        String jsonBody = json.toJson(builder.build());

        BatchPrice submittedTotals = json.fromJson(jsonBody, BatchPrice.class);

    }

    @Test
    public void serialiseBatchPriceWithTwoBasketsToJson() {
        BatchPriceBuilder builder = new BatchPriceBuilder();

        builder.withBasketTotal("0.25");
        builder.withBasketTotal("0.50");

        String jsonBody = json.toJson(builder.build());

        BatchPrice submittedTotals = json.fromJson(jsonBody, BatchPrice.class);

    }

    @Test
    public void serialiseEmptyBatchPriceToJson() {
        BatchPriceBuilder builder = new BatchPriceBuilder();

        String jsonBody = json.toJson(builder.build());

        BatchPrice submittedTotals = json.fromJson(jsonBody, BatchPrice.class);

    }
}
