package checkout;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("teams")
public class Team extends Model {

    public Team() {}
    
    public Team(String name) {
        setString("name", name);
    }
    
    public String getName() {
        return getString("name");
    }

    public void setCurrentRound(int round) {
        setInteger("currentRound", round);
    }

    public int getCurrentRound() {
        return getInteger("currentRound");
    }
}
