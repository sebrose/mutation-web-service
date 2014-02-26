package checkout;

import java.util.HashMap;
import java.util.Map;

public class BatchPriceComparisonResult {
    Map<Integer, String> incorrectBaskets = new HashMap<Integer, String>();

    public boolean allResultsCorrect() {
         return incorrectBaskets.isEmpty();
    }

    public String getResult(Integer basketId) {
        return incorrectBaskets.get(basketId);
    }
}
