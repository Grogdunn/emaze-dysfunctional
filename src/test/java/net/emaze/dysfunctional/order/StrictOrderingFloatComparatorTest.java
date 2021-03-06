package net.emaze.dysfunctional.order;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author rferranti
 */
public class StrictOrderingFloatComparatorTest {

    private final StrictOrderingFloatComparator comparator = new StrictOrderingFloatComparator();

    @Test
    public void positiveZeroIsGreaterThanNegativeZero() {
        Assert.assertEquals(Order.GT.order(), comparator.compare(+0.0f, -0.0f));
    }

    @Test
    public void negativeZeroIsLesserThanPositiveZero() {
        Assert.assertEquals(Order.LT.order(), comparator.compare(-0.0f, +0.0f));
    }

    @Test
    public void oneIsGreaterThanPositiveZero() {
        Assert.assertEquals(Order.GT.order(), comparator.compare(1f, +0.0f));
    }

    @Test
    public void oneIsGreaterThanNegativeZero() {
        Assert.assertEquals(Order.GT.order(), comparator.compare(1f, -0.0f));
    }

    @Test
    public void negativeOneIsLesserThanPositiveZero() {
        Assert.assertEquals(Order.LT.order(), comparator.compare(-1f, +0.0f));
    }

    @Test
    public void positiveOneIsLesserThanPositiveZero() {
        Assert.assertEquals(Order.GT.order(), comparator.compare(+1f, -0.0f));
    }

    @Test
    public void notANumberIsEqualsToHimself() {
        Assert.assertFalse(Float.NaN == Float.NaN);
        Assert.assertEquals(Order.EQ.order(), comparator.compare(Float.NaN, Float.NaN));
    }

    @Test(expected = IllegalArgumentException.class)
    public void comparingWithNullLhsYieldsException() {
        comparator.compare(null, 0f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void comparingWithNullRhsYieldsException() {
        comparator.compare(0f, null);
    }

    @Test
    public void twoComparatorsHaveSameHashCode() {
        Assert.assertEquals(new StrictOrderingFloatComparator().hashCode(), new StrictOrderingFloatComparator().hashCode());
    }

    @Test
    public void twoComparatorsAreEquals() {
        Assert.assertEquals(new StrictOrderingFloatComparator(), new StrictOrderingFloatComparator());
    }
}
