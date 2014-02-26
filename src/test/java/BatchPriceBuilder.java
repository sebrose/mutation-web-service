import checkout.Money;

import java.util.HashMap;
import java.util.Map;

import checkout.data.BatchPrice;

public class BatchPriceBuilder {
    private int nextBasketId = 1;
    private Map<Integer, Money> baskets = new HashMap<Integer, Money>();

    public BatchPrice build() {
        BatchPrice batchPrice = new BatchPrice();
        batchPrice.batch.baskets = baskets;

        baskets = new HashMap<Integer, Money>();
        nextBasketId = 1;

        return batchPrice;
    }

    public BatchPriceBuilder withBasketTotal(String total) {
        baskets.put(nextBasketId++, new Money(total));

        return this;
    }

    public BatchPriceBuilder withBasketIdAndTotal(Integer id, String total) {
        baskets.put(id, new Money(total));

        return this;
    }
}
