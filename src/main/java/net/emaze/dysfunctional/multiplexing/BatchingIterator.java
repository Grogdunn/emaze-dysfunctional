package net.emaze.dysfunctional.multiplexing;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import net.emaze.dysfunctional.contracts.dbc;
import net.emaze.dysfunctional.dispatching.delegates.Provider;
import net.emaze.dysfunctional.iterations.ReadOnlyIterator;
import net.emaze.dysfunctional.options.Box;

/**
 *
 *
 * @param <C>
 * @param <T>
 * @author rferranti
 */
public class BatchingIterator<C extends Collection<T>, T> extends ReadOnlyIterator<C> {

    private final Iterator<T> iterator;
    private final int batchSize;
    private final Provider<C> channelProvider;
    private final Box<C> prefetched = Box.empty();

    public BatchingIterator(int batchSize, Iterator<T> iterator, Provider<C> channelProvider) {
        dbc.precondition(batchSize > 0, "max channel size must be > 0");
        dbc.precondition(iterator != null, "iterator cannot be null");
        dbc.precondition(channelProvider != null, "channelProvider cannot be null");
        this.iterator = iterator;
        this.batchSize = batchSize;
        this.channelProvider = channelProvider;
    }

    @Override
    public boolean hasNext() {
        if (prefetched.isEmpty()) {
            prefetched.setContent(prefetch(iterator, batchSize));
        }
        return prefetched.getContent().size() != 0;
    }

    @Override
    public C next() {
        if (prefetched.isEmpty()) {
            prefetched.setContent(prefetch(iterator, batchSize));
        }
        if (prefetched.getContent().size() == 0) {
            throw new NoSuchElementException();
        }
        return prefetched.unload().value();
    }

    private C prefetch(Iterator<T> iter, int size) {
        final C result = channelProvider.provide();
        for (int counter = 0; counter != size && iter.hasNext(); ++counter) {
            result.add(iter.next());
        }
        return result;
    }
}