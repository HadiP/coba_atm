package coba.atm.domain.wrapper;

/**
 * Simple tuple to accommodate return with 2 value/pair at once.
 * @param <T>
 * @param <T2>
 */
public class Pair<T, T2> {

    private T o;
    private T2 o2;

    public Pair(T o, T2 o2) {
        this.o = o;
        this.o2 = o2;
    }

    public T getO() {
        return o;
    }

    public T2 getO2() {
        return o2;
    }

    public void setO(T o){
        this.o = o;
    }

    public void setO2(T2 o2){
        this.o2 = o2;
    }
}
