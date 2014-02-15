package checkout;

import cucumber.api.java.After;
import cucumber.api.java.Before;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class ServerHooks {
    public static final int PORT = 8887;

    private CheckoutServer checkoutServer;

    public ServerHooks() {
    }

    @Before
    public void startServer() throws IOException, InterruptedException, ExecutionException  {
        checkoutServer = new CheckoutServer(PORT);
        checkoutServer.start();
    }

    @After
    public void stopServer() throws IOException, InterruptedException, ExecutionException  {
        checkoutServer.stop();
    }
}
