package checkout;

import com.google.gson.Gson;

public class SpecialOfferCollectionFactory {
    private static SpecialOfferCollection testOffers;
    private static Gson json = new Gson();

    public static SpecialOfferCollection create(MyReader dataReader, int round) {
        if (testOffers != null) {
            return testOffers;
        }

        SpecialOfferCollection offers = json.fromJson(dataReader.getForRound(round), SpecialOfferCollection.class);
        return offers;
    }

    public static void WE_ARE_TESTING_WITH(SpecialOfferCollection testOffers_) {
        testOffers = testOffers_;
    }
}
