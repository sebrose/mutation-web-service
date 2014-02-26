import com.google.gson.Gson;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import checkout.data.BatchPrice;

public class GsonExploratoryTest {
    private Gson json = new Gson();

    @Test
    public void serialiseMapToJson() {
        Map<Integer, String> map = new HashMap<Integer, String>();

        map.put(1, "Wibble");
        map.put(2, "FooBar");

        System.out.println(json.toJson(map));
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
