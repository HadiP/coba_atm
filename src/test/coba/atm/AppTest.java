package coba.atm;

import coba.atm.domain.Account;
import coba.atm.domain.list.AccountList;
import coba.atm.exception.NoDefaultConstructorException;
import coba.atm.exception.ValidationErrorException;
import coba.atm.util.CsvReaderUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AppTest {

    public static void main(String[] args) {
        System.out.println("start CsvReaderUtil test..");
        csvToolTestCase();
        System.out.println("\nstart accountList test..");
        serviceTestCase();
    }

    private static void csvToolTestCase(){
        try {
            FileInputStream fileInputStream = new FileInputStream("C:\\Users\\Hadi_P111\\IdeaProjects\\local\\data-source\\data.csv");
            CsvReaderUtil csvReaderUtil = CsvReaderUtil.getInstance(fileInputStream)
                    .disableHeader(false)
                    .setDelimiter(";");
            System.out.println("START CONVERT...");
            List<Account> accounts = new ArrayList<>();
            csvReaderUtil.readAll(accounts, Account.class);
            System.out.println("CHECK DATA:");
            accounts.forEach(System.out::println);
        } catch (FileNotFoundException | NoDefaultConstructorException e) {
            throw new RuntimeException(e);
        }
    }

    private static void serviceTestCase(){
        AccountList test = new AccountList();
        Account coba = test.find(1);
        try {
            coba.addBalance("1000");
        } catch (ValidationErrorException e) {
            assert false;
        }
        System.out.println("Test copy constructor:");
        test.select().forEach(x -> System.out.println(x.getName()+"'s balance: "+x.getBalance()));

        ServiceTest serviceTest = new ServiceTest();
        serviceTest.doSomething(test);

        System.out.println("\nTest AccountList.save");
        test.save(coba);
        assert test.find(1).getBalance().equals(new BigDecimal("1100"));
        test.select().forEach(x -> System.out.println(x.getName()+"'s balance: "+x.getBalance()));
    }
}
