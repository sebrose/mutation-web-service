package checkout.data;

import checkout.PriceList;

public class PriceListGenerator {
    public static PriceList forRound(int round){
        switch (round){
            case 0:
                return round0();
            case 1:
                return round1();
            case 2:
                return round2();
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
        builder.addEntry("apple").withPrice("0.25");

        return builder.build();
    }

    private static PriceList round2() {
        PriceListBuilder builder = new PriceListBuilder();

        builder.addEntry("banana").withPrice("0.25");
        builder.addEntry("apple").withPrice("0.35");
        builder.addEntry("orange").withPrice("0.45");
        builder.addEntry("avocado").withPrice("0.75");

        return builder.build();
    }
}
