package checkout.data;

import checkout.DatabaseCredentials;
import org.javalite.activejdbc.Base;

public class DatabaseConnectionInitialiser {
    public DatabaseConnectionInitialiser() {

        if (!Base.hasConnection()){
            Base.open(
                    DatabaseCredentials.driver,
                    DatabaseCredentials.connectionString,
                    DatabaseCredentials.user,
                    DatabaseCredentials.password);
        }
    }
}