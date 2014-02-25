package uk.co.claysnow.checkout.service;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * Makes rest calls using Jersey
 *
 */
public class JerseyRestCall implements RestCall {
  
  private final Client client;
  private final String server;
  
  public JerseyRestCall(Client client, String server, int port) {
    this.client = client;
    this.server = "http://" + server + ":" + port;
  }

  @Override
  public Response get(String url) {
    WebResource webResource = client.resource(server + url);
    ClientResponse response = webResource.type("application/json").get(
        ClientResponse.class);
    return response(response);
  }

  @Override
  public Response put(String url, String json) {
    WebResource webResource = client.resource(server + url);

    ClientResponse clientResponse = webResource.type("application/json").put(
        ClientResponse.class, json);

    return response(clientResponse);
  }
  
  private Response response(ClientResponse response) {
    return new Response(response.getStatus(), response.getEntity(String.class));
  }

}
