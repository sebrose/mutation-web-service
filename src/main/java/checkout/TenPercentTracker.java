package checkout;

public class TenPercentTracker implements OfferTracker {
    public static final String OFFER_CODE = "TEN_PC";
    private Money totalSaving = new Money();

    @Override
    public void process(Float amount, Money cost) {
        totalSaving = totalSaving.add(cost.multiply(0.1).multiply(amount));
    }

    @Override
    public Money calculateSavings() {
        return totalSaving;
    }
}
