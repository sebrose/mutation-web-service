package checkout;


import checkout.data.Item;

import java.util.ArrayList;
import java.util.List;

public class Basket {
    private final Integer basketId;
    private List<Item> items = new ArrayList<Item>();

    public Basket(Integer basketId){
        this.basketId = basketId;
    }

    public int getItemCount() {
        return items.size();
    }

    public void addItem(Item item) {
        items.add(item);
    }


    public checkout.data.Item getBasketItem(int index) {
        return items.get(index);
    }

    public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof Basket) {
            Basket that = (Basket) other;
            result = this.basketId==that.basketId && this.items.containsAll(that.items) && that.items.containsAll(this.items);
        }

        return result;
    }

    @Override
    public String toString() {
        String result = String.format("Basket(%d) [ ", basketId);

        for (Item item : items)
          result += item.toString() + ", " ;

        result += " ] ";

        return result;
    }

    public int getBasketId() {
        return basketId;
    }


}
