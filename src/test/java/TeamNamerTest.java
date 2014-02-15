import org.junit.*;
import static org.junit.Assert.*;
import checkout.*;

public class TeamNamerTest {
    
    @Test
    public void nameShouldBeStrippedOfLeadingSpaces() {
        String baseName = "TeamName";
        String requestedName = "   " + baseName;
        
        String acceptedName = TeamNamer.process(requestedName);
        
        assertEquals(baseName, acceptedName);
    }
    
    @Test
    public void nameShouldBeStrippedOfTrailingSpaces() {
        String baseName = "TeamName";
        String requestedName = baseName + "   ";
        
        String acceptedName = TeamNamer.process(requestedName);
        
        assertEquals(baseName, acceptedName);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void nameContainingSpacesShouldThrow() {
        String requestedName = "Team Name";
        
        String acceptedName = TeamNamer.process(requestedName);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void nameContainingIllegalCharactersShouldThrow() {
        String requestedName = "Team&^%Name";
        
        String acceptedName = TeamNamer.process(requestedName);
    }
    
    @Test
    public void nameContainingDashAndUnderscoreShouldNotThrow() {
        String requestedName = "Team_Name-Snake";
        
        String acceptedName = TeamNamer.process(requestedName);

        assertEquals(requestedName, acceptedName);
    }
    
    @Test
    public void nameContainingNumbersShouldNotThrow() {
        String requestedName = "0123456789";
        
        String acceptedName = TeamNamer.process(requestedName);

        assertEquals(requestedName, acceptedName);
    }
}