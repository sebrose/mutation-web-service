package uk.co.claysnow.checkout.domain;

import java.util.Collections;
import java.util.List;


public final class Batch {
  
    private final List<Basket> baskets;
    
    public Batch(List<Basket> baskets) {
      this.baskets = baskets;
    }

    public List<Basket> getBaskets() {
      return Collections.unmodifiableList(baskets);
    }
    
}
