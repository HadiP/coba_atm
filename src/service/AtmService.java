package service;

import java.util.Scanner;

public interface AtmService {

    boolean login(String pw, String rawPw);

    String transactionScreen(Scanner sc);

    String transferScreen(Scanner sc);

    void withdrawScreen(Scanner sc);

}
