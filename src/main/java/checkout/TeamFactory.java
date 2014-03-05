package checkout;

public class TeamFactory {
    private static Team testTeam;

    public static Team create(String name) {
        String acceptedName = TeamNamer.process(name);

        if (testTeam != null) {
            return testTeam;
        }

        try {
            Team team = Team.registerTeam(acceptedName);
            return team;
        } catch (org.javalite.activejdbc.DBException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static void WE_ARE_TESTING_WITH(Team testTeam_) {
        testTeam = testTeam_;
    }
}