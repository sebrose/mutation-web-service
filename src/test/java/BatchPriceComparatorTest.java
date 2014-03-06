import checkout.BatchPriceComparator;
import checkout.BatchPriceComparisonResult;
import checkout.data.BatchPrice;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BatchPriceComparatorTest {
    BatchPriceBuilder builder = new BatchPriceBuilder();

    @Test
    public void compareTwoEmptyBatchPrices() {
        BatchPrice response = builder.build();
        BatchPrice expected = builder.build();

        BatchPriceComparisonResult v = checkout.BatchPriceComparator.check(expected, response);

        assertTrue(v.allResultsCorrect());
    }

    @Test
    public void compareNonEmptyIdenticalBatchPrices() {
        BatchPrice response = builder.withBasketTotal("10.00").build();
        BatchPrice expected = builder.withBasketTotal("10.00").build();
        ;

        BatchPriceComparisonResult v = checkout.BatchPriceComparator.check(expected, response);

        assertTrue(v.allResultsCorrect());
    }


    @Test
    public void compareDifferentBatchPrices() {
        Integer defaultBasketId = 1;

        BatchPrice response = builder.withBasketTotal("10.00").build();
        BatchPrice expected = builder.withBasketTotal("7.00").build();
        ;

        BatchPriceComparisonResult v = checkout.BatchPriceComparator.check(expected, response);

        //assertEquals(checkout.BatchPriceComparator.INCORRECT_VALUE_SUBMITTED, v.getResult(defaultBasketId));
        Assert.assertTrue(v.getResult(defaultBasketId).contains(BatchPriceComparator.INCORRECT_VALUE_SUBMITTED));
    }

    @Test
    public void submittedBatchHasUnexpectedBasketId() {
        Integer incorrectBasketId = 3;
        BatchPrice response = builder.withBasketIdAndTotal(incorrectBasketId, "10.00").build();
        BatchPrice expected = builder.build();
        ;

        BatchPriceComparisonResult v = checkout.BatchPriceComparator.check(expected, response);

        assertEquals(checkout.BatchPriceComparator.UNEXPECTED_BASKET_ID_SUBMITTED, v.getResult(incorrectBasketId));
    }

    @Test
    public void submittedBatchIsMissingBasketId() {
        Integer defaultBasketId = 1;

        BatchPrice response = builder.build();
        BatchPrice expected = builder.withBasketTotal("10.00").build();
        ;

        BatchPriceComparisonResult v = checkout.BatchPriceComparator.check(expected, response);

        assertEquals(checkout.BatchPriceComparator.MISSING_BASKET_ID, v.getResult(defaultBasketId));
    }

    @Test
    public void orderOfAddingIsNotSignificant() {

        BatchPrice response = builder
                .withBasketIdAndTotal(2, "5.00")
                .withBasketIdAndTotal(1, "10.00")
                .build();
        BatchPrice expected = builder
                .withBasketTotal("10.00")
                .withBasketTotal("5.00")
                .build();
        ;

        BatchPriceComparisonResult v = checkout.BatchPriceComparator.check(expected, response);

        assertTrue(v.allResultsCorrect());
    }

    @Test
    public void basketIdsAreSignificant() {

        BatchPrice response = builder
                .withBasketIdAndTotal(1, "5.00")
                .withBasketIdAndTotal(2, "10.00")
                .build();
        BatchPrice expected = builder
                .withBasketIdAndTotal(1, "10.00")
                .withBasketIdAndTotal(2, "5.00")
                .build();
        ;

        BatchPriceComparisonResult v = checkout.BatchPriceComparator.check(expected, response);

        assertTrue(v.getResult(1).contains(BatchPriceComparator.INCORRECT_VALUE_SUBMITTED));
        assertTrue(v.getResult(2).contains(checkout.BatchPriceComparator.INCORRECT_VALUE_SUBMITTED));
    }
}