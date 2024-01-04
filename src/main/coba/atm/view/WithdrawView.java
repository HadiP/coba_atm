package coba.atm.view;

import coba.atm.constants.State;
import coba.atm.domain.Account;
import coba.atm.domain.list.AccountList;
import coba.atm.service.WithdrawService;
import coba.atm.service.impl.WithdrawServiceImpl;
import coba.atm.util.Pair;

import java.util.Scanner;

import static coba.atm.constants.AppConstants.*;

public class WithdrawView {

    final WithdrawService service = new WithdrawServiceImpl();

    /**
     * Fixed amount for withdraw, "other" indicate dynamic withdraw fund.
     */
    private static final String[] FIXED_AMT = { "10", "50", "100"};

    public Pair<State, AccountList> withdrawScreen(AccountList accountList, Account currentAccount) {
        Pair<State, AccountList> resp;
        System.out.print(WITHDRAW_SCREEN);
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        if (input.matches(VALID_WITHDRAW_RGX)) {
            State s;
            String withdraw;
            if (input.matches(VALID_FIXED_WITHDRAW_RGX)) {
                withdraw = FIXED_AMT[Integer.parseInt(input) - 1];
                s = service.withdrawFund(accountList, currentAccount, withdraw);
            } else {
                System.out.print(OTHER_WD_SCREEN);
                //other amount
                withdraw = sc.nextLine();
                s = service.withdrawFund(accountList, currentAccount, input);
            }
            if(State.SUMMARY.equals(s)) {
                s = SummaryView.summaryScreen(currentAccount.getBalance(), withdraw);
            }
            resp = new Pair<>(s, accountList);
        } else {
            //back to transaction screen
            resp = new Pair<>(State.TRANSACTION, accountList);
        }
        return resp;
    }

}
