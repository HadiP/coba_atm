package coba.atm.service;

import java.util.Scanner;

public interface AtmService {

    boolean login(String pw, String rawPw);

    void transactionScreen(Scanner sc, String accNum);

    boolean transferScreen(Scanner sc, String accNum);

    boolean withdrawScreen(Scanner sc, String accNum);

    boolean validateAcc(String input, String fieldName);
}
