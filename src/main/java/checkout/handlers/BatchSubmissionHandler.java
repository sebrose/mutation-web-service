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

    public static class BatchTotalsDataOut {
        BatchPriceComparisonResult batch;
        String errorMessage;
    }

    public BatchSubmissionHandler(Gson json, RoundEntity roundEntity, MyReader batchReader, MyReader priceListReader){
        this.json = json;
        this.roundEntity = roundEntity;
        this.batchReader = batchReader;
        this.priceListReader = priceListReader;
    }

    @Override
    public JsonProcessorResultWrapper execute(HttpRequest req) {

        String teamName = Rest.param(req, "teamName");
        Team team = Team.findFirst("name=?", teamName);
        if (team == null) {
            throw new IllegalArgumentException(String.format("Unregistered team name supplied '%s'", teamName));
        }

        try {
            BatchPrice submittedTotals = json.fromJson(req.body(), BatchPrice.class);
            int currentRound = team.getCurrentRound();

            return evaluateSubmission(team, submittedTotals, currentRound);
        } catch (RuntimeException e) {
            team.addPoints(Scoring.INCORRECT_RESPONSE_POINTS);
            throw new RuntimeException(e.getMessage() + " - " + req.body()); //e;
        } finally {
            team.saveIt();
        }
    }

    private JsonProcessorResultWrapper evaluateSubmission(Team team, BatchPrice submittedTotals, int currentRound) {
        BatchTotalsDataOut out = new BatchTotalsDataOut();
        out.batch = compareSubmissionWithExpected(currentRound, submittedTotals);

        if (out.batch.allResultsCorrect()) {
            team.addPoints( Scoring.getScoreForRound(currentRound, roundEntity));
            team.setCurrentRound(currentRound + 1);
            return new JsonProcessorResultWrapper(201, json.toJson(out));
        } else {
            team.addPoints(Scoring.INCORRECT_RESPONSE_POINTS);
            return new JsonProcessorResultWrapper(400, json.toJson(out));
        }
    }

    private BatchPriceComparisonResult compareSubmissionWithExpected(int currentRound, BatchPrice submittedTotals) {
        Batch batch = BatchFactory.create(batchReader, currentRound);
        PriceList priceList = PriceListFactory.create(priceListReader, currentRound);
        BatchPrice expectedTotals = BatchPriceCalculator.calculate(batch, priceList);

        return BatchPriceComparator.check(expectedTotals, submittedTotals);
    }
}

