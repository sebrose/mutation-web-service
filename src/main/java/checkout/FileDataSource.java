package checkout;

import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileDataSource implements MyDataSource{
    @Override
    public String getStringData(String location) throws IOException {
        URL url = this.getClass().getResource(location);
        byte[] encoded = Files.readAllBytes(Paths.get(url.getPath()));
        return Charset.defaultCharset().decode(ByteBuffer.wrap(encoded)).toString();
    }
}
