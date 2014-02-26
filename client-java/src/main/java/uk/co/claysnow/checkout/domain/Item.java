package uk.co.claysnow.checkout.domain;

public final class Item {
  
  private final String  itemCode;
  private final Integer quantity;

  public Item(String itemCode, int quantity) {
    this.itemCode = itemCode;
    this.quantity = quantity;
  }

  public String getItemCode() {
    return itemCode;
  }

  public Integer getQuantity() {
    return quantity;
  }

}
