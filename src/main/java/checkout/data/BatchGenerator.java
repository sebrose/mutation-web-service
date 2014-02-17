package checkout.data;

import checkout.Batch;
import com.google.gson.Gson;

public class BatchGenerator {
    private static Gson json = new Gson();

    public static Batch forRound(int round){
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

    public static Batch round0() {
        BatchBuilder builder = new BatchBuilder();

        builder.addNewBasket()
                .withItem("banana");

        return builder.build();
    }

    private static Batch round1() {
        BatchBuilder builder = new BatchBuilder();

        builder.addNewBasket()
                .withItem("banana");

        builder.addNewBasket()
                .withItem("banana")
                .withQuantity(2);

        builder.addNewBasket()
                .withItem("banana");
        builder.addToBasket()
                .withItem("apple")
                .withQuantity(3);
        builder.addToBasket()
                .withItem("banana");

        return builder.build();
    }

    private static Batch round2() {
        BatchBuilder builder = new BatchBuilder();

        builder.addNewBasket()
                .withItem("banana");

        builder.addNewBasket()
                .withItem("banana")
                .withQuantity(2);

        builder.addNewBasket()
                .withItem("banana");
        builder.addToBasket()
                .withItem("apple")
                .withQuantity(3);
        builder.addToBasket()
                .withItem("banana");

        builder.addNewBasket();

        builder.addNewBasket()
                .withItem("avocado")
                .withQuantity(12);

        return builder.build();
    }
}
