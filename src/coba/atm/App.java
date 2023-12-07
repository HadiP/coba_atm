package coba.atm;

import coba.atm.domain.Account;
import coba.atm.domain.list.AccountList;
import coba.atm.service.AtmService;
import coba.atm.service.PasswordEncoder;
import coba.atm.service.impl.AtmServiceImpl;
import coba.atm.util.PasswordUtil;

import java.util.Scanner;

import static coba.atm.constants.AppConstants.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

/**
 * ATM Simulation coba.atm.App
 */
public class App {

    public static void main(String[] args) {
        AtmService atmService = new AtmServiceImpl();
        PasswordEncoder pwEncoder = new PasswordUtil();
        for (;;) {
            System.out.print(WELCOME_SCREEN);
            Scanner sc = new Scanner(System.in);
            // account number
            String accNum = sc.nextLine();
            Account accExist = AccountList.findByAccountNumber(accNum);
            System.out.print(WELCOME_SCREEN2);
            String pin = sc.nextLine();
            if (atmService.validateAcc(accNum, FIELD_ACC_NUM) && atmService.validateAcc(pin, FIELD_PIN)) {
                if (accExist == null || !pwEncoder.match(accExist.getPin(), pin)) {
                    System.out.println(ERR_INVALID_ACCOUNT_NUMBER_PIN);
                } else {
                    atmService.transactionScreen(sc, accNum);
                }
            }
        }
    }
}