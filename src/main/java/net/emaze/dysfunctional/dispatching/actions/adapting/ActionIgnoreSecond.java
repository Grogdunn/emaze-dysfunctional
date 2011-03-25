package net.emaze.dysfunctional.dispatching.actions.adapting;

import net.emaze.dysfunctional.contracts.dbc;
import net.emaze.dysfunctional.dispatching.actions.Action;
import net.emaze.dysfunctional.dispatching.actions.BinaryAction;

/**
 * @param <T1>
 * @param <T2>
 * @author rferranti
 */
public class ActionIgnoreSecond<T1, T2> implements BinaryAction<T1, T2> {

    private final Action<T1> action;

    public ActionIgnoreSecond(Action<T1> action) {
        dbc.precondition(action != null, "cannot ignore the second parameter with a null action");
        this.action = action;
    }

    @Override
    public void perform(T1 first, T2 second) {
        action.perform(first);
    }
}
