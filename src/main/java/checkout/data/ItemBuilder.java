package checkout.data;


public class ItemBuilder {
    private String itemCode = "DefaultCode";
    private Integer quantity = 1;

    public Item build() {
        return new Item(itemCode, quantity);
    }

    public ItemBuilder withItemCode(String itemCode) {
        this.itemCode = itemCode;
        return this;
    }

    public ItemBuilder withQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }
}
