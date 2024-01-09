package coba.atm.service.impl;

import coba.atm.constants.State;
import coba.atm.domain.Account;
import coba.atm.domain.list.AccountList;
import coba.atm.exception.AccountNotFoundException;
import coba.atm.exception.ValidationErrorException;
import coba.atm.service.AccountLoginService;
import coba.atm.util.Pair;

import java.util.Optional;

public class AccountLoginServiceImpl implements AccountLoginService {

    /**
     * Service for login, if the accountNumber & pin valid, & account found state will be updated to trx.
     * Otherwise, stay in login screen without select account.
     * @param accountList list of Account considered as repository
     * @param accountNumber {@link Account} accountNumber
     * @param pin Account's pin
     * @return pair of state & selected account
     */
    @Override
    public Optional<Account> login(AccountList accountList, String accountNumber, String pin) {
        Optional<Account> optResp;
        try {
            Account account = accountList.findByAccountNumber(accountNumber);
            if(account.validatePin(pin)){
                optResp = Optional.of(account);
            } else {
                optResp = Optional.empty();
            }
        } catch (ValidationErrorException | AccountNotFoundException ignored) {
            optResp = Optional.empty();
        }
        return optResp;
    }
}
