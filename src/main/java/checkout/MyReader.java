package checkout;

import java.io.IOException;

public class MyReader {
    private MyDataSource dataSource;
    private String descriptor;

    public MyReader(String descriptor, MyDataSource dataSource) {
        this.dataSource = dataSource;
        this.descriptor = descriptor;
    }

    public String getForRound(int round) {
        String location = String.format(CheckoutServer.LOCATION_FORMAT, round, descriptor);
        try {
            String data = dataSource.getStringData(location);
            // System.out.println("Data = " + data);

            return data;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
