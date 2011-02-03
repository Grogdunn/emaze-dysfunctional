package net.emaze.dysfunctional.delegates;

import net.emaze.dysfunctional.options.Box;
import net.emaze.dysfunctional.testing.O;
import net.emaze.dysfunctional.tuples.Triple;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author rferranti
 */
public class TernaryActionToTernaryDelegateTest {

    @Test(expected = IllegalArgumentException.class)
    public void adaptingNullActionYieldsException() {
        new TernaryActionToTernaryDelegate<O, O, O>(null);
    }

    @Test
    public void callingAdapterCallsAdapted() {
        final Box<Triple<O, O, O>> box = new Box<Triple<O, O, O>>();
        final TernaryAction<O, O, O> adaptee = new TernaryBoxingAction<O, O, O>(box);
        final TernaryDelegate<Void, O, O, O> del = new TernaryActionToTernaryDelegate<O, O, O>(adaptee);
        final O first = new O();
        final O second = new O();
        final O third = new O();

        del.perform(first, second, third);

        Assert.assertEquals(new Triple<O, O, O>(first, second, third), box.getContent());
    }

    public class TernaryBoxingAction<T1, T2, T3> implements TernaryAction<T1, T2, T3> {

        private Box<Triple<T1, T2, T3>> box;

        public TernaryBoxingAction(Box<Triple<T1, T2, T3>> box) {
            this.box = box;
        }

        @Override
        public void perform(T1 first, T2 second, T3 third) {
            box.setContent(new Triple<T1, T2, T3>(first, second, third));
        }
    }

}
