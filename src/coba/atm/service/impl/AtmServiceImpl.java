package coba.atm.service.impl;

import coba.atm.constants.AppConstants;
import coba.atm.domain.Account;
import coba.atm.domain.list.AccountList;
import coba.atm.service.AtmServiceAbs;
import coba.atm.service.PasswordEncoder;
import coba.atm.util.PasswordUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static coba.atm.constants.AppConstants.*;

public class AtmServiceImpl extends AtmServiceAbs {
    public AtmServiceImpl(PasswordEncoder pwe) {
        super(pwe);
    }

    public AtmServiceImpl() {
        super(new PasswordUtil());
    }

    @Override
    public void transactionScreen(Scanner sc, String accNum) {
        boolean exitFlag = false;
        while (!exitFlag) {
            System.out.print(TRX_SCREEN);
            String input = sc.nextLine();
            if (input.equals(WITHDRAW)) {
                if (withdrawScreen(sc, accNum)) {
                    exitFlag = true;
                }
            } else if (input.equals(TRANSFER)) {
                if (transferScreen(sc, accNum)) {
                    exitFlag = true;
                }
            } else if (input.isEmpty() || input.equals(EXIT)) {
                exitFlag = true;
            }
        }
    }

    /**
     * Transfer screen.
     * Rule:
     * - Valid input:
     *   1. Transfer destination (valid account number).
     *   2. Transfer amounts minimum $1 maximum $1000.
     *   3. Reference number input (random 6 digits number), need to input same number as confirmation.
     *   4. Confirmation 1:yes / 2:cancel (default 2).
     *   5. Quit/continue to trx screen 1:continue / 2:quit (to welcome screen).
     * 
     * @return true (Quit to welcome screen) / false (Continue to trx screen)
     */
    @Override
    public boolean transferScreen(Scanner sc, String accNum) {
        boolean exitStatus = false;
        // trf destination
        System.out.print(TRANSFER_SCREEN);
        String dest = sc.nextLine();
        if (SIMULATED_ESC_BTN.equals(dest)) {
            return exitStatus;
        } else if (!dest.matches(NUMERIC_ONLY_RGX) || AccountList.findByAccountNumber(dest) == null) {
            System.out.println(ERR_INVALID_ACCOUNT);
            return exitStatus;
        }
        // trf amounts
        System.out.print(TRANSFER_SCREEN2);
        String transfer = sc.nextLine();
        if (!"".equals(transfer) && !transfer.matches(NUMERIC_ONLY_RGX)) {
            System.out.println(ERR_INVALID_AMOUNTS);
        } else if (new BigDecimal(transfer).compareTo(new BigDecimal(1)) < 0) {
            System.out.println(ERR_MINIMUM_AMOUNT_1);
        } else if (new BigDecimal(transfer).compareTo(new BigDecimal(1000)) > 0) {
            System.out.println(ERR_MAXIMUM_AMOUNT_1000);
        } else {
            Account account = AccountList.findByAccountNumber(accNum);
            BigDecimal trfAmt = new BigDecimal(transfer);
            if (account.getBalance().compareTo(trfAmt) < 0) {
                System.out.println(AppConstants.ERR_INSUFFICIENT_BALANCE + transfer);
            } else {
                String refNum = PasswordUtil.generateRandom(6);
                System.out.printf(TRANSFER_SCREEN3, refNum);
                // reference number confirmation
                if (!sc.nextLine().equals(refNum)) {
                    System.out.println(ERR_REFERENCE_NUM);
                } else {
                    // confirm trf
                    System.out.printf(TRANSFER_SCREEN4, dest, trfAmt, refNum);
                    if (CONFIRM.equals(sc.nextLine())) {
                        // deduct balance, and execute transfer
                        account.setBalance(account.getBalance().subtract(trfAmt));
                        Account destAcc = AccountList.findByAccountNumber(dest);
                        destAcc.setBalance(destAcc.getBalance().add(trfAmt));
                        summaryScreen(account.getBalance(), trfAmt, dest, refNum);
                        if (!CONTINUE.equals(sc.nextLine())) {
                            exitStatus = true;
                        }
                    }
                }
            }
        }
        return exitStatus;
    }

    private static final String[] FIXED_AMT = { "10", "50", "100", "Other" };

