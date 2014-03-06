package checkout;

import java.util.HashMap;
import java.util.Map;

public class ThreeForTwoTracker implements OfferTracker {
    public static final String OFFER_CODE = "3_FOR_2";

    private Map<String, Integer> purchases = new HashMap<String, Integer>();

    @Override
    public void process(Float amount, Money cost) {
        purchases.put(cost.toString(), amount.intValue());
    }

    @Override
    public Money calculateSavings() {
        return new Money();
    }
}
