package checkout;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("teams")
public class Team extends Model {

    public Team() {}
    
    public Team(String name) {
        setString("name", name);
        setInteger("score", 0);
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

    public int getScore() {
        return getInteger("score");
    }

    public void addPoints(int points){
        int currentPoints = getScore();
        setInteger("score", currentPoints + points);
    }
}
