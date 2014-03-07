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
    private static final String DUMMY_ITEM = "yyy";
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
    public void shouldCreateWithJustCategoryCode() throws Exception {
        SpecialOffer offer = builder.withOfferCode(DUMMY_OFFER_CODE).forCategory("bar").withDescription("dummy").build();
    }

    @Test
    public void shouldCreateWithJustItemCode() throws Exception {
        SpecialOffer offer = builder.withOfferCode(DUMMY_OFFER_CODE).forItemCode("bar").withDescription("dummy").build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIfItemAndCategorySet() throws Exception {
        SpecialOffer offer = builder.withOfferCode(DUMMY_OFFER_CODE).forItemCode("foo").forCategory("bar").withDescription("dummy").build();
    }

    @Test
    public void shouldNotAccumulateSavingsIfDoesNotApply() throws Exception {
        SpecialOffer offer = builder.withOfferCode(DUMMY_OFFER_CODE).forItemCode("foo").withDescription("dummy").build();
        OfferTrackerFactory.WE_ARE_TESTING_WITH(DUMMY_OFFER_CODE, testTracker);

        offer.process("bar", DUMMY_CATEGORY, 1.0f, new Money("100.00"));

        Assert.assertEquals(new Money(), offer.calculateAndClearSavings());
    }

    @Test
    public void shouldHandleNullCategory() throws Exception {
        SpecialOffer offer = builder.withOfferCode(DUMMY_OFFER_CODE).forItemCode("foo").withDescription("dummy").build();
        OfferTrackerFactory.WE_ARE_TESTING_WITH(DUMMY_OFFER_CODE, testTracker);

        offer.process("bar", null, 1.0f, new Money("100.00"));

        Assert.assertEquals(new Money(), offer.calculateAndClearSavings());
    }

    @Test
    public void shouldAccumulateSavingsItemCodeMatches() throws Exception {
        SpecialOffer offer = builder.withOfferCode(DUMMY_OFFER_CODE).forItemCode("foo").withDescription("dummy").build();
        OfferTrackerFactory.WE_ARE_TESTING_WITH(DUMMY_OFFER_CODE, testTracker);

        offer.process("foo", DUMMY_CATEGORY, 1.0f, new Money("100.00"));

        Assert.assertEquals(new Money("100.00"), offer.calculateAndClearSavings());
    }

    @Test
    public void shouldAccumulateSavingsCategoryCodeMatches() throws Exception {
        SpecialOffer offer = builder.withOfferCode(DUMMY_OFFER_CODE).forCategory("bar").withDescription("dummy").build();
        OfferTrackerFactory.WE_ARE_TESTING_WITH(DUMMY_OFFER_CODE, testTracker);

        offer.process(DUMMY_ITEM, "bar", 1.0f, new Money("100.00"));

        Assert.assertEquals(new Money("100.00"), offer.calculateAndClearSavings());
    }
}
