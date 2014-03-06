package checkout;

public class SpecialOffer {
    private final String offerCode;
    private final String description;
    private final String eligibleItemCode;
    private OfferTracker offerTracker;

    public SpecialOffer(String offerCode, String description, String eligibleItemCode) {
        this.offerCode = offerCode;
        this.description = description;
        this.eligibleItemCode = eligibleItemCode;

        if (offerCode == null) {
            throw new IllegalArgumentException("Must have offer code set");
        }

        if (description == null) {
            throw new IllegalArgumentException("Must have description set");
        }

        if (eligibleItemCode == null) {
            throw new IllegalArgumentException("Must have eligible item set");
        }
    }

    public Money calculateSavings() {
        return getOfferTracker().calculateSavings();
    }

    public void process(String itemCode, String categoryCode, float amount, Money cost) {
        if (itemCode.equals(eligibleItemCode)) {
            getOfferTracker().process(amount, cost);
        }
    }

    private OfferTracker getOfferTracker() {
        if (offerTracker == null) {
            offerTracker = OfferTrackerFactory.create(offerCode);
        }

        return offerTracker;
    }
}
