package checkout.handlers;

import checkout.CheckoutServer;
import checkout.MyDataSource;
import checkout.Team;
import com.google.gson.Gson;
import org.webbitserver.HttpRequest;
import org.webbitserver.rest.Rest;

public class InMemoryBatchHandler implements JsonProcessor {
    private MyDataSource dataSource;

    public InMemoryBatchHandler(Gson json, MyDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public JsonProcessorResultWrapper execute(HttpRequest req) {
        Team team = Team.getRegisteredTeam(Rest.param(req, "teamName"));

        dataSource.setStringData_FOR_TEST_ONLY(team.getCurrentRound(), CheckoutServer.BATCH_LOCATION, req.body());

        return new JsonProcessorResultWrapper(201, "Cached batch changed");
    }
}
