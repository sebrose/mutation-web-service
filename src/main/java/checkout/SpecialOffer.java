package checkout;

public class SpecialOffer {
    private final String offerCode;
    private final String description;
    private final String eligibleItemCode;
    private final String eligibleCategoryCode;
    private OfferTracker offerTracker;

    public SpecialOffer(String offerCode, String description, String eligibleItemCode, String eligibleCategoryCode) {
        this.offerCode = offerCode;
        this.description = description;
        this.eligibleItemCode = eligibleItemCode;
        this.eligibleCategoryCode = eligibleCategoryCode;

        if (offerCode == null) {
            throw new IllegalArgumentException("Must have offer code set");
        }

        if (description == null) {
            throw new IllegalArgumentException("Must have description set");
        }

        if (eligibleItemCode == null && eligibleCategoryCode == null) {
            throw new IllegalArgumentException("Must have EITHER item or category code set");
        }

        if (eligibleCategoryCode != null && eligibleItemCode != null) {
            throw new IllegalArgumentException("Must have EITHER item OR category code set");
        }
    }

    public Money calculateAndClearSavings() {
        Money savings = getOfferTracker().calculateSavings();

        offerTracker = null;

        return savings;
    }

    public void process(String itemCode, String categoryCode, float amount, Money cost) {
        if (itemCode.equals(eligibleItemCode) || categoryCode.equals(eligibleCategoryCode)) {
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
