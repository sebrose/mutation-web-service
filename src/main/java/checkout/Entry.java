package checkout;

public class Entry {
    private final String itemCode;
    private final String categoryCode;
    private final Money unitPrice;
    private final Money kiloPrice;

    public Entry(String itemCode, String categoryCode, Money unitPrice, Money kiloPrice) {
        this.itemCode = itemCode;
        this.categoryCode = categoryCode;
        this.unitPrice = unitPrice;
        this.kiloPrice = kiloPrice;

        if (itemCode == null) {
            throw new IllegalArgumentException("Must have item code set");
        }

        if (categoryCode == null) {
            throw new IllegalArgumentException("Must have category code set");
        }

        if (unitPrice == null && kiloPrice == null) {
            throw new IllegalArgumentException("Must have unit or kilo price set");
        }

        if (unitPrice != null && kiloPrice != null) {
            throw new IllegalArgumentException("Cannot have both unit and kilo price set");
        }
    }

    public String getItemCode() {
        return itemCode;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public Money getPrice() {
        if (unitPrice != null) {
            return unitPrice;
        }

        return kiloPrice;
    }
}
