import checkout.*;
import com.google.gson.Gson;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class BatchFactoryTest {

    private class TestTeam extends Team {

        private int round;

        public TestTeam(int round) {
            this.round = round;
        }

        public String getName() {
            return "TestTeam";
        }

        public void setCurrentRound(int round) {
            this.round = round;
        }

        public int getCurrentRound() {
            return round;
        }
    }

    @Test
    public void shouldRetrieveRound0Batch() {
        final int round = 0;
        Gson json = new Gson();

        MyDataSource dataSource = new MyDataSource() {
            @Override
            public String getStringData(String location) throws IOException {
                return "{\"batch\":" +
                        "{\"baskets\":[" +
                        "{\"basketId\":1,\"items\":[{\"itemCode\":\"banana\",\"quantity\":1}]}" +
                        "]}}";
            }
        };

        MyReader dataReader = new MyReader("wibble", dataSource);

        Batch batch = BatchFactory.create(dataReader, round);

        assertEquals(json.fromJson(dataReader.getForRound(round), Batch.class), batch);
    }

    @Test
    public void batchIdsShouldIncrement() {
        final int round = 0;

        MyDataSource dataSource = new MyDataSource() {
            @Override
            public String getStringData(String location) throws IOException {
                return "{\"baskets\":[" +
                        "{\"basketId\":1,\"items\":[{\"itemCode\":\"banana\",\"quantity\":1}]},"+
                        "{\"basketId\":2,\"items\":[{\"itemCode\":\"banana\",\"quantity\":2}]},"+
                        "{\"basketId\":3,\"items\":[{\"itemCode\":\"banana\",\"quantity\":1}," +
                                                    "{\"itemCode\":\"apple\",\"quantity\":3}," +
                                                    "{\"itemCode\":\"banana\",\"quantity\":1}]}]}";
            }
        };

        MyReader dataReader = new MyReader("wibble", dataSource);

        Batch batch = BatchFactory.create(dataReader, round);

        assertEquals(1, batch.getBasket(0).getBasketId());
        assertEquals(2, batch.getBasket(1).getBasketId());
    }



}
