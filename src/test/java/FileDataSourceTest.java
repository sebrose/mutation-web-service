import checkout.CheckoutServer;
import checkout.FileDataSource;
import org.junit.Assert;
import org.junit.Test;

public class FileDataSourceTest {
    @Test
    public void shouldReturnTestDataIfPresent() throws Exception {
        FileDataSource source = new FileDataSource();

        source.setStringData_FOR_TEST_ONLY(98, "hello", "goodbye");

        Assert.assertEquals("goodbye", source.getStringData(String.format(CheckoutServer.LOCATION_FORMAT, 98, "hello")));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowIfNonExistentFileSpecified() throws Exception {
        FileDataSource source = new FileDataSource();

        source.getStringData("hello");
    }

    public void shouldNotThrowIfExistingFileSpecified() throws Exception {
        FileDataSource source = new FileDataSource();

        String data = source.getStringData("/definitions/round1/requirements.txt");
        Assert.assertTrue(data.length() > 0);
    }
}
