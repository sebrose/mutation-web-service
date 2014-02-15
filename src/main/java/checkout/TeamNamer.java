package checkout;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class TeamNamer {
    public static String process(String requestedName) {
        Pattern pattern = Pattern.compile("^\\s*([0-9a-zA-Z_\\-]+)\\s*$");
        
        Matcher matcher = pattern.matcher(requestedName);
        
        if (!matcher.find()) {
            throw new IllegalArgumentException("Name can only contain alphanumerics, '_' or '-' characters");
        }
        
        return matcher.group(1);
    }
}