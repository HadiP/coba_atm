package coba.atm;

import coba.atm.constants.State;
import coba.atm.domain.Account;
import coba.atm.domain.list.AccountList;
import coba.atm.view.LoginView;
import coba.atm.view.TransactionView;
import coba.atm.view.TransferView;
import coba.atm.view.WithdrawView;

import java.util.Optional;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

/**
 * ATM Simulation coba.atm.App
 */
public class App {

    public static void main(String[] args) {
        AccountList accountRepo = new AccountList();
        Account currentAccount = null;
        State state = State.LOGIN;
        for (;;) {
            if(state.equals(State.LOGIN)){
                LoginView loginView = new LoginView();
                Optional<Account> optRes = loginView.loginScreen(accountRepo);
                if(optRes.isPresent()){
                    state = State.TRANSACTION;
                    currentAccount = optRes.get();
                }
            } else if(state.equals(State.TRANSACTION)){
                TransactionView txView = new TransactionView();
                state = txView.transactionScreen();
            } else if(state.equals(State.TRANSFER)){
                TransferView trfView = new TransferView();
                state = trfView.transferScreen(accountRepo, currentAccount);
            } else if(state.equals(State.WITHDRAW)){
                WithdrawView withdrawView = new WithdrawView();
                state = withdrawView.withdrawScreen(accountRepo, currentAccount);
            } else {
                break;
            }
        }
    }
}