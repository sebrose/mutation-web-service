package checkout;

public class TeamFactory {
    private static Team testTeam;
    
    public static Team create(String name) {
        String acceptedName = TeamNamer.process(name);

        if (testTeam != null) {
            return testTeam;
        }

        Team team = new Team(acceptedName);
        
        try {
            team.saveIt();
        } catch (org.javalite.activejdbc.DBException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        
        return team;
    }
    
    public static void WE_ARE_TESTING_WITH(Team testTeam_) {
        testTeam = testTeam_;
    }
}