import checkout.BatchPriceCalculator;
import checkout.Money;
import checkout.data.BatchBuilder;
import checkout.data.BatchPrice;
import checkout.data.PriceListBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;



public class BatchPriceCalculatorTest {
    BatchBuilder batchBuilder = new BatchBuilder();
    PriceListBuilder priceListBuilder = new PriceListBuilder();

    @Test
    public void shouldUsePriceListToCalculatePrice() {
        batchBuilder.addNewBasket().withItem("banana");
        priceListBuilder.addEntry("banana").withPrice("0.50");

        BatchPrice btdi;

        btdi = BatchPriceCalculator.calculate(batchBuilder.build(), priceListBuilder.build());

        assertEquals(new Money("0.50"), btdi.batch.baskets.get(1));
    }

    @Test
    public void shouldTakeAccountOfQuantityWhenCalculatingPrice() {
        batchBuilder.addNewBasket().withItem("banana").withQuantity(2);
        priceListBuilder.addEntry("banana").withPrice("0.50");

        BatchPrice btdi;

        btdi = BatchPriceCalculator.calculate(batchBuilder.build(), priceListBuilder.build());

        assertEquals(new Money("1.00"), btdi.batch.baskets.get(1));
    }

    @Test
    public void shouldCopeWithMultipleItemsInBasket() {
        batchBuilder.addNewBasket().withItem("banana").withQuantity(2);
        batchBuilder.addToBasket().withItem("apple").withQuantity(1);

        priceListBuilder.addEntry("banana").withPrice("0.50");
        priceListBuilder.addEntry("apple").withPrice("1.50");

        BatchPrice btdi;

        btdi = BatchPriceCalculator.calculate(batchBuilder.build(), priceListBuilder.build());

        assertEquals(new Money("2.50"), btdi.batch.baskets.get(1));
    }

    @Test
    public void shouldCopeWithItemsInMultipleBaskets() {
        batchBuilder.addNewBasket().withItem("banana").withQuantity(2);
        batchBuilder.addNewBasket().withItem("apple").withQuantity(1);

        priceListBuilder.addEntry("banana").withPrice("0.50");
        priceListBuilder.addEntry("apple").withPrice("1.50");

        BatchPrice btdi;

        btdi = BatchPriceCalculator.calculate(batchBuilder.build(), priceListBuilder.build());

        assertEquals(new Money("1.00"), btdi.batch.baskets.get(1));
        assertEquals(new Money("1.50"), btdi.batch.baskets.get(2));
    }
}
