package checkout.handlers;

import checkout.MyReader;
import checkout.PriceList;
import checkout.PriceListFactory;
import checkout.Team;
import com.google.gson.Gson;
import org.webbitserver.HttpRequest;
import org.webbitserver.rest.Rest;

public class PricelistRequestHandler implements JsonProcessor {
    private Gson json;
    private MyReader dataReader;

    public static class PriceListDataOut {
        PriceList priceList;
        String errorMessage;
    }

    public PricelistRequestHandler(Gson json, MyReader dataReader){
        this.json = json;
        this.dataReader = dataReader;
    }

    @Override
    public JsonProcessorResultWrapper execute(HttpRequest req) {
        String teamName = Rest.param(req, "teamName");
        Team team = Team.findFirst("name=?", teamName);
        if (team == null) {
            throw new IllegalArgumentException(String.format("Unregistered team name supplied '%s'", teamName));
        }

        team.refresh();
        PriceListDataOut out = new PriceListDataOut();
        out.priceList = PriceListFactory.create(dataReader, team.getCurrentRound());
        return new JsonProcessorResultWrapper(200, json.toJson(out));
    }
}

