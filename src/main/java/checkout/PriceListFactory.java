package checkout;

import checkout.data.PriceListGenerator;

public class PriceListFactory {
    private static PriceList testPriceList;

    public static PriceList create(int round) {
        if (testPriceList != null) {
            return testPriceList;
        }

        return PriceListGenerator.forRound(round);
    }

    public static void WE_ARE_TESTING_WITH(PriceList testPriceList_) {
        testPriceList = testPriceList_;
    }
}
