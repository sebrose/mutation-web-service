package checkout.handlers;

import checkout.*;
import checkout.data.BatchPrice;
import com.google.gson.Gson;
import org.webbitserver.HttpRequest;
import org.webbitserver.rest.Rest;

public class BatchSubmissionHandler implements JsonProcessor {
    private Gson json;
    private RoundEntity roundEntity;

    public static class BatchTotalsDataOut {
        BatchPriceComparisonResult batch;
        String errorMessage;
    }

    public BatchSubmissionHandler(Gson json, RoundEntity roundEntity){
        this.json = json;
        this.roundEntity = roundEntity;
    }

    @Override
    public JsonProcessorResultWrapper execute(HttpRequest req) {

        String teamName = Rest.param(req, "teamName");
        Team team = Team.findFirst("name=?", teamName);
        if (team == null) {
            throw new IllegalArgumentException(String.format("Unregistered team name supplied '%s'", teamName));
        }

        int pointsScored = Scoring.INCORRECT_RESPONSE_POINTS;

        try {
            int currentRound = team.getCurrentRound();

            BatchPrice submittedTotals = json.fromJson(req.body(), BatchPrice.class);

            Batch batch = BatchFactory.create(currentRound);
            PriceList priceList = PriceListFactory.create(currentRound);
            BatchPrice expectedTotals = BatchPriceCalculator.calculate(batch, priceList);

            BatchTotalsDataOut out = new BatchTotalsDataOut();
            BatchPriceComparisonResult verification = BatchPriceComparator.check(expectedTotals, submittedTotals);
            out.batch = verification;

            int responseStatus = 400;
            if (verification.allResultsCorrect()) {
                responseStatus = 201;
                pointsScored = Scoring.getScoreForRound(currentRound, roundEntity);
                team.setCurrentRound(currentRound + 1);
            }
            return new JsonProcessorResultWrapper(responseStatus, json.toJson(out));
        } finally {
            team.addPoints(pointsScored);
            team.saveIt();
        }
    }
}

