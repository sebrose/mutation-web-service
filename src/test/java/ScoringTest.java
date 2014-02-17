import checkout.PersistentEntity;
import checkout.Scoring;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ScoringTest {
    private static final int DEFAULT_ROUND = 0;
    private static final int DEFAULT_MAX = 5;
    private static final int DEFAULT_MIN = 2;
    private static final int DEFAULT_DEC = 1;
    private static final PersistentEntity<Integer, Integer> DEFAULT_ENTITY = new PersistentEntity<Integer, Integer>() {
        @Override
        public void update(Integer id, Integer value) {}

        @Override
        public Integer get(Integer id) {
            return DEFAULT_MAX;
        }
    };

    @Test
    public void shouldGiveMaximum() {
        Scoring.addRound(DEFAULT_ROUND, DEFAULT_MAX, DEFAULT_MIN, DEFAULT_DEC, DEFAULT_ENTITY);

        assertEquals(Scoring.getScoreForRound(DEFAULT_ROUND, DEFAULT_ENTITY), DEFAULT_MAX);
    }

    @Test
    public void shouldDecrement() {
        Scoring.addRound(DEFAULT_ROUND, DEFAULT_MAX, DEFAULT_MIN, DEFAULT_DEC, DEFAULT_ENTITY);

        Scoring.getScoreForRound(DEFAULT_ROUND, DEFAULT_ENTITY);
        assertEquals(Scoring.getScoreForRound(DEFAULT_ROUND, DEFAULT_ENTITY), DEFAULT_MAX - DEFAULT_DEC);
    }

    @Test
    public void shouldNotDecrementBeyondMin() {
        Scoring.addRound(DEFAULT_ROUND, DEFAULT_MAX, DEFAULT_MIN, DEFAULT_DEC, DEFAULT_ENTITY);

        Scoring.getScoreForRound(DEFAULT_ROUND, DEFAULT_ENTITY);
        Scoring.getScoreForRound(DEFAULT_ROUND, DEFAULT_ENTITY);
        Scoring.getScoreForRound(DEFAULT_ROUND, DEFAULT_ENTITY);
        Scoring.getScoreForRound(DEFAULT_ROUND, DEFAULT_ENTITY);
        Scoring.getScoreForRound(DEFAULT_ROUND, DEFAULT_ENTITY);
        Scoring.getScoreForRound(DEFAULT_ROUND, DEFAULT_ENTITY);
        Scoring.getScoreForRound(DEFAULT_ROUND, DEFAULT_ENTITY);

        assertEquals(Scoring.getScoreForRound(DEFAULT_ROUND, DEFAULT_ENTITY), DEFAULT_MIN);
    }
}
