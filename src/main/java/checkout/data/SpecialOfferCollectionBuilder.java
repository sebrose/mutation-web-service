package checkout.data;

import checkout.SpecialOffer;
import checkout.SpecialOfferCollection;

import java.util.ArrayList;
import java.util.List;

public class SpecialOfferCollectionBuilder {
    private List<SpecialOffer> offers = new ArrayList<SpecialOffer>();

    private SpecialOfferBuilder offerBuilder;

    public SpecialOfferCollection build() {
        if (offerBuilder != null) {
            offers.add(offerBuilder.build());
            offerBuilder = null;
        }

        SpecialOfferCollection collection = new SpecialOfferCollection();
        for (SpecialOffer offer : offers) {
            collection.addOffer(offer);
        }

        return collection;
    }

    public SpecialOfferBuilder addOffer() {

        if (offerBuilder != null) {
            offers.add(offerBuilder.build());
        }

        offerBuilder = new SpecialOfferBuilder();

        return offerBuilder;
    }

    public SpecialOfferBuilder addOffer(String offerCode) {
        addOffer().withOfferCode(offerCode);
        return offerBuilder;
    }
}
