package net.emaze.dysfunctional.ranges;

import java.util.Arrays;
import java.util.Optional;
import net.emaze.dysfunctional.order.ComparableComparator;
import net.emaze.dysfunctional.order.JustBeforeNothingComparator;
import net.emaze.dysfunctional.order.NextIntegerSequencingPolicy;
import net.emaze.dysfunctional.order.Order;
import net.emaze.dysfunctional.ranges.Range.Endpoint;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author rferranti
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    DenseRangeTest.Functions.class,
    DenseRangeTest.Degenerations.class
})
public class DenseRangeTest {

    public static class Functions {

        @Test
        public void sameRangeHasSameHashcode() {
            Assert.assertEquals(RangeMother.r(0, 10).hashCode(), RangeMother.r(0, 10).hashCode());
        }

        @Test
        public void emptyRangesHaveSameHashcode() {
            final DenseRange<Integer> anEmptyRange = new DenseRange<Integer>(new NextIntegerSequencingPolicy(), new JustBeforeNothingComparator<Integer>(new ComparableComparator<Integer>()), Endpoint.Include, 10, Optional.of(10), Endpoint.Exclude);
            final DenseRange<Integer> anotherEmptyRange = new DenseRange<Integer>(new NextIntegerSequencingPolicy(), new JustBeforeNothingComparator<Integer>(new ComparableComparator<Integer>()), Endpoint.Include, 0, Optional.of(0), Endpoint.Exclude);
            Assert.assertFalse(anEmptyRange.iterator().hasNext());
            Assert.assertEquals(anEmptyRange.hashCode(), anotherEmptyRange.hashCode());
        }

        @Test
        public void emptyRangesAreEquals() {
            final DenseRange<Integer> anEmptyRange = new DenseRange<Integer>(new NextIntegerSequencingPolicy(), new JustBeforeNothingComparator<Integer>(new ComparableComparator<Integer>()), Endpoint.Include, 10, Optional.of(10), Endpoint.Exclude);
            final DenseRange<Integer> anotherEmptyRange = new DenseRange<Integer>(new NextIntegerSequencingPolicy(), new JustBeforeNothingComparator<Integer>(new ComparableComparator<Integer>()), Endpoint.Include, 0, Optional.of(0), Endpoint.Exclude);
            Assert.assertEquals(anEmptyRange, anotherEmptyRange);
        }

        @Test
        public void toStringReflectsRange() {
            Assert.assertEquals("[0-11)", RangeMother.r(0, 10).toString());
        }

        @Test
        public void canToStringOnUnboundedRange() {
            final DenseRange<Integer> unboundRange = new DenseRange<Integer>(new NextIntegerSequencingPolicy(), new JustBeforeNothingComparator<Integer>(new ComparableComparator<Integer>()), Endpoint.Include, 0, Optional.<Integer>empty(), Endpoint.Exclude);
            Assert.assertEquals("[0-...)", unboundRange.toString());
        }

        @Test
        public void rangeIsNotEqualToNull() {
            Assert.assertFalse(RangeMother.r(0, 10).equals(null));
        }

        @Test
        public void rangeIsEqualToItself() {
            DenseRange<Integer> range = RangeMother.r(0, 1);
            Assert.assertTrue(range.equals(range));
        }

        @Test
        public void rangeIsEqualToRangeWithDifferentBounds() {
            DenseRange<Integer> former = RangeMother.r(0, 1);
            DenseRange<Integer> latter = RangeMother.r(0, 2);
            Assert.assertFalse(former.equals(latter));
        }

        @Test
        public void rangeIsEqualToEquivalentRangeWithDifferentRightEndpoint() {
            DenseRange<Integer> former = RangeMother.r(Endpoint.Include, 1, 2, Endpoint.Include);
            DenseRange<Integer> latter = RangeMother.r(Endpoint.Include, 1, 3, Endpoint.Exclude);
            Assert.assertTrue(former.equals(latter));
        }

