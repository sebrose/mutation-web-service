package checkout;

public interface OfferTracker {
    void process(Float amount, Money cost);

    Money calculateSavings();
}
