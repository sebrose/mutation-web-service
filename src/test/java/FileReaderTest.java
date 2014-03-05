import checkout.MyDataSource;
import checkout.MyReader;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class FileReaderTest {
    private class TestDataSource implements MyDataSource {
        public String location;
        private String data;

        public TestDataSource(String data) {
            this.data = data;
        }

        @Override
        public String getStringData(String location) {
            this.location = location;
            return data;
        }

        @Override
        public void setStringData_FOR_TEST_ONLY(Integer round, String location, String data) {
        }
    }

    @Test
    public void shouldLookInSpecifiedLocation() throws Exception {
        final String data = "Data";

        TestDataSource dataSource = new TestDataSource(data);

        MyReader reader = new MyReader("requirements.txt", dataSource);

        assertEquals(data, reader.getForRound(0));
        assertEquals("/definitions/round0/requirements.txt", dataSource.location);
    }
}
