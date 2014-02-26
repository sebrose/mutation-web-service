package uk.co.claysnow.checkout.service;

public class Response {

    public final int httpResponseCode;
    public final String jsonBody;

    public Response(Integer httpResponseCode, String jsonBody){
        this.httpResponseCode = httpResponseCode;
        this.jsonBody = jsonBody;
    }

    @Override
    public String toString() {
      return "" + httpResponseCode + ": " + jsonBody;
    }
    
    

}
