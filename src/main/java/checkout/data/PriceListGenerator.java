package checkout.data;

import checkout.PriceList;
import com.google.gson.Gson;

public class PriceListGenerator {
    private static Gson json = new Gson();

    public static PriceList forRound(int round){
        switch (round){
            case 0:
                return round0();
            case 1:
                return round1();
            default:
                return null;
        }
    }

    public static PriceList round0() {
        PriceListBuilder builder = new PriceListBuilder();

        builder.addEntry("banana").withPrice("0.25");

        return builder.build();
    }

    private static PriceList round1() {
        PriceListBuilder builder = new PriceListBuilder();

        builder.addEntry("banana").withPrice("0.25");
        builder.addEntry("apple").withPrice("0.35");

        return builder.build();
    }
}
