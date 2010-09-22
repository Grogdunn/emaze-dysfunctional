package net.emaze.dysfunctional.numbers;

/**
 *
 * @author rferranti
 */
public class CircularCounter {

    private int value;
    private int limit;

    public CircularCounter(int limit) {
        this.limit = limit;
    }

    public int incrementAndGet() {
        value = (value + 1) % limit;
        return value;
    }

    public int getAndIncrement() {
        final int current = value;
        value = (current + 1) % limit;
        return current;
    }

    public int decrementAndGet(){
        value = ( value -1 ) % limit;
        return value;
    }
    
    public int getAndDecrement(){
        final int current = value;
        value = ( value -1 ) % limit;
        return current;
    }

    public int get(){
        return value;
    }
}
