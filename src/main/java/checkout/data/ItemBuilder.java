package checkout.data;


public class ItemBuilder {
    private String itemCode = "DefaultCode";
    private Integer quantity;
    private Float weight;

    public Item build() {
        return new Item(itemCode, quantity, weight);
    }

    public ItemBuilder withItemCode(String itemCode) {
        this.itemCode = itemCode;
        return this;
    }

    public ItemBuilder withQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public ItemBuilder withWeight(float weight) {
        this.weight = weight;
        return this;
    }
}
