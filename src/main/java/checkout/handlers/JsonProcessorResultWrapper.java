package checkout.handlers;

public class JsonProcessorResultWrapper {
    public int httpStatus;
    public String jsonResponse;

    public JsonProcessorResultWrapper(int httpStatus, String jsonResponse){
        this.httpStatus = httpStatus;
        this.jsonResponse = jsonResponse;
    }
}


