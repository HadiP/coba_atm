package coba.atm.domain;

import coba.atm.constants.State;
import coba.atm.domain.validation.AccountValidation;
import coba.atm.domain.wrapper.Pair;
import coba.atm.exception.ValidationErrorException;
import coba.atm.util.PasswordUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Account class used as application user entity
 */
@SuppressWarnings("unused")
public class Account extends CommonEntity<Long> {
    protected String accountNumber;
    protected String name;
    protected String pin;
    protected BigDecimal balance;

    /**
     * Common constructor used to create new account data, will always set updateDateTime to null.
     *
     * @param id             data id
     * @param createDateTime creation timestamp
     * @param name           account name
     * @param accountNumber  bank account number
     * @param pin            secret pin
     * @param balance        account balance
     */
    public Account(Long id, LocalDateTime createDateTime, String name, String accountNumber, String pin, BigDecimal balance) {
        super(id, createDateTime, null);
        this.name = name;
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
    }

    /**
     * Copy constructor
     * @param copyMe object to copy
     */
    public Account(Account copyMe){
        this.id = copyMe.id;
        this.accountNumber = copyMe.accountNumber;
        this.name = copyMe.name;
        this.pin = copyMe.pin;
        this.balance = copyMe.balance;
    }

    /**
     * Used in search by id
     *
     * @param id Account.id
     */
    public Account(Long id) {
        this.id = id;
    }

    /**
     * Used in search by accountNumber
     *
     * @param accNum Account.accountNumber
     */
    public Account(String accNum) {
        this.accountNumber = accNum;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void addBalance(String amounts) {
        this.balance = this.balance.add(new BigDecimal(amounts));
    }

    public void subtractBalance(String amounts, State state) throws ValidationErrorException {
        Pair<Boolean, String> resu = AccountValidation.getInstance().validateBalance(this.balance, amounts, state);
        if(!resu.getO()){
            throw new ValidationErrorException(resu.getO2());
        }
        this.balance = this.balance.subtract(new BigDecimal(amounts));
    }

    public boolean validatePin(String pin) throws ValidationErrorException {
        Pair<Boolean, String> res = AccountValidation.getInstance().checkPin(pin);
        if(!res.getO()){
            throw new ValidationErrorException(res.getO2());
        }
        return new PasswordUtil().match(this.pin, pin);
    }

    @Override
    public String toString() {
        return "id: " + this.getId() + " name: " + this.getName() + " account number: " + this.getAccountNumber() + "\n"
                + "created: " + this.getCreateDateTime() + " updated: " + this.getUpdateDateTime() + "\n";
    }
}
