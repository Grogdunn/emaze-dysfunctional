package net.emaze.dysfunctional.groups;

import java.util.Iterator;
import java.util.Map;
import net.emaze.dysfunctional.contracts.dbc;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A unary function indexing elements from an iterator. Indexing key is provided
 * by the passed function.
 *
 * @param <M> the output Map type parameter
 * @param <K> the key type parameter
 * @param <V> the value type parameter
 * @author rferranti
 */
public class IndexBy<M extends Map<K, V>, K, V> implements Function<Iterator<V>, M> {

    private final Function<V, K> grouper;
    private final Supplier<M> mapProvider;

    public IndexBy(Function<V, K> grouper, Supplier<M> mapProvider) {
        dbc.precondition(grouper != null, "cannot index with a null grouper");
        dbc.precondition(mapProvider != null, "cannot index with a null mapProvider");
        this.grouper = grouper;
        this.mapProvider = mapProvider;
    }

    @Override
    public M apply(Iterator<V> groupies) {
        dbc.precondition(groupies != null, "cannot index with a null iterator");
        final M grouped = mapProvider.get();
        while (groupies.hasNext()) {
            final V groupie = groupies.next();
            final K group = grouper.apply(groupie);
            grouped.put(group, groupie);
        }
        return grouped;
    }
}
