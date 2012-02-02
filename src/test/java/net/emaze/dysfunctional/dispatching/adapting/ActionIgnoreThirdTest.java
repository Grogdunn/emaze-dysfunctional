package net.emaze.dysfunctional.dispatching.adapting;

import net.emaze.dysfunctional.dispatching.actions.BinaryAction;
import net.emaze.dysfunctional.dispatching.actions.BinaryNoop;
import net.emaze.dysfunctional.Spies;
import net.emaze.dysfunctional.options.Box;
import net.emaze.dysfunctional.testing.O;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author rferranti
 */
public class ActionIgnoreThirdTest {

    @Test(expected = IllegalArgumentException.class)
    public void adaptingNullAcitonYieldsException() {
        new ActionIgnoreThird<O, O, O>(null);
    }

    @Test
    public void canIgnoreThirdParameter() {
        final Box<O> param1 = Box.empty();
        final Box<O> param2 = Box.empty();
        final BinaryAction<O, O> spy = Spies.spy(new BinaryNoop<O, O>(), param1, param2);
        final ActionIgnoreThird<O, O, O> adapted = new ActionIgnoreThird<O, O, O>(spy);
        adapted.perform(O.ONE, O.ANOTHER, O.IGNORED);
        Assert.assertTrue(param1.getContent().equals(O.ONE) && param2.getContent().equals(O.ANOTHER));
    }
}
