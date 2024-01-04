package coba.atm.service.impl;

import coba.atm.constants.State;
import coba.atm.domain.Account;
import coba.atm.domain.list.AccountList;
import coba.atm.exception.ValidationErrorException;
import coba.atm.service.TransferService;

public class TransferServiceImpl implements TransferService {

    @Override
    public State transferFund(AccountList accountList, Account currentAccount, Account destinationAcc, String transferFund) {
        State resp;
        try {
            currentAccount.subtractBalance(transferFund, State.TRANSFER);
            destinationAcc.addBalance(transferFund);
            //commit changes
            accountList.saves(currentAccount, destinationAcc);
            resp = State.SUMMARY;
        } catch (ValidationErrorException e) {
            resp = State.TRANSFER;
        }
        return resp;
    }
}
