package service.impl;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import domain.Account;
import domain.list.AccountList;
import listener.TransferNKeyListener;
import service.AtmServiceAbs;
import service.PasswordEncoder;
import util.PasswordUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static constants.AppConstants.*;

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
        try {
            //trf destination
            System.out.print(TRANSFER_SCREEN[0]);
            String dest = sc.nextLine();
            if (!dest.matches(NUMERIC_ONLY_RGX) || AccountList.findByAccountNumber(dest) == null) {
                System.out.println(ERR_INVALID_ACCOUNT);
                return exitStatus;
            }
            //enable global screen and native key logger
            GlobalScreen.registerNativeHook();
            TransferNKeyListener listener = new TransferNKeyListener();
            GlobalScreen.addNativeKeyListener(listener);
            //start to listen to the event keyboard event
            long end = System.currentTimeMillis() + 30000; //timeout in 30s
            for (; ; ) {
                if (TransferNKeyListener.EVENT_CANCELED.equals(listener.getEventStatus()) || System.currentTimeMillis() < end) {
                    GlobalScreen.unregisterNativeHook();
                    return exitStatus;
                } else if(TransferNKeyListener.EVENT_EXIT.equals(listener.getEventStatus())){
                    GlobalScreen.unregisterNativeHook();
                    break;
                }
            }
            //trf amounts
            System.out.print(TRANSFER_SCREEN[1]);
            String transfer = sc.nextLine();
            if (!"".equals(transfer) && !transfer.matches(NUMERIC_ONLY_RGX)) {
                System.out.println(ERR_INVALID_AMOUNTS);
                //eventStatus = EVENT_CANCELED;
            } else if (new BigDecimal(transfer).compareTo(new BigDecimal(1)) < 0) {
                System.out.println("Minimum amount to transfer is $1");
                //eventStatus = EVENT_CANCELED;
            } else if (new BigDecimal(transfer).compareTo(new BigDecimal(1000)) > 0) {
                System.out.println("Maximum amount to transfer is $1000");
                //eventStatus = EVENT_CANCELED;
            } else {
                Account account = AccountList.findByAccountNumber(accNum);
                if (account.getBalance().compareTo(new BigDecimal(transfer)) < 0) {
                    System.out.println("Insufficient balance $" + transfer);
                    //eventStatus = EVENT_CANCELED;
                } else {
                    String refNum = PasswordUtil.generateRandom(6);
                    System.out.printf(TRANSFER_SCREEN[2], refNum);
                    //reference number confirmation
                    if (!sc.nextLine().equals(refNum)) {
                        System.out.println("Invalid Reference Number");
                        //eventStatus = EVENT_CANCELED;
                    } else {
                        //confirm trf
                        System.out.printf(TRANSFER_SCREEN[3]);
                        //deduct balance, and execute transfer
                        BigDecimal trfAmt = new BigDecimal(transfer);
                        account.setBalance(account.getBalance().subtract(trfAmt));
                        Account destAcc = AccountList.findByAccountNumber(dest);
                        destAcc.setBalance(destAcc.getBalance().add(trfAmt));
                        summaryScreen(account.getBalance(), trfAmt, dest, refNum);
                        String input = sc.nextLine();
                        if ("".equals(input) || "2".equals(input)) {
                           exitStatus = true;
                        }
                    }
                }
            }
        } catch (NativeHookException e) {
            e.printStackTrace();
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
                if (input.equals("1")) {
                    break;
                }
                return true;
            } else if (input.equals(WD50)) {
                BigDecimal deduct = new BigDecimal(50);
                account.setBalance(withdraw(account.getBalance(), deduct));
                summaryScreen(account.getBalance(), deduct);
                input = sc.nextLine();
                if (input.equals("1")) {
                    break;
                }
                return true;
            } else if (input.equals(WD100)) {
                BigDecimal deduct = new BigDecimal(100);
                account.setBalance(withdraw(account.getBalance(), deduct));
                summaryScreen(account.getBalance(), deduct);
                input = sc.nextLine();
                if (input.equals("1")) {
                    break;
                }
                return true;
            } else if (input.equals(OTHER)) {
                System.out.print(OTHER_WD_SCREEN);
                input = sc.nextLine();
                if (input.matches(NUMERIC_ONLY_RGX) && new BigDecimal(input).remainder(new BigDecimal(10)).compareTo(BigDecimal.ZERO) == 0) {
                    BigDecimal deduct = new BigDecimal(input);
                    if (deduct.compareTo(new BigDecimal(1000)) > 0) {
                        System.out.println("Maximum amount to withdraw is $1000");
                    } else {
                        account.setBalance(withdraw(account.getBalance(), deduct));
                        summaryScreen(account.getBalance(), deduct);
                        input = sc.nextLine();
                        if (input.equals("1")) {
                            break;
                        }
                        return true;
                    }
                } else {
                    System.out.println("Invalid Amount.");
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
            System.out.println(fieldName + ERR_MINIMUM_6_LENGTH);
            return false;
        } else if (!input.matches(NUMERIC_ONLY_RGX)) {
            System.out.println(fieldName + ERR_DIGIT);
            return false;
        }
        return true;
    }

    private BigDecimal withdraw(BigDecimal amt, BigDecimal deduct) {
        if (amt.compareTo(deduct) < 0) {
            System.out.println("Insufficient balance $" + deduct.subtract(amt).toPlainString());
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
