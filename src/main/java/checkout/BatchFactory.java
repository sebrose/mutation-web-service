package checkout;

import checkout.data.BatchGenerator;

public class BatchFactory {
    private static Batch testBatch;

    public static Batch create(int round) {
        if (testBatch != null) {
            return testBatch;
        }

        return BatchGenerator.forRound(round);
    }

    public static void WE_ARE_TESTING_WITH(Batch testBatch_) {
        testBatch = testBatch_;
    }
}
