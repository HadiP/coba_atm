package coba.atm;

import coba.atm.domain.Account;
import coba.atm.domain.list.AccountList;
import coba.atm.exception.ValidationErrorException;

public class AppTest {

    public static void main(String[] args) {
        AccountList test = new AccountList();
        Account coba = test.find(1);
        try {
            coba.addBalance("1000");
        } catch (ValidationErrorException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Test copy constructor:");
        test.select().forEach(x -> System.out.println(x.getName()+"'s balance: "+x.getBalance()));

        ServiceTest serviceTest = new ServiceTest();
        serviceTest.doSomething(test);

        System.out.println("\nTest AccountList.save");
        test.save(coba);
        test.select().forEach(x -> System.out.println(x.getName()+"'s balance: "+x.getBalance()));
    }

}
