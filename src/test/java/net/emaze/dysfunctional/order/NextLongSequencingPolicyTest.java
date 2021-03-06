package net.emaze.dysfunctional.order;

import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author rferranti
 */
public class NextLongSequencingPolicyTest {

    private final NextLongSequencingPolicy policy = new NextLongSequencingPolicy();

    @Test
    public void canEvaluateNext() {
        Assert.assertEquals(Optional.of(new Long(1l)), policy.next(0l));
    }

    @Test(expected = IllegalArgumentException.class)
    public void nextOfNullYieldsException() {
        policy.next(null);
    }

    @Test
    public void twoPoliciesHaveSameHashCode() {
        Assert.assertEquals(new NextLongSequencingPolicy().hashCode(), new NextLongSequencingPolicy().hashCode());
    }

    @Test
    public void twoPoliciesAreEquals() {
        Assert.assertEquals(new NextLongSequencingPolicy(), new NextLongSequencingPolicy());
    }
}