package checkout.data;

import checkout.SpecialOffer;

public class SpecialOfferBuilder {

    private String offerCode;
    private String description;
    private String eligibleItemCode;

    public SpecialOffer build() {
        return new SpecialOffer(offerCode, description, eligibleItemCode);
    }

    public SpecialOfferBuilder withOfferCode(String offerCode) {
        this.offerCode = offerCode;
        return this;
    }

    public SpecialOfferBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public SpecialOfferBuilder forItemCode(String itemCode) {
        this.eligibleItemCode = itemCode;
        return this;
    }
}

