package checkout.handlers;

import checkout.Team;
import com.google.gson.Gson;
import org.webbitserver.HttpRequest;
import org.webbitserver.rest.Rest;

public class ScoreRequestHandler implements JsonProcessor {
    private Gson json;

    public ScoreRequestHandler(Gson json){
        this.json = json;
    }

    @Override
    public JsonProcessorResultWrapper execute(HttpRequest req) {
        String teamName = Rest.param(req, "teamName");
        Team team = Team.findFirst("name=?", teamName);
        if (team == null) {
            throw new IllegalArgumentException(String.format("Unregistered team name supplied '%s'", teamName));
        }

        team.refresh();
        return new JsonProcessorResultWrapper(200, String.format("{\"score\": %d}", team.getScore()));
    }
}


