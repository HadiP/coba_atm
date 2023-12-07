package coba.atm.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Account class used as application user entity
 */
public class Account extends CommonEntity<Long> {

    private String accountNumber;
    private String name;
    private String pin;
    private BigDecimal balance;

    /**
     * Common constructor used to create new account data, will always set updateDateTime to null.
     * @param id data id
     * @param createDateTime creation timestamp
     * @param name account name
     * @param accountNumber bank account number
     * @param pin secret pin
     * @param balance account balance
     */
    public Account(Long id, LocalDateTime createDateTime, String name, String accountNumber, String pin, BigDecimal balance) {
        super(id, createDateTime, null);
        this.name = name;
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
    }

    /**
     * Used in search by id
     * @param id Account.id
     */
    public Account(Long id){
        this.id = id;
    }

    /**
     * Used in search by accountNumber
     * @param accNum Account.accountNumber
     */
    public Account(String accNum){
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

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "id: "+this.getId()+" name: "+this.getName()+ " account number: "+this.getAccountNumber() +"\n"
                + "created: "+ this.getCreateDateTime() +" updated: "+this.getUpdateDateTime()+"\n";
    }
}
