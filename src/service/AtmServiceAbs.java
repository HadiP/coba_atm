package service;

import java.math.BigDecimal;

public abstract class AtmServiceAbs implements AtmService {

    private final PasswordEncoder pwe;

    public AtmServiceAbs(PasswordEncoder pwe) {
        this.pwe = pwe;
    }

    public boolean login(String pw, String rawPw) {
        return pwe.match(pw, rawPw);
    }
}
