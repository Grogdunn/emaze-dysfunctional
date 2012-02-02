package net.emaze.dysfunctional.collections;

import java.util.Map.Entry;
import net.emaze.dysfunctional.contracts.dbc;
import net.emaze.dysfunctional.dispatching.delegates.Delegate;
import net.emaze.dysfunctional.tuples.Pair;

public class EntryToPair<K, V> implements Delegate<Pair<K, V>, Entry<K, V>> {

    @Override
    public Pair<K, V> perform(Entry<K, V> entry) {
        dbc.precondition(entry != null, "canno transform a null entry to a pair");
        return Pair.of(entry.getKey(), entry.getValue());
    }
}
