import checkout.*;
import checkout.data.BatchBuilder;
import checkout.data.PriceListBuilder;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

public class TeamTest {
    @Test
    public void shouldEvaluateSubmission() throws Exception {
        Team team = new Team() {
            @Override
            public int getCurrentRound() {
                return 99;
            }

        };

        Gson json = new Gson();
        BatchBuilder batchBuilder = new BatchBuilder();
        batchBuilder.addNewBasket().withItem("foobar").withQuantity(2);
        final String batchJson = json.toJson(batchBuilder.build());

        PriceListBuilder plBuilder = new PriceListBuilder();
        plBuilder.addEntry("foobar").withUnitPrice("0.50");
        final String priceListJson = json.toJson(plBuilder.build());

        MyDataSource dataSource = new FileDataSource();
        dataSource.setStringData_FOR_TEST_ONLY(99, CheckoutServer.BATCH_LOCATION, batchJson);
        dataSource.setStringData_FOR_TEST_ONLY(99, CheckoutServer.PRICE_LIST_LOCATION, priceListJson);
        dataSource.setStringData_FOR_TEST_ONLY(99, CheckoutServer.SPECIAL_OFFER_LOCATION, "{}");


        MyReader batchReader = new MyReader(CheckoutServer.BATCH_LOCATION, dataSource);
        MyReader priceListReader = new MyReader(CheckoutServer.PRICE_LIST_LOCATION, dataSource);
        MyReader offersReader = new MyReader(CheckoutServer.SPECIAL_OFFER_LOCATION, dataSource);

        BatchPriceBuilder builder = new BatchPriceBuilder();
        builder.withBasketTotal("1.00");

        BatchPriceComparisonResult result = team.processSubmission(builder.build(), batchReader, priceListReader, offersReader);

        Assert.assertTrue(result.allResultsCorrect());
    }
}
