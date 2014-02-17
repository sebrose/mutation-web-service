package checkout;

import cucumber.api.java.Before;

import checkout.data.DatabaseConnectionInitialiser;

public class ResetHooks {
    private final static DatabaseConnectionInitialiser  databaseConnectionInitiliser = new DatabaseConnectionInitialiser();
    
    @Before(order=1)
    public void reset() {
        Team.deleteAll();
        Round.deleteAll();
    }
}
