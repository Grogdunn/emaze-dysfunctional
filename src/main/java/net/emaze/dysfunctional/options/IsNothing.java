package net.emaze.dysfunctional.options;

import net.emaze.dysfunctional.contracts.dbc;
import net.emaze.dysfunctional.dispatching.logic.Predicate;

/**
 * Unary Predicate matching Maybe.nothing elements.
 * @param <T> the maybe type parameter
 * @author rferranti
 */
public class IsNothing<T> implements Predicate<Maybe<T>> {

    @Override
    public boolean accept(Maybe<T> element) {
        dbc.precondition(element != null, "testing IsNothing against null");
        return !element.hasValue();
    }

}
