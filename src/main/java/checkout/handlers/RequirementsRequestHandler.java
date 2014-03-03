package checkout.handlers;

import checkout.MyReader;
import checkout.Team;
import com.google.gson.Gson;
import org.webbitserver.HttpRequest;
import org.webbitserver.rest.Rest;

public class RequirementsRequestHandler implements JsonProcessor {
    private Gson json;
    private MyReader dataReader;

    public static class RequirementsOut {
        String requirements;
        String errorMessage;
    }

    public RequirementsRequestHandler(Gson json, MyReader dataReader) {
        this.json = json;
        this.dataReader = dataReader;
    }

    @Override
    public JsonProcessorResultWrapper execute(HttpRequest req) {

        Team team = Team.getRegisteredTeam(Rest.param(req, "teamName"));
        team.refresh();

        RequirementsOut out = new RequirementsOut();
        out.requirements = team.getCurrentRequirements(dataReader);

        return new JsonProcessorResultWrapper(200, json.toJson(out));
    }
}

