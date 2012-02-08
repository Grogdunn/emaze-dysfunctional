package net.emaze.dysfunctional.interceptions;

import java.util.Iterator;
import net.emaze.dysfunctional.contracts.dbc;
import net.emaze.dysfunctional.dispatching.delegates.TernaryDelegate;

/**
 * Composes a delegate with an iterator of interceptors.
 *
 * (delegate ° interceptor1 ° interceptor2)
 *
 * @param <R> the delegate result type
 * @param <T1> the delegate first parameter type
 * @param <T2> the delegate second parameter type
 * @param <T3> the delegate third parameter type
 * @author rferranti
 */
public class TernaryInterceptorChain<R, T1, T2, T3> implements TernaryDelegate<R, T1, T2, T3> {

    private final TernaryDelegate<R, T1, T2, T3> composed;

    public <I extends TernaryInterceptor<T1, T2, T3>> TernaryInterceptorChain(TernaryDelegate<R, T1, T2, T3> innermost, Iterator<I> chain) {
        dbc.precondition(innermost != null, "innermost delegate cannot be null");
        dbc.precondition(chain != null, "chain cannot be null");
        TernaryDelegate<R, T1, T2, T3> current = innermost;
        while (chain.hasNext()) {
            current = new TernaryInterceptorAdapter<R, T1, T2, T3>(chain.next(), current);
        }
        this.composed = current;
    }

    @Override
    public R perform(T1 first, T2 second, T3 third) {
        return composed.perform(first, second, third);
    }
}
