import checkout.Money;
import checkout.OfferTracker;
import checkout.ThreeForTwoTracker;
import org.junit.Assert;
import org.junit.Test;

public class ThreeForTwoTrackerTest {
    @Test
    public void shouldDoNothingForSingles() throws Exception {
        OfferTracker offer = new ThreeForTwoTracker();
        offer.process(1f, new Money("1.23"));

        Assert.assertEquals(new Money(), offer.calculateSavings());
    }

    //@Test
    public void shouldSaveCostForTriples() throws Exception {
        OfferTracker offer = new ThreeForTwoTracker();
        offer.process(3f, new Money("1.23"));

        Assert.assertEquals(new Money("1.23"), offer.calculateSavings());
    }

    // shouldSaveCheapest of 3

    // 6 or more should save as much as possible (cheapest and 4th cheapest)

    // should only handle unit quantities
}
