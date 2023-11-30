package domain.list;

import domain.Account;
import domain.CommonEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Dummy data store.
 * Initial data will always contain;
 * Name : John Doe
 * PIN : 012108
 * Balance : $100
 * Account Number: 112233
 * --
 * Name : Jane Doe
 * PIN : 932012
 * Balance : $30
 * Account Number: 112244
 */
public class AccountList {

    private static List<Account> ACC_LIST = Arrays.asList(new Account(1L, LocalDateTime.now(), "John Doe", "012108", "112233", new BigDecimal("100")), new Account(1L, LocalDateTime.now(), "John Doe", "012108", "112233", new BigDecimal("100")));

    public static List<Account> select(){
        return ACC_LIST;
    }

    /**
     * find Account by id {@link Account}
     * @param id of Account
     * @return Account
     */
    public static Account find(long id){
        Comparator<Account> idCmp = Comparator.comparing(CommonEntity::getId);
        int idx = Collections.binarySearch(ACC_LIST, new Account(id), idCmp);
        if(idx >= 0) return ACC_LIST.get(idx);
        return null;
    }

    /**
     * find Account by accountNumber {@link Account}
     * @param accNum of Account
     * @return Account
     */
    public static Account findByAccountNumber(String accNum){
        Comparator<Account> idCmp = Comparator.comparing(Account::getAccountNumber);
        int idx = Collections.binarySearch(ACC_LIST, new Account(accNum), idCmp);
        if(idx >= 0) return ACC_LIST.get(idx);
        return null;
    }

}
