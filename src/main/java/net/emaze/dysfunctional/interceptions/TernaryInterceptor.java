package net.emaze.dysfunctional.interceptions;

/**
 *
 * @param <T1>
 * @param <T2>
 * @param <T3> 
 * @author rferranti
 */
public interface TernaryInterceptor<T1, T2, T3> {
    public void before(T1 first, T2 second, T3 third);
    public void after(T1 first, T2 second, T3 third);
}