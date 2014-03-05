package checkout;

import java.io.IOException;

public interface MyDataSource {
    String getStringData(String location) throws IOException;

    void setStringData_FOR_TEST_ONLY(Integer currentRound, String location, String data);
}
