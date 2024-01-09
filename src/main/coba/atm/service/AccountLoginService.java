package coba.atm.service;

import coba.atm.constants.State;
import coba.atm.domain.Account;
import coba.atm.domain.list.AccountList;
import coba.atm.util.Pair;

import java.util.Optional;

public interface AccountLoginService {

    Optional<Account> login(AccountList accountList, String accountNumber, String pin);

}
