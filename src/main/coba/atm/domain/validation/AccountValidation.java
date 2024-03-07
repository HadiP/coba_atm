package coba.atm.domain.validation;

import coba.atm.constants.AppConstants;
import coba.atm.constants.State;
import coba.atm.domain.wrapper.Pair;

import java.math.BigDecimal;

import static coba.atm.constants.AppConstants.*;

/**
 * Account entity class validation.
 */
public final class AccountValidation {

    private AccountValidation() {
    }

    private static AccountValidation accountValidation = null;

    public static AccountValidation getInstance() {
        if (accountValidation == null) {
            accountValidation = new AccountValidation();
        }
        return accountValidation;
    }

    /**
     * Validate accountNumber or Pin.
     *
     * @param input     code that need to be validated
     * @param fieldName field of account (account number or pin)
     * @return valid status (true/false)
     */
    public Pair<Boolean, String> validateCode(String input, String fieldName) {
        if (input.length() < 6) {
            return new Pair<>(false, String.format(ERR_MINIMUM_6_LENGTH, fieldName));
        } else if (!input.matches(NUMERIC_ONLY_RGX)) {
            return new Pair<>(false, String.format(ERR_DIGIT, fieldName));
        }
        return new Pair<>(true, null);
    }

    /**
     * Validate balance for subtraction.
     *
     * @param balance Account's balance
     * @param changes Changes of balance (transfer/withdraw)
     * @param state   application's State
     * @return boolean (true/false) false represent invalid balance
     */
    public Pair<Boolean, String> validateBalance(BigDecimal balance, String changes, State state) {
        if (!isValidNumericForTrx(changes, state)) {
            return new Pair<>(false, ERR_INVALID_AMOUNTS);
        } else if(isBalanceNotSufficient(balance, changes)){
            return new Pair<>(false, AppConstants.ERR_INSUFFICIENT_BALANCE + balance);
        } else {
            return validateAmountThreshold(changes, state);
        }
    }

    /**
     * Check if the number given was valid or not for transaction.
     * By checking if it was numeric and if the state is withdraw the amount need to be a multiplication of 10.
     * @param number number that need to be validated.
     * @param state state of transaction.
     * @return true(valid) / false (not valid)
     */
    public boolean isValidNumericForTrx(String number, State state){
        if("".equals(number) || !number.matches(NUMERIC_ONLY_RGX)){
            return State.WITHDRAW.equals(state)
                    && new BigDecimal(number).remainder(new BigDecimal(10)).compareTo(BigDecimal.ZERO) == 0;
        }
        return false;
    }

    public Pair<Boolean, String> validateAmountThreshold(String changes, State state) {
        String msg = "";
        boolean status = false;
        BigDecimal balanceAmt = new BigDecimal(changes);
        int check1k = balanceAmt.compareTo(new BigDecimal(1000));
        if (State.TRANSFER.equals(state)) {
            if (balanceAmt.compareTo(new BigDecimal(1)) < 0) {
                msg = ERR_MINIMUM_AMOUNT_1;
            } else if (check1k > 0) {
                msg = ERR_MAXIMUM_AMOUNT_1000;
            } else {
                status = true;
            }
        } else if (State.WITHDRAW.equals(state)) {
            if (check1k > 0) {
                msg = ERR_MAX_AMOUNT_WITHDRAW_1000;
            } else {
                status = true;
            }
        }
        return new Pair<>(status, msg);
    }

    public boolean isBalanceNotSufficient(BigDecimal currentBalance, String changes) {
        BigDecimal withdrawAmt = new BigDecimal(changes);
        return currentBalance.compareTo(withdrawAmt) < 0;
    }

    public Pair<Boolean, String> checkAccountNum(String accountNumber) {
        return validateCode(accountNumber, "Account Number");
    }

    public Pair<Boolean, String> checkPin(String pin) {
        return validateCode(pin, "Pin");
    }

}
