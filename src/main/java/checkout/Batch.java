package checkout;


import java.util.ArrayList;
import java.util.List;

public class Batch {
    private List<Basket> baskets = new ArrayList<Basket>();

    public int getBasketCount() {
        return baskets.size();
    }

    public void addBasket(Basket basket) {
        baskets.add(basket);
    }

    public Basket getBasket(int index){
        return baskets.get(index);
    }

    @Override
    public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof Batch) {
            Batch that = (Batch) other;
            result = this.baskets.containsAll(that.baskets) && that.baskets.containsAll(this.baskets);
        }

        return result;
    }

    @Override
    public String toString() {
        String result = "Batch [ ";

        for (Basket basket : baskets)
            result += basket.toString() + ", " ;

        result += " ] ";

        return result;
    }
}
