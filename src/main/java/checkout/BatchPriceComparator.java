package checkout;

import checkout.data.BatchPrice;

import java.util.Map;

public class BatchPriceComparator {
    public static final String INCORRECT_VALUE_SUBMITTED = "IncorrectValue";
    public static final String UNEXPECTED_BASKET_ID_SUBMITTED = "UnexpectedBasket";
    public static final String MISSING_BASKET_ID = "MissingBasket";

    public static BatchPriceComparisonResult check(BatchPrice expectedTotals, BatchPrice submittedTotals) {
        BatchPriceComparisonResult result = new BatchPriceComparisonResult();

        for (Map.Entry<Integer, Money> expectedEntry : expectedTotals.batch.baskets.entrySet()) {
            Integer basketId = expectedEntry.getKey();

            if (submittedTotals.batch.baskets.containsKey(basketId)) {
                Money submittedValue = submittedTotals.batch.baskets.get(basketId);
                Money expectedValue = expectedEntry.getValue();

                if (!submittedValue.equals(expectedValue)) {
                    result.incorrectBaskets.put(basketId, INCORRECT_VALUE_SUBMITTED);
                }
            } else {
                result.incorrectBaskets.put(basketId, MISSING_BASKET_ID);
            }
        }

        for (Map.Entry<Integer, Money> submittedEntry : submittedTotals.batch.baskets.entrySet()) {
            Integer basketId = submittedEntry.getKey();

            if (!expectedTotals.batch.baskets.containsKey(basketId)) {
                result.incorrectBaskets.put(basketId, UNEXPECTED_BASKET_ID_SUBMITTED);
            }
        }

        return result;
    }
}