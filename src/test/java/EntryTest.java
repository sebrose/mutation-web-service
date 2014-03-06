import checkout.Entry;
import checkout.Money;
import org.junit.Test;

public class EntryTest {
    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAllowNullWeightAndQuantity() throws Exception {
        new Entry("stuff", "misc", null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAllowNonNullWeightAndQuantity() throws Exception {
        new Entry("stuff", "misc", new Money(), new Money());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAllowNullDescription() throws Exception {
        new Entry(null, "misc", new Money(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAllowNullCategory() throws Exception {
        new Entry("stuff", null, new Money(), null);
    }
}
