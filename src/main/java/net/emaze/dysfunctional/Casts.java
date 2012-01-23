package net.emaze.dysfunctional;

import net.emaze.dysfunctional.casts.Narrow;
import net.emaze.dysfunctional.casts.Widen;
import net.emaze.dysfunctional.dispatching.delegates.Delegate;

/**
 *
 * @author rferranti
 */
public abstract class Casts {

    public static <R, T> R widen(T value) {
        return new Widen<R, T>().perform(value);
    }

    public static <T, R extends T> Delegate<R, T> widen() {
        return new Widen<R, T>();
    }

    public static <R, T extends R> R narrow(T value) {
        return new Narrow<R, T>().perform(value);
    }

    public static <R, T extends R> Delegate<R, T> narrow() {
        return new Narrow<R, T>();
    }
}