        @Test
        public void rangeIsEqualToEquivalentRangeWithDifferentLeftEndpoint() {
            DenseRange<Integer> former = RangeMother.r(Endpoint.Include, 1, 2, Endpoint.Include);
            DenseRange<Integer> latter = RangeMother.r(Endpoint.Exclude, 0, 2, Endpoint.Include);
            Assert.assertTrue(former.equals(latter));
        }

        @Test
        public void rangesWithSameBoundsHaveSameOrder() {
            DenseRange<Integer> former = RangeMother.r(0, 10);
            DenseRange<Integer> latter = RangeMother.r(0, 10);
            Assert.assertEquals(Order.EQ.order(), former.compareTo(latter));
        }

        @Test
        public void densifiedDenseRangeIsEqualsToItselfInList() {
            DenseRange<Integer> range = RangeMother.r(0, 10);
            Assert.assertEquals(Arrays.asList(range), range.densified());
        }

        @Test
        public void elementInRangeIsContained() {
            DenseRange<Integer> range = RangeMother.r(0, 2);
            Assert.assertTrue(range.contains(1));
        }

        @Test
        public void elementPriorToLowerBoundIsNotContained() {
            DenseRange<Integer> range = RangeMother.r(1, 2);
            Assert.assertFalse(range.contains(0));
        }

        @Test
        public void elementAfterUpperBoundIsNotContained() {
            DenseRange<Integer> range = RangeMother.r(1, 2);
            Assert.assertFalse(range.contains(3));
        }

        @Test
        public void upperBoundIsNotContainedIfRightOpen() {
            DenseRange<Integer> range = RangeMother.r(Endpoint.Include, 1, 2, Endpoint.Exclude);
            Assert.assertFalse(range.contains(2));
        }
    }

    public static class Degenerations {

        @Test(expected = IllegalArgumentException.class)
        public void creatingDenseRangeWithNullSequencerYieldsException() {
            new DenseRange<Integer>(null, RangeMother.comparator, Endpoint.Include, 0, Optional.of(1), Endpoint.Include);
        }

        @Test(expected = IllegalArgumentException.class)
        public void creatingDenseRangeWithNullComparatorYieldsException() {
            new DenseRange<Integer>(RangeMother.sequencer, null, Endpoint.Include, 0, Optional.of(1), Endpoint.Include);
        }

        @Test(expected = IllegalArgumentException.class)
        public void creatingDenseRangeWithNullLowerBoundYieldsException() {
            new DenseRange<Integer>(RangeMother.sequencer, RangeMother.comparator, Endpoint.Include, null, Optional.of(1), Endpoint.Include);
        }

        @Test(expected = IllegalArgumentException.class)
        public void creatingDenseRangeWithNullUpperBoundYieldsException() {
            new DenseRange<Integer>(RangeMother.sequencer, RangeMother.comparator, Endpoint.Include, 0, null, Endpoint.Include);
        }

        @Test(expected = IllegalArgumentException.class)
        public void creatingDenseRangeWithUpperBoundLesserThenLowerBoundYieldsException() {
            new DenseRange<Integer>(RangeMother.sequencer, RangeMother.comparator, Endpoint.Include, 10, Optional.of(0), Endpoint.Include);
        }

        @Test(expected = IllegalArgumentException.class)
        public void creatingWithNothingAsUpperValueAndIncludedUpperEndpointYieldsException() {
            new DenseRange<Integer>(RangeMother.sequencer, RangeMother.comparator, Endpoint.Include, 10, Optional.<Integer>empty(), Endpoint.Include);
        }

        @Test(expected = IllegalArgumentException.class)
        public void checkingForOverlapsWithNullYieldsException() {
            RangeMother.r(0, 10).overlaps(null);
        }

        @Test(expected = IllegalArgumentException.class)
        public void comparingWithNullYieldsException() {
            RangeMother.r(0, 10).compareTo(null);
        }

        @Test(expected = ClassCastException.class)
        public void callingErasureWithWrongTypeYieldsException() {
            Comparable c = RangeMother.r(0, 10);
            c.compareTo(new Object());
        }
    }
}
