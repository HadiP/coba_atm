package service.impl;

import domain.Account;
import domain.list.AccountList;
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
                if(withdrawScreen(sc, accNum)){
                    break;
                }
            } else if (input.equals(TRANSFER)) {
                System.out.println(UNSUPPORTED_MSG);
                //transferScreen(sc, accNum);
            } else if (input.isEmpty() || input.equals(EXIT)) {
                break;
            }
        }
    }

    @Override
    public boolean transferScreen(Scanner sc, String accNum) {
        for (; ; ) {
            String input = sc.nextLine();
            if (input.equals("1")) {

            } else if (input.equals("2")) {

            } else if (input.isEmpty() || input.equals("3")) {
                break;
            }
        }
        return false;
    }

    @Override
    public boolean withdrawScreen(Scanner sc, String accNum) {
        for (;;) {
            System.out.print(WITHDRAW_SCREEN);
            String input = sc.nextLine();
            Account account = AccountList.findByAccountNumber(accNum);
            if (input.equals(WD10)) {
                BigDecimal deduct = new BigDecimal(10);
                account.setBalance(withdraw(account.getBalance(), deduct));
                summaryScreen(account.getBalance(), deduct);
                input = sc.nextLine();
                if(input.equals("1")){
                    break;
                }
                return true;
            } else if (input.equals(WD50)) {
                BigDecimal deduct = new BigDecimal(50);
                account.setBalance(withdraw(account.getBalance(), deduct));
                summaryScreen(account.getBalance(), deduct);
                input = sc.nextLine();
                if(input.equals("1")){
                    break;
                }
                return true;
            } else if (input.equals(WD100)) {
                BigDecimal deduct = new BigDecimal(100);
                account.setBalance(withdraw(account.getBalance(), deduct));
                summaryScreen(account.getBalance(), deduct);
                input = sc.nextLine();
                if(input.equals("1")){
                    break;
                }
                return true;
            } else if (input.equals(OTHER)) {
                System.out.print(OTHER_WD_SCREEN);
                input = sc.nextLine();
                if(input.matches(NUMERIC_ONLY_RGX) && new BigDecimal(input).remainder(new BigDecimal(10)).compareTo(BigDecimal.ZERO) == 0){
                    BigDecimal deduct = new BigDecimal(input);
                    if(deduct.compareTo(new BigDecimal(1000)) > 0){
                        System.out.println("Maximum amount to withdraw is $1000");
                    } else {
                        account.setBalance(withdraw(account.getBalance(), deduct));
                        summaryScreen(account.getBalance(), deduct);
                        input = sc.nextLine();
                        if(input.equals("1")){
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
     * @param input
     * @param fieldName
     * @return
     */
    @Override
    public boolean validateAcc(String input, String fieldName){
        if(input.length() < 6){
            System.out.println(fieldName + ERR_MINIMUM_6_LENGTH);
            return false;
        } else if(!input.matches(NUMERIC_ONLY_RGX)) {
            System.out.println(fieldName + ERR_DIGIT);
            return false;
        }
        return true;
    }
    private void summaryScreen(BigDecimal balance, BigDecimal deduct){
        System.out.printf(SUMMARY_SCREEN, LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_PATTERN)), deduct, balance);
    }

    private BigDecimal withdraw(BigDecimal amt, BigDecimal deduct) {
        if (amt.compareTo(deduct) < 0) {
            System.out.println("Insufficient balance $" + deduct.subtract(amt).toPlainString());
            return amt;
        }
        return amt.subtract(deduct);
    }
}
