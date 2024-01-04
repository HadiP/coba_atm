package coba.atm.domain;

import coba.atm.constants.AppConstants;
import coba.atm.constants.State;
import coba.atm.exception.ValidationErrorException;
import coba.atm.util.PasswordUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static coba.atm.constants.AppConstants.*;

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
     * @param copyme object to copy
     */
    public Account(Account copyme){
        this.id = copyme.id;
        this.accountNumber = copyme.accountNumber;
        this.name = copyme.name;
        this.pin = copyme.pin;
        this.balance = copyme.balance;
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

    /*public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }*/

    /**
     * Validate accountNumber or Pin.
     *
     * @param input code that need to be validated
     * @param fieldName field of account (account number or pin)
     * @return valid status (true/false)
     */
    private static boolean validateCode(String input, String fieldName) {
        if (input.length() < 6) {
            System.out.printf(ERR_MINIMUM_6_LENGTH, fieldName);
            return false;
        } else if (!input.matches(NUMERIC_ONLY_RGX)) {
            System.out.printf(ERR_DIGIT, fieldName);
            return false;
        }
        return true;
    }

    /**
     * Validate balance for subtraction.
     * @param balance Account's balance
     * @param state application's State
     * @return boolean (true/false) false represent invalid balance
     */
    public boolean validateBalance(String balance, State state) {
        if (!"".equals(balance) && !balance.matches(NUMERIC_ONLY_RGX) &&
                (State.TRANSFER.equals(state) || new BigDecimal(balance).remainder(new BigDecimal(10)).compareTo(BigDecimal.ZERO) == 0)) {
            System.out.println(ERR_INVALID_AMOUNTS);
        } else {
            BigDecimal balanceAmt = new BigDecimal(balance);
            if (this.balance.compareTo(balanceAmt) < 0) {
                System.out.println(AppConstants.ERR_INSUFFICIENT_BALANCE + balanceAmt);
            } else {
                int check1k = balanceAmt.compareTo(new BigDecimal(1000));
                if (State.TRANSFER.equals(state)) {
                    if (balanceAmt.compareTo(new BigDecimal(1)) < 0) {
                        System.out.println(ERR_MINIMUM_AMOUNT_1);
                    } else if (check1k > 0) {
                        System.out.println(ERR_MAXIMUM_AMOUNT_1000);
                    } else {
                        return true;
                    }
                } else if (State.WITHDRAW.equals(state)) {
                    if (check1k > 0) {
                        System.out.println(ERR_MAX_AMOUNT_WITHDRAW_1000);
                    } else {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean checkAccountNum(String accountNumber) {
        return validateCode(accountNumber, "Account Number");
    }

    private static boolean checkPin(String pin) {
        return validateCode(pin, "Pin");
    }

    public void addBalance(String amounts) throws ValidationErrorException {
        if(!amounts.matches(NUMERIC_ONLY_RGX)){
            throw new ValidationErrorException();
        }
        this.balance = this.balance.add(new BigDecimal(amounts));
    }

    public void subtractBalance(String amounts, State state) throws ValidationErrorException {
        if(!validateBalance(amounts, state)){
            throw new ValidationErrorException();
        }
        this.balance = this.balance.subtract(new BigDecimal(amounts));
    }

    public boolean validatePin(String pin) throws ValidationErrorException {
        if(!checkPin(pin)){
            throw new ValidationErrorException();
        }
        return new PasswordUtil().match(this.pin, pin);
    }

    @Override
    public String toString() {
        return "id: " + this.getId() + " name: " + this.getName() + " account number: " + this.getAccountNumber() + "\n"
                + "created: " + this.getCreateDateTime() + " updated: " + this.getUpdateDateTime() + "\n";
    }
}
