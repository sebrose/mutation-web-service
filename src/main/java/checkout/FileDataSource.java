package checkout;

import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class FileDataSource implements MyDataSource {
    private Map<String, String> testData = new HashMap<String, String>();

    @Override
    public String getStringData(String location) throws IOException {
        System.out.println("Get from : " + location);

        if (testData.containsKey(location)) {
            System.out.println("Cached: " + testData.get(location));
            return testData.get(location);
        }

        URL url = this.getClass().getResource(location);
        byte[] encoded = Files.readAllBytes(Paths.get(url.getPath()));
        return Charset.defaultCharset().decode(ByteBuffer.wrap(encoded)).toString();
    }

    @Override
    public void setStringData_FOR_TEST_ONLY(Integer round, String location, String data) {
        String key = String.format(CheckoutServer.LOCATION_FORMAT, round, location);

        testData.put(key, data);

        System.out.println("Set: " + key + " -> " + data);

    }
}
