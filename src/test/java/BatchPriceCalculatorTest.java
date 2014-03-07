import checkout.*;
import checkout.data.BatchBuilder;
import checkout.data.BatchPrice;
import checkout.data.PriceListBuilder;
import checkout.data.SpecialOfferCollectionBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class BatchPriceCalculatorTest {
    BatchBuilder batchBuilder = new BatchBuilder();
    PriceListBuilder priceListBuilder = new PriceListBuilder();
    SpecialOfferCollectionBuilder specialOfferCollectionBuilder = new SpecialOfferCollectionBuilder();

    @Test
    public void shouldUsePriceListToCalculatePrice() {
        batchBuilder.addNewBasket().withItem("banana").withQuantity(1);
        priceListBuilder.addEntry("banana").withUnitPrice("0.50");

        BatchPrice btdi;

        btdi = BatchPriceCalculator.calculate(batchBuilder.build(), priceListBuilder.build(), specialOfferCollectionBuilder.build());

        assertEquals(new Money("0.50"), btdi.batch.baskets.get(1));
    }

    @Test
    public void shouldTakeAccountOfQuantityWhenCalculatingPrice() {
        batchBuilder.addNewBasket().withItem("banana").withQuantity(2);
        priceListBuilder.addEntry("banana").withUnitPrice("0.50");

        BatchPrice btdi;

        btdi = BatchPriceCalculator.calculate(batchBuilder.build(), priceListBuilder.build(), specialOfferCollectionBuilder.build());

        assertEquals(new Money("1.00"), btdi.batch.baskets.get(1));
    }

    @Test
    public void shouldCopeWithMultipleItemsInBasket() {
        batchBuilder.addNewBasket().withItem("banana").withQuantity(2);
        batchBuilder.addToBasket().withItem("apple").withQuantity(1);

        priceListBuilder.addEntry("banana").withUnitPrice("0.50");
        priceListBuilder.addEntry("apple").withUnitPrice("1.50");

        BatchPrice btdi;

        btdi = BatchPriceCalculator.calculate(batchBuilder.build(), priceListBuilder.build(), specialOfferCollectionBuilder.build());

        assertEquals(new Money("2.50"), btdi.batch.baskets.get(1));
    }

    @Test
    public void shouldCopeWithItemsInMultipleBaskets() {
        batchBuilder.addNewBasket().withItem("banana").withQuantity(2);
        batchBuilder.addNewBasket().withItem("apple").withQuantity(1);

        priceListBuilder.addEntry("banana").withUnitPrice("0.50");
        priceListBuilder.addEntry("apple").withUnitPrice("1.50");

        BatchPrice btdi;

        btdi = BatchPriceCalculator.calculate(batchBuilder.build(), priceListBuilder.build(), specialOfferCollectionBuilder.build());

        assertEquals(new Money("1.00"), btdi.batch.baskets.get(1));
        assertEquals(new Money("1.50"), btdi.batch.baskets.get(2));
    }

    @Test
    public void shouldHandleItemSoldByWeight() throws Exception {
        batchBuilder.addNewBasket().withItem("banana").withWeight(1.0f);
        batchBuilder.addNewBasket().withItem("apple").withWeight(1.5f);

        priceListBuilder.addEntry("banana").withKiloPrice("0.50");
        priceListBuilder.addEntry("apple").withKiloPrice("1.50");

        BatchPrice btdi;

        btdi = BatchPriceCalculator.calculate(batchBuilder.build(), priceListBuilder.build(), specialOfferCollectionBuilder.build());

        assertEquals(new Money("0.50"), btdi.batch.baskets.get(1));
        assertEquals(new Money("2.25"), btdi.batch.baskets.get(2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldHandleMissingItem() throws Exception {
        batchBuilder.addNewBasket().withItem("banana").withWeight(1.0f);

        priceListBuilder.addEntry("apple").withKiloPrice("1.50");

        BatchPrice btdi;

        btdi = BatchPriceCalculator.calculate(batchBuilder.build(), priceListBuilder.build(), specialOfferCollectionBuilder.build());
    }

    @Test
    public void shouldAccessCorrectCategory() throws Exception {
        batchBuilder.addNewBasket().withItem("banana").withWeight(1.0f);
        priceListBuilder.addEntry("banana").inCategory("fruit").withKiloPrice("0.50");
        specialOfferCollectionBuilder.addOffer("OFFER").forCategory("fruit").withDescription("test");
        OfferTrackerFactory.WE_ARE_TESTING_WITH("OFFER", new OfferTracker() {

            Money saving = new Money();

            @Override
            public void process(Float amount, Money cost) {
                System.out.println("Processing " + cost);
                saving = saving.add(cost);
                System.out.println("Saving " + saving);
            }

            @Override
            public Money calculateSavings() {
                System.out.println("Savings " + saving);
                return saving;
            }
        });

        BatchPrice btdi;

        btdi = BatchPriceCalculator.calculate(batchBuilder.build(), priceListBuilder.build(), specialOfferCollectionBuilder.build());

        assertEquals(new Money(), btdi.batch.baskets.get(1));
    }

    @Test
    public void shouldResetTrackersForEachBasket() throws Exception {
        batchBuilder.addNewBasket().withItem("banana").withWeight(1.0f);
        batchBuilder.addNewBasket().withItem("thingy").withWeight(1.0f);
        priceListBuilder.addEntry("banana").inCategory("fruit").withKiloPrice("0.50");
        priceListBuilder.addEntry("thingy").inCategory("widget").withUnitPrice("0.50");
        specialOfferCollectionBuilder.addOffer("OFFER").forCategory("fruit").withDescription("test");
        OfferTrackerFactory.WE_ARE_TESTING_WITH("OFFER", new OfferTracker() {

            Money saving = new Money();

            @Override
            public void process(Float amount, Money cost) {
                saving = saving.add(cost);
            }

            @Override
            public Money calculateSavings() {
                Money mySaving = saving;
                saving = new Money();
                return mySaving;
            }
        });

        BatchPrice btdi;

        btdi = BatchPriceCalculator.calculate(batchBuilder.build(), priceListBuilder.build(), specialOfferCollectionBuilder.build());

        assertEquals(new Money(), btdi.batch.baskets.get(1));
        assertEquals(new Money("0.50"), btdi.batch.baskets.get(2));
    }

    @Test
    public void shouldHandleUnusedSpecialOffer() throws Exception {
        batchBuilder.addNewBasket().withItem("banana").withWeight(1.0f);
        priceListBuilder.addEntry("banana").inCategory("fruit").withKiloPrice("0.50");
        specialOfferCollectionBuilder.addOffer("OFFER").forCategory("hardware").withDescription("test");
        OfferTrackerFactory.WE_ARE_TESTING_WITH("OFFER", new BogofTracker());

        BatchPrice btdi;

        btdi = BatchPriceCalculator.calculate(batchBuilder.build(), priceListBuilder.build(), specialOfferCollectionBuilder.build());

        assertEquals(new Money("0.50"), btdi.batch.baskets.get(1));
    }
}
