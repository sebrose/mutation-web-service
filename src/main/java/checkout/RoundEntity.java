package checkout;

public class RoundEntity implements PersistentEntity<Integer, Integer> {
    @Override
    public void update(Integer roundNumber, Integer pointsAvailable) {
        Round round = Round.findFirst("number = ?", roundNumber);
        if (round == null) {
            round = new Round(roundNumber, pointsAvailable);
        } else {
            round.setPoints(pointsAvailable);
        }
        round.saveIt();
    }

    @Override
    public Integer get(Integer roundNumber) {
        Round round = Round.findFirst("number = ?", roundNumber);
        return (round == null ? 99999 : round.getPoints());
    }
}
