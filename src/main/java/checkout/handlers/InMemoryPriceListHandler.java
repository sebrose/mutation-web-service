package checkout.handlers;

import checkout.CheckoutServer;
import checkout.MyDataSource;
import com.google.gson.Gson;
import org.webbitserver.HttpRequest;
import org.webbitserver.rest.Rest;

public class InMemoryPriceListHandler implements JsonProcessor {
    private MyDataSource dataSource;

    public InMemoryPriceListHandler(Gson json, MyDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public JsonProcessorResultWrapper execute(HttpRequest req) {
        Integer round = Integer.valueOf(Rest.param(req, "round"));

        dataSource.setStringData_FOR_TEST_ONLY(round, CheckoutServer.PRICE_LIST_LOCATION, req.body());

        return new JsonProcessorResultWrapper(201, "Cached price list changed");
    }
}
