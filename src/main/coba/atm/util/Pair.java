package coba.atm.util;

/**
 * Simple tuple to accommodate return with 2 value/pair at once.
 * @param <T>
 * @param <T2>
 */
public class Pair<T, T2> {

    private final T o;
    private final T2 o2;

    public Pair(T o, T2 o2) {
        this.o = o;
        this.o2 = o2;
    }

    public Object getO() {
        return o;
    }

    public Object getO2() {
        return o2;
    }
}
