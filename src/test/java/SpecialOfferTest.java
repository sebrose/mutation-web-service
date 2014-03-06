import checkout.Money;
import checkout.OfferTracker;
import checkout.OfferTrackerFactory;
import checkout.SpecialOffer;
import checkout.data.SpecialOfferBuilder;
import org.junit.Assert;
import org.junit.Test;

public class SpecialOfferTest {
    private SpecialOfferBuilder builder = new SpecialOfferBuilder();

    private static final String DUMMY_CATEGORY = "xxx";
    private static final String DUMMY_OFFER_CODE = "zzz";
    private final OfferTracker testTracker = new OfferTracker() {
        private Money total = new Money();

        @Override
        public void process(Float amount, Money cost) {
            total = cost;
        }

        @Override
        public Money calculateSavings() {
            return total;
        }
    };

    @Test
    public void shouldNotAccumulateSavingsIfDoesNotApply() throws Exception {
        SpecialOffer offer = builder.withOfferCode(DUMMY_OFFER_CODE).forItemCode("foo").withDescription("dummy").build();
        OfferTrackerFactory.WE_ARE_TESTING_WITH(DUMMY_OFFER_CODE, testTracker);

        offer.process("bar", DUMMY_CATEGORY, 1.0f, new Money("100.00"));

        Assert.assertEquals(new Money(), offer.calculateSavings());
    }

    @Test
    public void shouldAccumulateSavingsIfDoApply() throws Exception {
        SpecialOffer offer = builder.withOfferCode(DUMMY_OFFER_CODE).forItemCode("foo").withDescription("dummy").build();
        OfferTrackerFactory.WE_ARE_TESTING_WITH(DUMMY_OFFER_CODE, testTracker);

        offer.process("foo", DUMMY_CATEGORY, 1.0f, new Money("100.00"));

        Assert.assertEquals(new Money("100.00"), offer.calculateSavings());
    }
}
