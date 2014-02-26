package checkout.handlers;

import checkout.Batch;
import checkout.BatchFactory;
import checkout.Team;
import com.google.gson.Gson;
import org.webbitserver.HttpRequest;
import org.webbitserver.rest.Rest;

public class BatchRequestHandler implements JsonProcessor {
    private Gson json;

    public static class BatchDataOut {
        Batch batch;
        String errorMessage;
    }

    public BatchRequestHandler(Gson json){
        this.json = json;
    }

    @Override
    public JsonProcessorResultWrapper execute(HttpRequest req) {
        BatchDataOut out = new BatchDataOut();
        String teamName = Rest.param(req, "teamName");
        Team team = Team.findFirst("name=?", teamName);
        if (team == null) {
            throw new IllegalArgumentException(String.format("Unregistered team name supplied '%s'", teamName));
        }

        team.refresh();
        out.batch = BatchFactory.create(team.getCurrentRound());
        return new JsonProcessorResultWrapper(200, json.toJson(out));
    }
}


