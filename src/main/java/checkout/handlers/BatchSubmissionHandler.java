package checkout.handlers;

import checkout.*;
import checkout.data.BatchPrice;
import com.google.gson.Gson;
import org.webbitserver.HttpRequest;
import org.webbitserver.rest.Rest;

public class BatchSubmissionHandler implements JsonProcessor {
    private Gson json;
    private RoundEntity roundEntity;
    private MyReader batchReader;
    private MyReader priceListReader;
    private MyReader specialOfferReader;

    public static class BatchTotalsDataOut {
        BatchPriceComparisonResult batch;
        String errorMessage;
    }

    public BatchSubmissionHandler(Gson json, RoundEntity roundEntity, MyReader batchReader, MyReader priceListReader, MyReader specialOfferReader) {
        this.json = json;
        this.roundEntity = roundEntity;
        this.batchReader = batchReader;
        this.priceListReader = priceListReader;
        this.specialOfferReader = specialOfferReader;
    }

    @Override
    public JsonProcessorResultWrapper execute(HttpRequest req) {
        Team team = Team.getRegisteredTeam(Rest.param(req, "teamName"));

        try {
//            System.out.println("Submission rcvd: " + req.body());
            BatchPrice submittedTotals = json.fromJson(req.body(), BatchPrice.class);

            return evaluateSubmission(team, submittedTotals);
        } catch (RuntimeException e) {
            team.incorrectSubmission();
            throw new RuntimeException(e.getMessage() + " - " + req.body()); //e;
        }
    }

    private JsonProcessorResultWrapper evaluateSubmission(Team team, BatchPrice submittedTotals) {
        BatchTotalsDataOut out = new BatchTotalsDataOut();
        out.batch = team.processSubmission(submittedTotals, batchReader, priceListReader, specialOfferReader);

        if (out.batch.allResultsCorrect()) {
            team.correctSubmission(roundEntity);
            return new JsonProcessorResultWrapper(201, json.toJson(out));
        } else {
            team.incorrectSubmission();
            return new JsonProcessorResultWrapper(400, json.toJson(out));
        }
    }
}

