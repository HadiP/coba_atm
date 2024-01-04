package coba.atm.view;

import coba.atm.constants.State;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static coba.atm.constants.AppConstants.*;

public class SummaryView {

    /**
     * Withdraw summary screen.
     * Show detail of latest withdraw transaction.
     *
     * @param balance {@link coba.atm.domain.Account} balance
     * @param deduct deduction of balance
     */
    public static State summaryScreen(BigDecimal balance, String deduct) {
        System.out.printf(SUMMARY_SCREEN, LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_PATTERN)), deduct,
                balance);
        String resp = new Scanner(System.in).nextLine();
        System.out.println();
        if(CONTINUE.equals(resp)){
            return State.TRANSACTION;
        } else {
            return State.LOGIN;
        }
    }

    /**
     * Transfer summary screen.
     * Show detail of latest trf transaction.
     *
     * @param balance {@link coba.atm.domain.Account} balance
     * @param deduct deduction of balance
     * @param destination Account.accountNumber used as destination of transfer
     * @param reference reference number
     */
    public static State summaryScreen(BigDecimal balance, String deduct, String destination, String reference) {
        System.out.printf(SUMMARY_SCREEN_TRF, destination, deduct, reference, balance);
        String resp = new Scanner(System.in).nextLine();
        System.out.println();
        if(CONTINUE.equals(resp)){
            return State.TRANSACTION;
        } else {
           return State.LOGIN;
        }
    }

}
