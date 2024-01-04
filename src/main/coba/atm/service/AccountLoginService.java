package coba.atm.service;

import coba.atm.constants.State;
import coba.atm.domain.Account;
import coba.atm.domain.list.AccountList;
import coba.atm.util.Pair;

public interface AccountLoginService {

    Pair<State, Account> login(AccountList accountList, String accountNumber, String pin);

}
