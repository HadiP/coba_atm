package service.impl;

import service.AtmServiceAbs;
import util.PasswordUtil;

import java.util.Scanner;

import static constants.AppConstants.*;

public class AtmServiceImpl extends AtmServiceAbs {

    public static final String OTHER = "4";
    public static final String WD100 = "3";
    public static final String WD50 = "2";
    public static final String WD10 = "1";
    public static final String BACK = "5";

    public AtmServiceImpl(PasswordUtil pwe) {
        super(pwe);
    }

    @Override
    public String transactionScreen(Scanner sc){
        for(;;) {
            System.out.print(TRX_SCREEN);
            String input = sc.nextLine();
            if (input.equals("1")) {
                withdrawScreen(sc);
            } else if (input.equals("2")) {
                transferScreen(sc);
            } else if (input.isEmpty() || input.equals("3")) {
                break;
            }
        }
        return null;
    }

    @Override
    public String transferScreen(Scanner sc) {
        for(;;) {
            String input = sc.nextLine();
            if (input.equals("1")) {

            } else if (input.equals("2")) {

            } else if (input.isEmpty() || input.equals("3")) {
                break;
            }
        }
        return null;
    }

    @Override
    public void withdrawScreen(Scanner sc) {
        for(;;) {
            System.out.print(WITHDRAW_SCREEN);
            String input = sc.nextLine();
            if (input.equals(WD10)) {

            } else if (input.equals(WD50)) {

            } else if (input.equals(WD100)) {

            } else if (input.equals(OTHER)) {

            } else if (input.isEmpty() || input.equals(BACK)) {
                break;
            }
        }
    }
}
