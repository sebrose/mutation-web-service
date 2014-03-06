import checkout.Money;
import checkout.OfferTracker;
import checkout.TenPercentTracker;
import org.junit.Assert;
import org.junit.Test;

public class TenPercentTrackerTest {
    @Test
    public void shouldHandlesZeroCostItems() throws Exception {
        OfferTracker offer = new TenPercentTracker();
        offer.process(1f, new Money("1.23"));

        Assert.assertEquals(new Money("0.12"), offer.calculateSavings());
    }

    @Test
    public void shouldHandleMultipleItems() throws Exception {
        OfferTracker offer = new TenPercentTracker();
        offer.process(1f, new Money("1.10"));
        offer.process(1f, new Money("2.20"));
        offer.process(1f, new Money("3.30"));

        Assert.assertEquals(new Money("0.66"), offer.calculateSavings());

    }

    @Test
    public void shouldApplyToEachItemIndividually() throws Exception {
        OfferTracker offer = new TenPercentTracker();
        offer.process(1f, new Money("1.03"));
        offer.process(1f, new Money("1.03"));

        Assert.assertEquals(new Money("0.20"), offer.calculateSavings());

    }

    @Test
    public void shouldHandleNonZeroQuantities() throws Exception {
        OfferTracker offer = new TenPercentTracker();
        offer.process(2f, new Money("1.03"));

        Assert.assertEquals(new Money("0.20"), offer.calculateSavings());

    }


    @Test
    public void shouldHandleNonIntegerAmounts() throws Exception {
        OfferTracker offer = new TenPercentTracker();
        offer.process(2.6f, new Money("1.00"));

        Assert.assertEquals(new Money("0.26"), offer.calculateSavings());

    }

    @Test
    public void shouldReturnZeroSavingsIfNothingProcessed() throws Exception {
        OfferTracker offer = new TenPercentTracker();

        Assert.assertEquals(new Money(), offer.calculateSavings());
    }
}
