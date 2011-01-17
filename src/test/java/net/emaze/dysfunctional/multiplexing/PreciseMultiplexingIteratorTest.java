package net.emaze.dysfunctional.multiplexing;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.emaze.dysfunctional.consumers.Consumers;
import net.emaze.dysfunctional.options.Maybe;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 *
 * @author rferranti
 */
@RunWith(Suite.class)
@SuiteClasses({
    PreciseMultiplexingIteratorTest.Functions.class,
    PreciseMultiplexingIteratorTest.Degenerations.class
})
public class PreciseMultiplexingIteratorTest {

    public static class Degenerations {

        @Test(expected = IllegalArgumentException.class)
        public void cannotCreateMultiplexingIteratorWithNullIterators() {
            new PreciseMultiplexingIterator<Object>((Iterator<Object>[]) null);
        }

        @Test(expected = UnsupportedOperationException.class)
        public void cannotRemoveFromMultiplexingIterator() {
            Iterator<Integer> first = Arrays.asList(1, 2).iterator();
            PreciseMultiplexingIterator<Integer> i = new PreciseMultiplexingIterator<Integer>(first);
            i.next();
            i.remove();
        }
    }

    public static class Functions {

        @Test
        public void hasNoNextWhenEveryIteratorIsEmpty() {
            Iterator<Integer> first = Arrays.<Integer>asList().iterator();
            Iterator<Integer> second = Arrays.<Integer>asList().iterator();
            Assert.assertFalse(new PreciseMultiplexingIterator<Integer>(first, second).hasNext());
        }

        @Test
        public void canConsumeMultiplexingIterator(){
            Iterator<Integer> odds = Arrays.asList(1,3).iterator();
            Iterator<Integer> evens = Arrays.asList(2,4).iterator();
            PreciseMultiplexingIterator<Integer> iter = new PreciseMultiplexingIterator<Integer>(odds, evens);
            List<Maybe<Integer>> got = Consumers.all(iter);
            List<Maybe<Integer>> expected = Arrays.asList(Maybe.just(1),Maybe.just(2),Maybe.just(3),Maybe.just(4));
            Assert.assertEquals(expected, got);
        }
    }
}