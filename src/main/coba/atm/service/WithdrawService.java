package coba.atm.service;

import coba.atm.constants.State;
import coba.atm.domain.Account;
import coba.atm.domain.list.AccountList;
import coba.atm.util.Pair;

public interface WithdrawService {

    State withdrawFund(AccountList accountList, Account currentAccount, String withdrawAmount);

}
