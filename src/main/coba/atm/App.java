package coba.atm;

import coba.atm.constants.State;
import coba.atm.domain.Account;
import coba.atm.domain.list.AccountList;
import coba.atm.util.Pair;
import coba.atm.view.LoginView;
import coba.atm.view.TransactionView;
import coba.atm.view.TransferView;
import coba.atm.view.WithdrawView;

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
                Pair<State, Account> resp = loginView.loginScreen(accountRepo);
                state = (State) resp.getO();
                currentAccount = (Account) resp.getO2();
            } else if(state.equals(State.TRANSACTION)){
                TransactionView txView = new TransactionView();
                state = txView.transactionScreen();
            } else if(state.equals(State.TRANSFER)){
                TransferView trfView = new TransferView();
                Pair<State, AccountList> resp = trfView.transactionScreen(accountRepo, currentAccount);
                state = (State) resp.getO();
                //update latest value
                accountRepo = (AccountList) resp.getO2();
            } else if(state.equals(State.WITHDRAW)){
                WithdrawView withdrawView = new WithdrawView();
                Pair<State, AccountList> resp = withdrawView.withdrawScreen(accountRepo, currentAccount);
                state = (State) resp.getO();
                accountRepo = (AccountList) resp.getO2();
            } else {
                break;
            }
        }
    }
}