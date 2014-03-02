package checkout;

import com.google.gson.Gson;

public class BatchFactory {
    private static Batch testBatch;
    private static Gson json = new Gson();

    public static Batch create(MyReader dataReader, int round) {
        if (testBatch != null) {
            return testBatch;
        }

        Batch batch = json.fromJson(dataReader.getForRound(round), Batch.class);
        return batch;
    }

    public static void WE_ARE_TESTING_WITH(Batch testBatch_) {
        testBatch = testBatch_;
    }
}
