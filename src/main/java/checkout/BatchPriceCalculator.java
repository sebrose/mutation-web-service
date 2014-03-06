package checkout;


import checkout.data.BatchPrice;
import checkout.data.Item;

public class BatchPriceCalculator {
    public static BatchPrice calculate(Batch batch, PriceList priceList, SpecialOfferCollection offers) {
        BatchPrice in = new BatchPrice();

        for (int i = 0; i < batch.getBasketCount(); i++) {
            Basket basket = batch.getBasket(i);
            Money basketTotal = new Money();

            for (int j = 0; j < basket.getItemCount(); j++) {
                Item item = basket.getBasketItem(j);
                Entry entry = priceList.findEntry(item.getItemCode());

                basketTotal = basketTotal.add(entry.getPrice().multiply(item.getAmount()));

                offers.process(item.getItemCode(), "", item.getAmount(), entry.getPrice());
            }

            Money savings = offers.calculateSavings();
            basketTotal = basketTotal.minus(savings);

            in.batch.baskets.put(basket.getBasketId(), basketTotal);
        }

        return in;
    }
}
