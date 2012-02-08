package net.emaze.dysfunctional.multiplexing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import net.emaze.dysfunctional.contracts.dbc;
import net.emaze.dysfunctional.dispatching.logic.HasNext;
import net.emaze.dysfunctional.iterations.ReadOnlyIterator;
import net.emaze.dysfunctional.options.Maybe;
import net.emaze.dysfunctional.reductions.Any;

/**
 * A composite iterator. Composed iterators are consumed in order.
 *
 * @param <E> the element type
 * @author rferranti
 */
public class ChainIterator<E> extends ReadOnlyIterator<E> {

    private final List<Iterator<E>> iterators = new ArrayList<Iterator<E>>();

    public <T extends Iterator<E>> ChainIterator(Iterator<T> iterators) {
        dbc.precondition(iterators != null, "trying to create a ChainIterator from a null list of iterators");
        while (iterators.hasNext()) {
            this.iterators.add(iterators.next());
        }
    }

    @Override
    public boolean hasNext() {
        return iterators.isEmpty() ? false : new Any<Iterator<E>>(new HasNext<Iterator<E>>()).accept(iterators.iterator());
    }

    private static <E> Maybe<Iterator<E>> currentElement(List<Iterator<E>> iterators) {
        final Iterator<Iterator<E>> iteratorOfIterators = iterators.iterator();
        while (iteratorOfIterators.hasNext()) {
            final Iterator<E> iterator = iteratorOfIterators.next();
            if (!iterator.hasNext()) {
                iteratorOfIterators.remove();
                continue;
            }
            return Maybe.just(iterator);
        }
        return Maybe.nothing();
    }

    @Override
    public E next() {
        final Maybe<Iterator<E>> maybeElement = currentElement(iterators);
        if (maybeElement.hasValue()) {
            return maybeElement.value().next();
        }
        throw new NoSuchElementException("iterator is consumed");
    }
}
