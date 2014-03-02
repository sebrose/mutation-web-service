package checkout;

import com.google.gson.Gson;

public class PriceListFactory {
    private static PriceList testPriceList;
    private static Gson json = new Gson();

    public static PriceList create(MyReader dataReader, int round) {
        if (testPriceList != null) {
            return testPriceList;
        }

        return json.fromJson(dataReader.getForRound(round), PriceList.class);
    }

    public static void WE_ARE_TESTING_WITH(PriceList testPriceList_) {
        testPriceList = testPriceList_;
    }
}
