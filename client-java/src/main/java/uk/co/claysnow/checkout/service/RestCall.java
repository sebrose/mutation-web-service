package uk.co.claysnow.checkout.service;

public interface RestCall {
  
  Response get(String url);
  
  Response put(String url, String json);

}
