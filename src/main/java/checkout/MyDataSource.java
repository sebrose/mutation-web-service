package checkout;

import java.io.IOException;

public interface MyDataSource {
    String getStringData(String location) throws IOException;
}
