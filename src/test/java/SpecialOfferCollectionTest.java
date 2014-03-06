import checkout.Money;
import checkout.SpecialOffer;
import checkout.SpecialOfferCollection;
import org.junit.Assert;
import org.junit.Test;

public class SpecialOfferCollectionTest {

    private static final String DUMMY_ITEM_CODE = "banana";
    private static final String NON_MATCHING_ITEM = "apple";
    private static final String MATCHING_ITEM = "avocado";
    private static final String DUMMY_CATEGORY_CODE = "";
    private static final Float DUMMY_AMOUNT = 1f;
    private static final Money DUMMY_COST = new Money("1.23");
    private static final Money DUMMY_SAVINGS = new Money("100.00");
    private static final String TEST_OFFER = "TEST";
    private static final String TEST_DESCRIPTION = "Desc";

    private static final SpecialOffer testOffer = new SpecialOffer(TEST_OFFER, TEST_DESCRIPTION, NON_MATCHING_ITEM) {
        private boolean processed;

        @Override
        public void process(String itemCode, String categoryCode, float amount, Money cost) {
            if (itemCode.equals(MATCHING_ITEM))
                processed = true;
        }

        @Override
        public Money calculateSavings() {
            if (processed) {
                return DUMMY_SAVINGS;
            }
            return new Money();
        }
    };

    @Test
    public void shouldNotComplainIfNoOffers() throws Exception {

        SpecialOfferCollection collection = new SpecialOfferCollection();

        collection.process(DUMMY_ITEM_CODE, DUMMY_CATEGORY_CODE, DUMMY_AMOUNT, DUMMY_COST);

        Assert.assertEquals(new Money(), collection.calculateSavings());
    }

    @Test
    public void shouldProcessAnOffer() throws Exception {

        SpecialOfferCollection collection = new SpecialOfferCollection();
        collection.addOffer(testOffer);

        collection.process(MATCHING_ITEM, DUMMY_CATEGORY_CODE, DUMMY_AMOUNT, DUMMY_COST);

        Assert.assertEquals(DUMMY_SAVINGS, collection.calculateSavings());
    }


    @Test
    public void shouldProcessEveryOffer() throws Exception {

        SpecialOfferCollection collection = new SpecialOfferCollection();
        collection.addOffer(testOffer);
        collection.addOffer(testOffer);

        collection.process(MATCHING_ITEM, DUMMY_CATEGORY_CODE, DUMMY_AMOUNT, DUMMY_COST);

        Assert.assertEquals(DUMMY_SAVINGS.multiply(2.0), collection.calculateSavings());
    }
}
