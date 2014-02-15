package checkout;

public class Entry {
    private final String itemCode;
    private final Money unitPrice;

    public Entry(String itemCode, Money unitPrice) {
        this.itemCode = itemCode;
        this.unitPrice = unitPrice;
    }

    public String getItemCode() {
        return itemCode;
    }

    public Money getUnitPrice() {
        return unitPrice;
    }
}
