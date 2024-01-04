package coba.atm.service;

import coba.atm.constants.State;
import coba.atm.domain.Account;
import coba.atm.domain.list.AccountList;

public interface TransferService {

    State transferFund(AccountList accountList, Account account,
                           Account destinationAcc, String transferFund);

}
