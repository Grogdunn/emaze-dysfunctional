package net.emaze.dysfunctional.order;

import java.util.Iterator;
import net.emaze.dysfunctional.contracts.dbc;

public class PeriodicIterator<T> implements Iterator<T> {

    private final PeriodicSequencingPolicy<T> sequencer;
    private T next;

    public PeriodicIterator(PeriodicSequencingPolicy<T> sequencer, T start) {
        dbc.precondition(sequencer != null, "cannot create a PeriodicIterator witha null sequencer");
        this.sequencer = sequencer;
        this.next = start;
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public T next() {
        final T result = next;
        next = sequencer.next(next);
        return result;
    }

    @Override
    public void remove() {
        //ignore
    }
}