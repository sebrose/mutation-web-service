package checkout.data;

public class Item {
    private final String itemCode;
    private final Integer quantity;

    public Item(String itemCode, int quantity){
        this.itemCode = itemCode;
        this.quantity = quantity;
    }


    @Override
    public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof Item) {
            Item that = (Item) other;
            result =  (this.itemCode.equals(that.itemCode)
                        && this.quantity==that.quantity);
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

    public Integer getQuantity() {
        return quantity;
    }
}
