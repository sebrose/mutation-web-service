package checkout.handlers;

import checkout.Batch;
import checkout.BatchFactory;
import checkout.MyReader;
import checkout.Team;
import com.google.gson.Gson;
import org.webbitserver.HttpRequest;
import org.webbitserver.rest.Rest;

public class BatchRequestHandler implements JsonProcessor {
    private Gson json;
    private MyReader dataReader;

    public static class BatchDataOut {
        Batch batch;
        String errorMessage;
    }

    public BatchRequestHandler(Gson json, MyReader dataReader){
        this.json = json;
        this.dataReader = dataReader;
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
        out.batch = BatchFactory.create(dataReader, team.getCurrentRound());

        System.out.println("Batch = " + out.batch.toString());
        return new JsonProcessorResultWrapper(200, json.toJson(out));
    }
}


