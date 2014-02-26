package checkout.handlers;

import org.webbitserver.HttpRequest;

public interface JsonProcessor {
    JsonProcessorResultWrapper execute(HttpRequest req);
}


