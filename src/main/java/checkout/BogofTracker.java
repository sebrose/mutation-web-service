package checkout;

public class BogofTracker implements OfferTracker {
    public static final String OFFER_CODE = "BOGOF";
    int count;
    Money cost;

    @Override
    public void process(Float amount, Money cost) {
        if (this.cost == null) {
            this.cost = cost;
        } else if (!this.cost.equals(cost)) {
            throw new IllegalArgumentException("Cannot change the price of item during single checkout");
        }

        if (amount != amount.intValue()) {
            throw new IllegalArgumentException("BOGOF only applies to items sold by unit");
        }

        count += amount.intValue();
    }

    @Override
    public Money calculateSavings() {
        if (cost == null) {
            return new Money();
        }

        return cost.multiply(count / 2);
    }
}
