package coba.atm.service.impl;

import coba.atm.constants.State;
import coba.atm.domain.Account;
import coba.atm.domain.list.AccountList;
import coba.atm.exception.ValidationErrorException;
import coba.atm.service.WithdrawService;

public class WithdrawServiceImpl implements WithdrawService {

    @Override
    public State withdrawFund(AccountList accountList, Account currentAccount, String withdrawAmount) {
        State resp;
        try {
            currentAccount.subtractBalance(withdrawAmount, State.WITHDRAW);
            //save withdraw process into "repository" of accounts
            accountList.save(currentAccount);
            resp = State.SUMMARY;
        } catch (ValidationErrorException e) {
            //return state as withdraw to retry the process
            resp = State.WITHDRAW;
        }
        return resp;
    }
}
