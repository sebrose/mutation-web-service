package uk.co.claysnow.checkout.service;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import uk.co.claysnow.checkout.domain.Basket;
import uk.co.claysnow.checkout.domain.Batch;
import uk.co.claysnow.checkout.domain.Item;
import uk.co.claysnow.checkout.domain.Team;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

public class JsonHandler {

  private final Gson gson;

  public JsonHandler() {
    final GsonBuilder gb = new GsonBuilder();
    gb.registerTypeAdapter(Batch.class, batchAdapter());
    gb.registerTypeAdapter(Team.class, teamAdapter());
    gb.registerTypeAdapter(Basket.class, basketCreator());
    gb.registerTypeAdapter(Item.class, itemCreator());
    this.gson = gb.create();
  }

  public <T> T fromJson(final String json, final Class<T> c) {
    return this.gson.fromJson(json, c);
  }

  public <T> String toJson(final T object, final Class<T> c) {
    return this.gson.toJson(object, c);
  }

  private static JsonDeserializer<Team> teamAdapter() {
    return new JsonDeserializer<Team>() {
      @Override
      public Team deserialize(final JsonElement json, final Type typeOfT,
          final JsonDeserializationContext context) throws JsonParseException {
        return new Team(json.getAsJsonObject().get("acceptedName")
            .getAsString());
      }

    };
  }

  private static JsonDeserializer<Batch> batchAdapter() {
    return new JsonDeserializer<Batch>() {

      @Override
      public Batch deserialize(final JsonElement json, final Type typeOfT,
          final JsonDeserializationContext context) throws JsonParseException {
        final JsonArray baskets = json.getAsJsonObject()
            .getAsJsonObject("batch").getAsJsonArray("baskets");
        final Type collectionType = new TypeToken<List<Basket>>() {
        }.getType();
        final List<Basket> bs = context.deserialize(baskets, collectionType);
        return new Batch(bs);

      }

    };
  }
  
  private static InstanceCreator<Basket> basketCreator() {
    return new InstanceCreator<Basket>() {
      @Override
      public Basket createInstance(Type type) {
        return new Basket(0, Collections.<Item>emptyList());
      }
      
    };
  }

  
  private static InstanceCreator<Item> itemCreator() {
    return new InstanceCreator<Item>() {
      @Override
      public Item createInstance(Type type) {
        return new Item("",0);
      }
      
    };
  }


}
