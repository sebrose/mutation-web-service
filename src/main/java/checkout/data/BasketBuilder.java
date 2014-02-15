package checkout.data;

import checkout.Basket;

import java.util.ArrayList;
import java.util.List;

public class BasketBuilder {
    private Integer basketId;
    private ItemBuilder itemBuilder;

    private List<Item> items = new ArrayList<Item>();


    public Basket build() {
        if (itemBuilder != null){
            items.add(itemBuilder.build());
            itemBuilder = null;
        }

        Basket basket = new Basket(basketId);
        for (Item item : items){
            basket.addItem(item);
        }

        return basket;
    }

    public ItemBuilder addItem() {

        if (itemBuilder != null){
            items.add(itemBuilder.build());
        }

        itemBuilder = new ItemBuilder();

        return itemBuilder;
    }

    public ItemBuilder withItem(String itemCode) {

        return addItem().withItemCode(itemCode);
    }

    public BasketBuilder withBasketId(Integer id) {
        basketId = id;

        return this;
    }
}
