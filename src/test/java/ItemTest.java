import checkout.Money;
import checkout.data.Item;
import org.junit.Test;

public class ItemTest {
    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAllowNullUnitAndKiloPrice() throws Exception {
        new Item("stuff", null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAllowNonNullUnitAndKiloPrice() throws Exception {
        new Item("stuff", new Integer(1), new Float(2f));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAllowNullItemCode() throws Exception {
        new Item(null, new Integer(1), null);
    }
}
