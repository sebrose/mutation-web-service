package checkout.handlers;

import checkout.MyReader;
import checkout.SpecialOfferCollection;
import checkout.Team;
import com.google.gson.Gson;
import org.webbitserver.HttpRequest;
import org.webbitserver.rest.Rest;

public class SpecialOfferRequestHandler implements JsonProcessor {
    private Gson json;
    private MyReader dataReader;

    public static class SpecialOfferDataOut {
        SpecialOfferCollection specialOffers;
        String errorMessage;
    }

    public SpecialOfferRequestHandler(Gson json, MyReader dataReader) {
        this.json = json;
        this.dataReader = dataReader;
    }

    @Override
    public JsonProcessorResultWrapper execute(HttpRequest req) {
        Team team = Team.getRegisteredTeam(Rest.param(req, "teamName"));
        team.refresh();
        team.requestProcessed();

        SpecialOfferDataOut out = new SpecialOfferDataOut();
        out.specialOffers = team.getCurrentSpecialOffers(dataReader);

        return new JsonProcessorResultWrapper(200, json.toJson(out));
    }
}
