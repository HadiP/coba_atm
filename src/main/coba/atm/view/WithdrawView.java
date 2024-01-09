package coba.atm.view;

import coba.atm.constants.State;
import coba.atm.domain.Account;
import coba.atm.domain.list.AccountList;
import coba.atm.service.WithdrawService;
import coba.atm.service.impl.WithdrawServiceImpl;

import java.util.Scanner;

import static coba.atm.constants.AppConstants.*;

public class WithdrawView {

    final WithdrawService service = new WithdrawServiceImpl();

    /**
     * Fixed amount for withdraw, "other" indicate dynamic withdraw fund.
     */
    private static final String[] FIXED_AMT = { "10", "50", "100"};

    public State withdrawScreen(AccountList accountList, Account currentAccount) {
        State resp = State.TRANSACTION;
        System.out.print(WITHDRAW_SCREEN);
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        if (input.matches(VALID_WITHDRAW_RGX)) {
            String withdraw;
            if (input.matches(VALID_FIXED_WITHDRAW_RGX)) {
                withdraw = FIXED_AMT[Integer.parseInt(input) - 1];
                resp = service.withdrawFund(accountList, currentAccount, withdraw);
            } else {
                System.out.print(OTHER_WD_SCREEN);
                //other amount
                withdraw = sc.nextLine();
                resp = service.withdrawFund(accountList, currentAccount, input);
            }
            if(State.SUMMARY.equals(resp)) {
                resp = SummaryView.summaryScreen(currentAccount.getBalance(), withdraw);
            }
        }
        return resp;
    }

}
