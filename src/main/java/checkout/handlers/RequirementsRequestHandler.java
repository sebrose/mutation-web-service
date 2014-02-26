package checkout.handlers;

import checkout.Team;
import checkout.data.RequirementsGenerator;
import com.google.gson.Gson;
import org.webbitserver.HttpRequest;
import org.webbitserver.rest.Rest;

public class RequirementsRequestHandler implements JsonProcessor {
    private Gson json;

    public static class RequirementsOut {
        String requirements;
        String errorMessage;
    }

    public RequirementsRequestHandler(Gson json){
        this.json = json;
    }

    @Override
    public JsonProcessorResultWrapper execute(HttpRequest req) {
        RequirementsOut out = new RequirementsOut();
        String teamName = Rest.param(req, "teamName");
        Team team = Team.findFirst("name=?", teamName);
        if (team == null) {
            throw new IllegalArgumentException(String.format("Unregistered team name supplied '%s'", teamName));
        }

        team.refresh();
        out.requirements = RequirementsGenerator.forRound(team.getCurrentRound());
        return new JsonProcessorResultWrapper(200, json.toJson(out));
    }
}

