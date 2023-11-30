import domain.Account;
import domain.list.AccountList;
import service.AtmService;
import service.impl.AtmServiceImpl;

import java.util.Scanner;

import static constants.AppConstants.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

/**
 * ATM Simulation App
 */
public class App {

    public static void main(String[] args) {
        AtmService atmService = new AtmServiceImpl();
        for (;;) {
            System.out.print(WELCOMING_SCREEN[0]);
            Scanner sc = new Scanner(System.in);
            //account number
            String accNum = sc.nextLine();
            Account accExist = AccountList.findByAccountNumber(accNum);
            System.out.print(WELCOMING_SCREEN[1]);
            String pin = sc.nextLine();
            if (atmService.validateAcc(accNum, FIELD_ACC_NUM) && atmService.validateAcc(pin, FIELD_PIN)) {
                if (accExist == null || !accExist.getPin().equals(pin)) {
                    System.out.println(ERR_INVALID_ACCOUNT);
                } else {
                    atmService.transactionScreen(sc, accNum);
                }
            }
        }
    }
}