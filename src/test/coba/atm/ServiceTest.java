package coba.atm;

import coba.atm.domain.Account;
import coba.atm.domain.list.AccountList;
import coba.atm.exception.ValidationErrorException;

public class ServiceTest {

    void doSomething(AccountList accountList){
        Account coba = accountList.find(2);
        try {
            coba.addBalance("1000");
            //commit changes
            accountList.save(coba);
        } catch (ValidationErrorException e) {
            throw new RuntimeException(e);
        }
    }

}
