package net.emaze.dysfunctional.multiplexing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.emaze.dysfunctional.consumers.Consumers;
import net.emaze.dysfunctional.contracts.dbc;
import net.emaze.dysfunctional.dispatching.logic.HasNext;
import net.emaze.dysfunctional.iterations.Iterations;
import net.emaze.dysfunctional.options.MaybeIteratorTransformer;
import net.emaze.dysfunctional.options.Maybe;
import net.emaze.dysfunctional.numbers.CircularCounter;

/**
 * squared: [1,2] [a,b,c] -> just(1),just(a),just(2),just(b),nothing,just(c)
 * @param <E>
 * @author rferranti
 */
public class PreciseMultiplexingIterator<E> implements Iterator<Maybe<E>> {

    private final List<Iterator<Maybe<E>>> iterators = new ArrayList<Iterator<Maybe<E>>>();
    private final CircularCounter currentIndex;

    public <T extends Iterator<E>> PreciseMultiplexingIterator(Iterator<T> iterators) {
        dbc.precondition(iterators != null, "trying to create a PreciseMultiplexingIterator from a null iterator of iterators");
        this.iterators.addAll(Consumers.all(Iterations.transform(iterators, new MaybeIteratorTransformer<T, E>())));
        this.currentIndex = new CircularCounter(this.iterators.size());
    }

    @Override
    public boolean hasNext() {
        return Iterations.any(iterators, new HasNext<Iterator<Maybe<E>>>());
    }

    @Override
    public Maybe<E> next() {
        return iterators.get(currentIndex.getAndIncrement()).next();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Cannot remove from a PreciseMultiplexingIterator");
    }
}
