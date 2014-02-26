package uk.co.claysnow.checkout.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import uk.co.claysnow.checkout.domain.Basket;
import uk.co.claysnow.checkout.domain.Batch;
import uk.co.claysnow.checkout.domain.Item;
import uk.co.claysnow.checkout.domain.Team;
import uk.co.claysnow.checkout.service.JsonHandler;

public class JsonHandlerTest {
  
  private JsonHandler testee = new JsonHandler();
  

  @Test
  public void shouldDeserialiseItems() {
    Item actual = testee.fromJson("{\"itemCode\":\"foo\",\"quantity\":42}", Item.class);
    assertEquals("foo", actual.getItemCode());
    assertEquals(Integer.valueOf(42), actual.getQuantity());
  }
  
  @Test
  public void shouldDeserialiseBaskets() {
    Basket actual = testee.fromJson("{\"basketId\":12,\"items\":[{\"itemCode\":\"banana\",\"quantity\":1}]}", Basket.class);
    assertEquals(Integer.valueOf(12), actual.getBasketId());
    assertEquals(1, actual.getItems().size());
  }

  @Test
  public void shouldDeserialiseBatches() {
    Batch actual = testee.fromJson("{\"batch\":{\"baskets\":[{\"basketId\":1,\"items\":[{\"itemCode\":\"banana\",\"quantity\":1}]}]}}", Batch.class);
    assertEquals(1, actual.getBaskets().size());
  }  
  
  @Test
  public void shouldDeserialiseTeams() {
    Team actual = testee.fromJson("{\"id\":41,\"acceptedName\":\"teamName\"}", Team.class);
    assertEquals("teamName", actual.getName());
  }
  
  @Test
  public void shouldSerialiseTeams() {
    String name = "a team name";
    Team t = new Team(name);
    String json = testee.toJson(t, Team.class);
    assertTrue(json.contains(name));
  }
  
}
