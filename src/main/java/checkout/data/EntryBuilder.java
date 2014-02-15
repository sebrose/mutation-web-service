package checkout.data;


import checkout.Entry;
import checkout.Money;

public class EntryBuilder {
    private String itemCode;
    private Money unitPrice;

    public Entry build() {
        return new Entry(itemCode, unitPrice);
    }

    public EntryBuilder withItemCode(String itemCode) {
        this.itemCode = itemCode;
        return this;
    }

    public EntryBuilder withPrice(String unitPrice) {
        this.unitPrice = new Money(unitPrice);
        return this;
    }
}
