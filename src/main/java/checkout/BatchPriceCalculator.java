package checkout;


import checkout.data.Item;
import checkout.data.PriceList;

public class BatchPriceCalculator {
    public static CheckoutServer.BatchPrice calculate(Batch batch, PriceList priceList) {
        CheckoutServer.BatchPrice in = new CheckoutServer.BatchPrice();

        for (int i=0; i<batch.getBasketCount(); i++){
            Basket basket = batch.getBasket(i);
            Money basketTotal = new Money();

            for (int j = 0; j<basket.getItemCount(); j++){
                Item item = basket.getBasketItem(j);
                Entry entry = priceList.findEntry(item.getItemCode());

                basketTotal = basketTotal.add(entry.getUnitPrice().multiply(item.getQuantity()));
            }

            in.batch.baskets.put(basket.getBasketId(), basketTotal);
        }

        return in;
    }
}
