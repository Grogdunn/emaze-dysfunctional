package net.emaze.dysfunctional.numbers;

import net.emaze.dysfunctional.dispatching.delegates.BinaryDelegate;
import net.emaze.dysfunctional.numbers.policies.DividePolicy;
import net.emaze.dysfunctional.testing.O;
import org.junit.Assert;
import org.junit.Test;

public class DivideTest {

    @Test(expected = IllegalArgumentException.class)
    public void creatingDivideWithNullPolicyYieldsException() {
        new Divide<O, O, O>(null);
    }

    @Test
    public void canDivideUsingPolicy() {
        final BinaryDelegate<O, O, O> divide = new Divide<O, O, O>(new DividePolicy<O, O, O>() {

            @Override
            public O divide(O lhs, O rhs) {
                return O.YET_ANOTHER;
            }
        });
        final O got = divide.perform(O.ONE, O.ANOTHER);
        Assert.assertEquals(O.YET_ANOTHER, got);
    }
}