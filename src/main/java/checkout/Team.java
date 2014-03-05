package checkout;

import checkout.data.BatchPrice;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("teams")
public class Team extends Model {

    public Team() {
    }

    public Team(String name) {
        setString("name", name);
        setInteger("score", 0);
    }

    public static Team registerTeam(String name) {
        Team team = new Team(name);
        team.successfulRegistration();
        return team;
    }

    public static Team getRegisteredTeam(String teamName) {
        Team team = Team.findFirst("name=?", teamName);
        if (team == null) {
            throw new IllegalArgumentException(String.format("Unregistered team name supplied '%s'", teamName));
        }

        return team;
    }

    public String getName() {
        return getString("name");
    }

    public void setCurrentRound(int round) {
        setInteger("currentRound", round);
    }

    public int getCurrentRound() {
        return getInteger("currentRound");
    }

    public int getScore() {
        return getInteger("score");
    }

    private void addPoints(int points) {
        int currentPoints = getScore();
        setInteger("score", currentPoints + points);
    }

    public String getCurrentRequirements(MyReader requirementsReader) {
        return requirementsReader.getForRound(getCurrentRound());
    }

    public Batch getCurrentBatch(MyReader batchReader) {
        return BatchFactory.create(batchReader, getCurrentRound());
    }

    public PriceList getCurrentPriceList(MyReader priceListReader) {
        return PriceListFactory.create(priceListReader, getCurrentRound());
    }

    public BatchPriceComparisonResult processSubmission(BatchPrice submittedTotals, MyReader batchReader, MyReader priceListReader) {

        Batch batch = BatchFactory.create(batchReader, getCurrentRound());
        System.out.println("Batch: " + batch.toString());
        PriceList priceList = PriceListFactory.create(priceListReader, getCurrentRound());
        checkout.data.BatchPrice expectedTotals = BatchPriceCalculator.calculate(batch, priceList);

        return BatchPriceComparator.check(expectedTotals, submittedTotals);
    }

    private void successfulRegistration() {
        addPoints(Scoring.REGISTRATION_POINTS);
        saveIt();
    }

    public void correctSubmission(RoundEntity roundEntity) {
        int currentRound = getCurrentRound();
        addPoints(Scoring.getScoreForRound(currentRound, roundEntity));
        setCurrentRound(currentRound + 1);
        saveIt();
    }

    public void incorrectSubmission() {
        addPoints(Scoring.INCORRECT_RESPONSE_POINTS);
        saveIt();
    }
}
