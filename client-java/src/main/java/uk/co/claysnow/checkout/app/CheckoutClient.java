package uk.co.claysnow.checkout.app;

import uk.co.claysnow.checkout.domain.Batch;
import uk.co.claysnow.checkout.domain.Team;
import uk.co.claysnow.checkout.service.BadResponseException;
import uk.co.claysnow.checkout.service.CheckoutService;
import uk.co.claysnow.checkout.service.JsonHandler;
import uk.co.claysnow.checkout.service.Response;

public class CheckoutClient {
  
  private final CheckoutService service;
  private final JsonHandler jsonHandler;
  
  public CheckoutClient(CheckoutService service, JsonHandler jsonHandler) {
    this.service = service;
    this.jsonHandler = jsonHandler;
  }
  
  public Team registerTeam(String name) {
    Response response = service.registerTeam(jsonHandler.toJson(new Team(name), Team.class));
    errorIfCodeNot(201, response);
    
    // use name from remote service in case requested name was truncated
    return jsonHandler.fromJson(response.jsonBody, Team.class);
    
  }

  
  public String requestRequirements(Team team) {
    Response response = service.requestRequirements(team.getName());
    errorIfCodeNot(200, response);
    
    // use name from remote service in case requested name was truncated
    return response.jsonBody;
  }

  public Batch requestBatch(Team t) {
    Response response = service.requestBatch(t.getName());
    errorIfCodeNot(200, response);
   
    return jsonHandler.fromJson(response.jsonBody, Batch.class);    
  }

  
  private void errorIfCodeNot(int expectedCode, Response response) {
    if ( response.httpResponseCode != expectedCode ) {
      throw new BadResponseException(response.jsonBody);
    }
    
  }
  
}
