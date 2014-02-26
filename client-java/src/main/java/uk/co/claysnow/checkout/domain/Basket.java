package uk.co.claysnow.checkout.domain;

import java.util.Collections;
import java.util.List;

public final class Basket {
  
  private final Integer basketId;
  private final List<Item> items;

  public Basket(Integer basketId, List<Item> items){
      this.basketId = basketId;
      this.items = items;
  }

  public Integer getBasketId() {
    return basketId;
  }

  public List<Item> getItems() {
    return Collections.unmodifiableList(items);
  }
  
}
