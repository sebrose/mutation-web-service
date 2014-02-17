package checkout;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("rounds")
public class Round extends Model {

    public Round() {}

    public Round(int round, int points){
        setInteger("number", round);
        setInteger("points", points);
    }

    public int getNumber() {
        return getInteger("Number");
    }

    public int getPoints() {
        return getInteger("points");
    }

    public void setPoints(int points) {
        setInteger("points", points);
    }
}