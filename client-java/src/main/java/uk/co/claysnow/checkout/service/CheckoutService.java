package uk.co.claysnow.checkout.service;


public class CheckoutService {

  private final RestCall  rest;

  public CheckoutService(RestCall rest) {
    this.rest = rest;
  }
  
  public Response registerTeam(String json) {
    return rest.put("/Checkout/Team", json);
  }
  
  public Response requestRequirements(String team) {
    return rest.get("/Checkout/Requirements/" + team);
  }

  public Response requestBatch(String team) {
    return rest.get("/Checkout/Batch/" + team);
  }

  public Response requestPriceList(String team) {
    return rest.get("/Checkout/PriceList/" + team);
  }

  public Response submitTotals(String team, String json) {
    return rest.put("/Checkout/Batch/" + team, json);
  }
  
  public Response requestScore(String team) {
    return rest.get("/Checkout/Score/" + team);
  }

}
