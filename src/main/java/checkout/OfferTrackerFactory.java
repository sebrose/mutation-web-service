package checkout;

import java.util.HashMap;
import java.util.Map;

public class OfferTrackerFactory {
    private static Map<String, OfferTracker> trackers = new HashMap<String, OfferTracker>();

    public static OfferTracker create(String code) {
        if (trackers.containsKey(code)) {
            System.out.println("Returning test OfferTracker " + code);
            return trackers.get(code);
        }

        if (code.equals(BogofTracker.OFFER_CODE)) {
            return new BogofTracker();
        }

        if (code.equals(TenPercentTracker.OFFER_CODE)) {
            return new TenPercentTracker();
        }

        throw new IllegalArgumentException("Unrecognised offer code: " + code);
    }

    public static void WE_ARE_TESTING_WITH(String code, OfferTracker tracker) {
        System.out.println("caching test OfferTracker " + code);
        trackers.put(code, tracker);
    }

}
