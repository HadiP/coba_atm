package coba.atm.view;

import coba.atm.constants.State;

import java.util.Scanner;

import static coba.atm.constants.AppConstants.*;

public class TransactionView {

    public State transactionScreen() {
        State s = State.TRANSACTION;
        System.out.print(TRX_SCREEN);
        String input = new Scanner(System.in).nextLine();
        if (input.equals(WITHDRAW)) {
            s = State.WITHDRAW;
        } else if (input.equals(TRANSFER)) {
            s = State.TRANSFER;
        } else if (input.isEmpty() || input.equals(EXIT)) {
            s = State.LOGIN;
        }
        return s;
    }
}