    /**
     * Withdraw screen.
     * Valid input 1-5 (default 5):
     * 1. Withdraw fixed ammounts of $10.
     * 2. Withdraw fixed ammounts of $50.
     * 3. Withdraw fixed ammounts of $100.
     * 4. Other amounts.
     * 5. Quit/exit to trx screen.
     */
    @Override
    public boolean withdrawScreen(Scanner sc, String accNum) {
        boolean exitFlag = false;
        boolean exitTxScreen = false;
        while (!exitFlag) {
            System.out.print(WITHDRAW_SCREEN);
            String input = sc.nextLine();
            Account account = AccountList.findByAccountNumber(accNum);
            if (input.matches(VALID_WITHDRAW_RGX) || input.isEmpty()) {
                exitFlag = true;
                if (input.matches(VALID_FIXED_WITHDRAW_RGX)) {
                    exitTxScreen = withdrawFixedAmt(account, FIXED_AMT[Integer.parseInt(input) - 1], sc);
                } else if (input.equals(OTHER)) {
                    System.out.print(OTHER_WD_SCREEN);
                    input = sc.nextLine();
                    exitTxScreen = withdrawDynamicAmt(account, input, sc);
                }
            }
        }
        return exitTxScreen;
    }

    /**
     * Withdraw fixed amount.
     * Rule:
     * - Withdraw with certain ammount, if the deduction > balance return err message.
     * 
     * @param account
     * @param deduct
     * @param sc
     * @return true (Quit to welcome screen) / false (Continue to trx screen)
     */
    private boolean withdrawFixedAmt(Account account, String deduct, Scanner sc) {
        BigDecimal bdDeduct = new BigDecimal(deduct);
        if (account.getBalance().compareTo(bdDeduct) >= 0) {
            account.setBalance(account.getBalance().subtract(bdDeduct));
            summaryScreen(account.getBalance(), bdDeduct);
            return !sc.nextLine().equals(CONTINUE);
        }
        System.out.println(ERR_INSUFFICIENT_BALANCE + bdDeduct.subtract(account.getBalance()).toPlainString());
        return false;
    }

    /**
     * Withdraw dynamic amounts.
     * Rules:
     * - Only accept nunber inputs.
     * - Can only withdraw multiplication of 10.
     * - Max withdraw amounts at once is $1000.
     * 
     * @param account
     * @param input
     * @param sc
     * @return true (Quit to welcome screen) / false (Continue to trx screen)
     */
    private boolean withdrawDynamicAmt(Account account, String input, Scanner sc) {
        if (input.matches(NUMERIC_ONLY_RGX)
                && new BigDecimal(input).remainder(new BigDecimal(10)).compareTo(BigDecimal.ZERO) == 0) {
            if (new BigDecimal(input).compareTo(new BigDecimal(1000)) > 0) {
                System.out.println(ERR_MAX_AMOUNT_WITHDRAW_1000);
            } else {
                return withdrawFixedAmt(account, input, sc);
            }
        } else {
            System.out.println(ERR_INVALID_AMOUNTS);
        }
        return false;
    }

    /**
     * Validate account on welcome screen.
     *
     * @param input
     * @param fieldName
     * @return
     */
    @Override
    public boolean validateAcc(String input, String fieldName) {
        if (input.length() < 6) {
            System.out.printf(ERR_MINIMUM_6_LENGTH, fieldName);
            return false;
        } else if (!input.matches(NUMERIC_ONLY_RGX)) {
            System.out.printf(ERR_DIGIT, fieldName);
            return false;
        }
        return true;
    }

    /**
     * Withdraw summary screen.
     * Show detail of latest withdraw transaction.
     * 
     * @param balance
     * @param deduct
     */
    private void summaryScreen(BigDecimal balance, BigDecimal deduct) {
        System.out.printf(SUMMARY_SCREEN, LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_PATTERN)), deduct,
                balance);
    }

    /**
     * Transfer summary screen.
     * Show detail of latest trf transaction.
     * 
     * @param balance
     * @param deduct
     * @param destination
     * @param reference
     */
    private void summaryScreen(BigDecimal balance, BigDecimal deduct, String destination, String reference) {
        System.out.printf(SUMMARY_SCREEN_TRF, destination, deduct, reference, balance);
    }

}
