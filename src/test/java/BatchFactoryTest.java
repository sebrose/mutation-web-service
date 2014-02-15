import checkout.Batch;
import checkout.BatchFactory;
import checkout.Team;
import checkout.data.BatchGenerator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BatchFactoryTest {

    private class TestTeam extends Team {

        private int round;

        public TestTeam(int round) {
            this.round = round;
        }

        public String getName() {
            return "TestTeam";
        }

        public void setCurrentRound(int round) {
            this.round = round;
        }

        public int getCurrentRound() {
            return round;
        }
    }

    @Test
    public void shouldRetrieveRound1Batch() {
        final int round = 0;

        Team testTeam = new TestTeam(round);

        Batch batch = BatchFactory.create(round);

        assertEquals(BatchGenerator.round0(), batch);
    }

    @Test
    public void batchIdsShouldIncrement() {
        final int round = 1;

        Team testTeam = new TestTeam(round);

        Batch batch = BatchFactory.create(round);

        assertEquals(1, batch.getBasket(0).getBasketId());
        assertEquals(2, batch.getBasket(1).getBasketId());
    }



}
