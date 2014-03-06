import checkout.BogofTracker;
import checkout.Money;
import checkout.OfferTracker;
import org.junit.Assert;
import org.junit.Test;

public class BogofTrackerTest {
    @Test
    public void bogofDoesNothingForSingles() throws Exception {
        OfferTracker offer = new BogofTracker();
        offer.process(1f, new Money("1.23"));

        Assert.assertEquals(new Money(), offer.calculateSavings());
    }

    @Test
    public void bogofChargesForOneWhenYouBuyTwo() throws Exception {
        OfferTracker offer = new BogofTracker();
        offer.process(1f, new Money("1.23"));
        offer.process(1f, new Money("1.23"));

        Assert.assertEquals(new Money("1.23"), offer.calculateSavings());
    }

    @Test
    public void bogofChargesForTwoWhenYouBuyThree() throws Exception {
        OfferTracker offer = new BogofTracker();
        offer.process(1f, new Money("1.23"));
        offer.process(1f, new Money("1.23"));
        offer.process(1f, new Money("1.23"));

        Assert.assertEquals(new Money("1.23"), offer.calculateSavings());
    }

    @Test(expected = IllegalArgumentException.class)
    public void bogofThrowsIfCostChanges() throws Exception {
        OfferTracker offer = new BogofTracker();
        offer.process(1f, new Money("1.23"));
        offer.process(1f, new Money("1.24"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void bogofThrowsIfAmountNotInteger() throws Exception {
        OfferTracker offer = new BogofTracker();
        offer.process(1.01f, new Money("1.23"));
    }

    @Test
    public void shouldReturnZeroSavingsIfNothingProcessed() throws Exception {
        OfferTracker offer = new BogofTracker();

        Assert.assertEquals(new Money(), offer.calculateSavings());
    }
}
