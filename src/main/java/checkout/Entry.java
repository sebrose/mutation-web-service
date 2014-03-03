package checkout;

public class Entry {
    private final String itemCode;
    private final Money unitPrice;
    private final Money kiloPrice = null;

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
