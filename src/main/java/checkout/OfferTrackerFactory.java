package checkout;

import java.util.HashMap;
import java.util.Map;

public class OfferTrackerFactory {
    private static Map<String, OfferTracker> trackers = new HashMap<String, OfferTracker>();

    public static OfferTracker create(String code) {
        if (trackers.containsKey(code)) {
            return trackers.get(code);
        }

        if (code.equals(BogofTracker.OFFER_CODE)) {
            return new BogofTracker();
        }

        throw new IllegalArgumentException("Unrecognised offer code: " + code);
    }

    public static void WE_ARE_TESTING_WITH(String code, OfferTracker tracker) {
        trackers.put(code, tracker);
    }

}
