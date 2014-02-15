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

                System.out.println(String.format("%s x %d @ %s", item.getItemCode(), item.getQuantity(), entry.getUnitPrice()));
                basketTotal = basketTotal.add(entry.getUnitPrice().multiply(item.getQuantity()));
                System.out.println(String.format("Basket total %s", basketTotal));
            }

            System.out.println(String.format("Basket id %d,  total %s", basket.getBasketId(), basketTotal));
            in.batch.baskets.put(basket.getBasketId(), basketTotal);
        }

        return in;
    }
}
