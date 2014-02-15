package checkout.data;

import checkout.Basket;
import checkout.Batch;

import java.util.ArrayList;
import java.util.List;

public class BatchBuilder {
    private BasketBuilder basketBuilder;
    private int nextBasketId=1;

    private List<Basket> baskets = new ArrayList<Basket>();

    public Batch build() {
        if (basketBuilder != null){
            baskets.add(basketBuilder.build());
            basketBuilder = null;
        }

        Batch batch = new Batch();
        for (Basket basket : baskets){
            batch.addBasket(basket);
        }

        return batch;

    }

    public BasketBuilder addNewBasket() {

        if (basketBuilder != null){
            baskets.add(basketBuilder.build());
        }

        basketBuilder = new BasketBuilder();
        basketBuilder.withBasketId(nextBasketId++);

        return basketBuilder;
    }

    public BasketBuilder addToBasket() {
        if (basketBuilder == null){
            basketBuilder = new BasketBuilder();
        }

        return basketBuilder;
    }
}
