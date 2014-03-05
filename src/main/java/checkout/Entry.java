package checkout;

public class Entry {
    private final String itemCode;
    private final Money unitPrice;
    private final Money kiloPrice;

    public Entry(String itemCode, Money unitPrice, Money kiloPrice) {
        this.itemCode = itemCode;
        this.unitPrice = unitPrice;
        this.kiloPrice = kiloPrice;
    }

    public String getItemCode() {
        return itemCode;
    }

    public Money getPrice() {
        if (unitPrice != null) {
            return unitPrice;
        }

        return kiloPrice;
    }
}
