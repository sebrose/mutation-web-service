package checkout;

import java.util.ArrayList;
import java.util.List;

public class SpecialOfferCollection {
    private List<SpecialOffer> offers = new ArrayList<SpecialOffer>();

    public SpecialOfferCollection() {
    }

    public void addOffer(SpecialOffer offer) {
        offers.add(offer);
    }

    public void process(String itemCode, String category, Float amount, Money price) {
        for (SpecialOffer offer : offers) {
            offer.process(itemCode, category, amount, price);
        }
    }

    public Money calculateSavings() {
        Money savings = new Money();

        for (SpecialOffer offer : offers) {
            Money saving = offer.calculateSavings();
            savings = savings.add(saving);
        }

        return savings;
    }
}
