package coba.atm.view;

import coba.atm.domain.Account;
import coba.atm.domain.list.AccountList;
import coba.atm.service.AccountLoginService;
import coba.atm.service.impl.AccountLoginServiceImpl;

import java.util.Optional;
import java.util.Scanner;

import static coba.atm.constants.AppConstants.WELCOME_SCREEN;
import static coba.atm.constants.AppConstants.WELCOME_SCREEN2;

public class LoginView {

    public Optional<Account> loginScreen(AccountList accList){
        final AccountLoginService loginService = new AccountLoginServiceImpl();
        System.out.print(WELCOME_SCREEN);
        Scanner sc = new Scanner(System.in);
        // account number
        String accNum = sc.nextLine();
        System.out.print(WELCOME_SCREEN2);
        String pin = sc.nextLine();
        return loginService.login(accList, accNum, pin);
    }

}
