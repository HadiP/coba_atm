package coba.atm.service;

public abstract class AtmServiceAbs implements AtmService {

    private final PasswordEncoder pwe;

    protected AtmServiceAbs(PasswordEncoder pwe) {
        this.pwe = pwe;
    }

    public boolean login(String pw, String rawPw) {
        return pwe.match(pw, rawPw);
    }
}
