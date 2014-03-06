import checkout.Money;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MoneyTest {

    @Test
    public void shouldMultiplyWholeDollars() {
        Money orig = new Money("1.00");

        Money result = orig.multiply(1);

        assertEquals(orig, result);
    }

    @Test
    public void shouldMultiplyDollarsAndCents() {
        Money orig = new Money("1.23");

        Money result = orig.multiply(2);

        assertEquals(new Money("2.46"), result);
    }

    @Test
    public void shouldMultiplyFractionsOfDollarsAndCents() {
        Money orig = new Money("1.00");

        Money result = orig.multiply(2.2);

        assertEquals(new Money("2.20"), result);
    }

    @Test
    public void shouldRoundUp() {
        Money orig = new Money("1.00");

        Money result = orig.multiply(1.99999);

        assertEquals(new Money("2.00"), result);
    }

    @Test
    public void shouldRoundDown() {
        Money orig = new Money("1.00");

        Money result = orig.multiply(1.00499);

        assertEquals(new Money("1.00"), result);
    }

    @Test
    public void shouldMultiplyByZero() throws Exception {
        Money orig = new Money("1.00");

        Money result = orig.multiply(0);

        assertEquals(new Money(), result);
    }
}
