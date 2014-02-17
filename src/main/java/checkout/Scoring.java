package checkout;

import java.util.HashMap;
import java.util.Map;

public class Scoring {
    public static final int REGISTRATION_POINTS = 10;
    public static final int INCORRECT_RESPONSE_POINTS = -1;

    private static class ScoreStructure {

        public int maximumPoints;
        public int minimumPoints;
        public int decrementBy;
        public int currentPoints;

        public ScoreStructure(int max, int min, int dec){
            this(max, min, dec, max);
        }

        public ScoreStructure(int max, int min, int dec, int current){
            maximumPoints = max;
            currentPoints = current;
            minimumPoints = min;
            decrementBy= dec;

        }

    }
    private static Map<Integer, ScoreStructure> scoreStructure = new HashMap<Integer, ScoreStructure>();

    public static void reset() {
        scoreStructure = new  HashMap<Integer, ScoreStructure>();
        initialise(new PersistentEntity<Integer, Integer>() {

            @Override
            public void update(Integer id, Integer value) {}

            @Override
            public Integer get(Integer roundNumber) {
                Round round = Round.findFirst("number = ?", roundNumber);
                return (round == null ? 99999 : round.getPoints());
            }
        });
    }

    public static void addRound(Integer roundNumber, int max, int min, int dec, PersistentEntity<Integer, Integer> entity) {
        int current = Math.min(max, entity.get(roundNumber));

        scoreStructure.put(roundNumber, new ScoreStructure(max, min, dec, current));
    }

    public static void resetCurrentScore(int round, int points) {
        scoreStructure.get(round).currentPoints = points;
    }

    public static int getScoreForRound(Integer roundNumber, PersistentEntity<Integer, Integer> entity) {
        ScoreStructure scoreStructureForRound = scoreStructure.get(roundNumber);
        int score = scoreStructureForRound.currentPoints;

        int pointsAvailable
                = Math.max(
                    scoreStructureForRound.minimumPoints,
                    scoreStructureForRound.currentPoints - scoreStructureForRound.decrementBy);

        scoreStructureForRound.currentPoints = pointsAvailable;

        entity.update(roundNumber, pointsAvailable);

        return score;
    }

    public static void initialise(PersistentEntity<Integer, Integer> entity) {
        addRound(0, 50, 10, 5, entity);
        addRound(1, 100, 10, 10, entity);
        addRound(2, 100, 10, 10, entity);
        addRound(3, 100, 10, 10, entity);
        addRound(4, 150, 20, 10, entity);
        addRound(5, 150, 20, 10, entity);
        addRound(6, 150, 20, 10, entity);
        addRound(7, 200, 30, 20, entity);
        addRound(8, 200, 30, 20, entity);
        addRound(9, 200, 30, 20, entity);
        addRound(10, 250, 50, 25, entity);
    }



}
