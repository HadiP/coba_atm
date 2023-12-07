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
        for (; ; ) {
            System.out.print(TRX_SCREEN);
            String input = sc.nextLine();
            if (input.equals(WITHDRAW)) {
                if (withdrawScreen(sc, accNum)) {
                    break;
                }
            } else if (input.equals(TRANSFER)) {
                if (transferScreen(sc, accNum)) {
                    break;
                }
            } else if (input.isEmpty() || input.equals(EXIT)) {
                break;
            }
        }
    }

    @Override
    public boolean transferScreen(Scanner sc, String accNum) {
        boolean exitStatus = false;
        //trf destination
        System.out.print(TRANSFER_SCREEN[0]);
        String dest = sc.nextLine();
        if (SIMULATED_ESC_BTN.equals(dest)) {
            return exitStatus;
        } else if (!dest.matches(NUMERIC_ONLY_RGX) || AccountList.findByAccountNumber(dest) == null) {
            System.out.println(ERR_INVALID_ACCOUNT);
            return exitStatus;
        }
        //trf amounts
        System.out.print(TRANSFER_SCREEN[1]);
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
                //eventStatus = EVENT_CANCELED;
            } else {
                String refNum = PasswordUtil.generateRandom(6);
                System.out.printf(TRANSFER_SCREEN[2], refNum);
                //reference number confirmation
                if (!sc.nextLine().equals(refNum)) {
                    System.out.println(ERR_REFERENCE_NUM);
                } else {
                    //confirm trf
                    System.out.printf(TRANSFER_SCREEN[3], dest, trfAmt, refNum);
                    if(CONFIRM.equals(sc.nextLine())){
                        //deduct balance, and execute transfer
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

    @Override
    public boolean withdrawScreen(Scanner sc, String accNum) {
        for (; ; ) {
            System.out.print(WITHDRAW_SCREEN);
            String input = sc.nextLine();
            Account account = AccountList.findByAccountNumber(accNum);
            if (input.equals(WD10)) {
                BigDecimal deduct = new BigDecimal(10);
                account.setBalance(withdraw(account.getBalance(), deduct));
                summaryScreen(account.getBalance(), deduct);
                input = sc.nextLine();
                if (input.equals(CONTINUE)) {
                    break;
                }
                return true;
            } else if (input.equals(WD50)) {
                BigDecimal deduct = new BigDecimal(50);
                account.setBalance(withdraw(account.getBalance(), deduct));
                summaryScreen(account.getBalance(), deduct);
                input = sc.nextLine();
                if (input.equals(CONTINUE)) {
                    break;
                }
                return true;
            } else if (input.equals(WD100)) {
                BigDecimal deduct = new BigDecimal(100);
                account.setBalance(withdraw(account.getBalance(), deduct));
                summaryScreen(account.getBalance(), deduct);
                input = sc.nextLine();
                if (input.equals(CONTINUE)) {
                    break;
                }
                return true;
            } else if (input.equals(OTHER)) {
                System.out.print(OTHER_WD_SCREEN);
                input = sc.nextLine();
                if (input.matches(NUMERIC_ONLY_RGX) && new BigDecimal(input).remainder(new BigDecimal(10)).compareTo(BigDecimal.ZERO) == 0) {
                    BigDecimal deduct = new BigDecimal(input);
                    if (deduct.compareTo(new BigDecimal(1000)) > 0) {
                        System.out.println(ERR_MAX_AMOUNT_WITHDRAW_1000);
                    } else {
                        account.setBalance(withdraw(account.getBalance(), deduct));
                        summaryScreen(account.getBalance(), deduct);
                        input = sc.nextLine();
                        if (input.equals(CONTINUE)) {
                            break;
                        }
                        return true;
                    }
                } else {
                    System.out.println(ERR_INVALID_AMOUNTS);
                }
            } else if (input.isEmpty() || input.equals(BACK)) {
                break;
            }
        }
        return false;
    }

    /**
     * Validate account
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

    private BigDecimal withdraw(BigDecimal amt, BigDecimal deduct) {
        if (amt.compareTo(deduct) < 0) {
            System.out.println(ERR_INSUFFICIENT_BALANCE + deduct.subtract(amt).toPlainString());
            return amt;
        }
        return amt.subtract(deduct);
    }

    private void summaryScreen(BigDecimal balance, BigDecimal deduct) {
        System.out.printf(SUMMARY_SCREEN, LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_PATTERN)), deduct, balance);
    }

    private void summaryScreen(BigDecimal balance, BigDecimal deduct, String destination, String reference) {
        System.out.printf(SUMMARY_SCREEN_TRF, destination, deduct, reference, balance);
    }

}
