import checkout.BogofTracker;
import checkout.OfferTracker;
import checkout.OfferTrackerFactory;
import checkout.TenPercentTracker;
import org.junit.Assert;
import org.junit.Test;

public class OfferTrackerFactoryTest {
    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIfNoTrackerFound() throws Exception {
        OfferTrackerFactory.create("undefined");
    }

    @Test
    public void shouldRecogniseBogofCode() throws Exception {
        OfferTracker tracker = OfferTrackerFactory.create(BogofTracker.OFFER_CODE);

        Assert.assertTrue(tracker instanceof BogofTracker);
    }

    @Test
    public void shouldRecogniseTenPercentCode() throws Exception {
        OfferTracker tracker = OfferTrackerFactory.create(TenPercentTracker.OFFER_CODE);

        Assert.assertTrue(tracker instanceof TenPercentTracker);
    }
}
