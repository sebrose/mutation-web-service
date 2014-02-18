package checkout.data;

public class RequirementsGenerator {
    public static String forRound(int round){
        switch (round){
            case 0:
                return round0();
            case 1:
                return round1();
            case 2:
                return round2();
            default:
                return null;
        }
    }

    public static String round0() {
        return
                "Round 0\n" +
                "GET the URL /Checkout/Batch/your_team_name to retrieve a batch of baskets that you need to calculate the price of.\n" +
                "Each basket has a unique ID\n" +
                "In this round the batch will only contain a single basket\n " +
                "In this round the basket will only contain a single item\n" +
                "The item will be a banana and each banana costs 25c\n\n" +
                "You PUT the result to /Checkout/Batch/your_team_name\n" +
                "The JSON payload should look something like this:\n" +
                "    {\"batch\":{\"mutationScore\":85,\"baskets\":{\"1\":{\"dollars\":0,\"cents\":75}}}}\n" +
                "                               basket ID from batch ^\n"+
                "                                            total cost of basket     ^            ^\n" +
                "                                             (which you have calculated)\n";
    }

    private static String round1() {
        return
                "Round 1\n" +
                        "GET the URL /Checkout/Batch/your_team_name to retrieve a batch of baskets that you need to calculate the price of.\n" +
                        "Each basket has a unique ID\n" +
                        "In this round the batch will contain multiple baskets\n " +
                        "In this round the basket will contain multiple items\n" +
                        "All items cost 25 cents\n\n" +
                        "You PUT the result to /Checkout/Batch/your_team_name\n" +
                        "The JSON payload should look something like this:\n" +
                        "    {\"batch\":{\"mutationScore\":85,\"baskets\":{\"1\":{\"dollars\":0,\"cents\":75},\"2\":{\"dollars\":7,\"cents\":25}}}}\n" +
                        "                               basket ID from batch ^                                  ^\n"+
                        "                                       total cost of each basket     ^            ^                     ^            ^\n" +
                        "                                        (which you have calculated)\n";
    }

    private static String round2() {
        return
                "Round 2\n" +
                        "As before, but this round you need to use a price list to work out what items cost\n" +
                        "You GET this from /Checkout/PriceList/your_team_name\n";
    }
}
