package net.emaze.dysfunctional.delegates;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rferranti
 */
public class PipelinedAction<E> implements Action<E>, Multicasting<Action<E>> {

    private final List<Action<E>> actions = new ArrayList<Action<E>>();

    @Override
    public void perform(E value) {
        for (Action action : actions) {
            action.perform(value);
        }
    }

    @Override
    public void add(Action<E> anAction) {
        actions.add(anAction);
    }

    @Override
    public void remove(Action<E> anAction) {
        actions.remove(anAction);
    }

    @Override
    public void setFunctors(List<Action<E>> functors){
        this.actions.clear();
        this.actions.addAll(functors);
    }
}