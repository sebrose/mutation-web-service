package checkout.handlers;

import checkout.Batch;
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

    public BatchRequestHandler(Gson json, MyReader dataReader) {
        this.json = json;
        this.dataReader = dataReader;
    }

    @Override
    public JsonProcessorResultWrapper execute(HttpRequest req) {

        Team team = Team.getRegisteredTeam(Rest.param(req, "teamName"));
        team.refresh();
        team.requestProcessed();

        BatchDataOut out = new BatchDataOut();
        out.batch = team.getCurrentBatch(dataReader);

        return new JsonProcessorResultWrapper(200, json.toJson(out));
    }
}


