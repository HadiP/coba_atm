package coba.atm.constants;

/**
 * Screen state.
 */
public enum State {

    LOGIN("login"),
    TRANSACTION("transaction"),
    WITHDRAW("withdraw"),
    TRANSFER("transfer"),
    SUMMARY("summary");

    State(String val){
        this.val = val;
    }
    private final String val;

    @Override
    public String toString() {
        return val;
    }
}
