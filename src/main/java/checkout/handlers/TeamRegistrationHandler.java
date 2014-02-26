package checkout.handlers;

import checkout.Team;
import checkout.TeamFactory;
import com.google.gson.Gson;
import org.webbitserver.HttpRequest;

public class TeamRegistrationHandler implements JsonProcessor {
    private Gson json;

    public static class RegistrationDataIn {
        String name;
    }

    public static class RegistrationDataOut {
        long id;
        String acceptedName;
        String errorMessage;
    }

    public TeamRegistrationHandler(Gson json){
        this.json = json;
    }

    @Override
    public JsonProcessorResultWrapper execute(HttpRequest reqJson) {
        System.out.println(String.format("Body: %s", reqJson.body()));
        RegistrationDataIn body = json.fromJson(reqJson.body(), RegistrationDataIn.class);
        RegistrationDataOut out = new RegistrationDataOut();
        Team team = TeamFactory.create(body.name);
        out.id = ((Long) team.getId());
        out.acceptedName = team.getName();
        String jsonOut = json.toJson(out);
        System.out.println(String.format("Response: %s", jsonOut));
        return new JsonProcessorResultWrapper(201, jsonOut);
    }
}

