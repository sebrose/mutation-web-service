package checkout.data;

public class Item {
    private final String itemCode;
    private final Integer quantity;
    private final Float weight;

    public Item(String itemCode, Integer quantity, Float weight) {
        this.itemCode = itemCode;
        this.quantity = quantity;
        this.weight = weight;
    }


    @Override
    public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof Item) {
            Item that = (Item) other;
            result = (this.itemCode.equals(that.itemCode)
                    && this.quantity == that.quantity);
        }
        return result;
    }

    @Override
    public String toString() {
        return String.format("[%s %d]", itemCode, quantity);
    }

    public String getItemCode() {
        return itemCode;
    }

    public Float getAmount() {
        if (quantity != null) {
            return quantity.floatValue();
        }

        return weight;
    }
}
