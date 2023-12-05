package constants;

/**
 * App Constants
 */
public class AppConstants {

    /**
     * Welcome screen
     */
    public static final String[] WELCOMING_SCREEN = new String[]{"\nEnter Account Number: ",
            "\nEnter PIN: "};
    /**
     * Transaction screen
     */
    public static final String TRX_SCREEN = "\n1. Withdraw\n" +
            "2. Fund Transfer\n" +
            "3. Exit\n" +
            "Please choose option[3]: ";
    /**
     * Withdraw screen
     */
    public static final String WITHDRAW_SCREEN = "\n1. $10\n" +
            "2. $50\n" +
            "3. $100\n" +
            "4. Other\n" +
            "5. Back\n" +
            "Please choose option[5]: ";
    public static final String OTHER_WD_SCREEN = "\nOther Withdraw\n" +
            "Enter amount to withdraw: ";
    public static final String SUMMARY_SCREEN = "\nSummary\n" +
            "Date : %s\n" +
            "Withdraw : $%s\n" +
            "Balance : $%s\n" +
            "\n" +
            "1. Transaction \n" +
            "2. Exit\n" +
            "Choose option[2]: ";
    public static final String SUMMARY_SCREEN_TRF = "\nFund Transfer Summary\n" +
            "Destination Account : %s\n" +
            "Transfer Amount     : $%s\n" +
            "Reference Number    : %s\n" +
            "Balance             : $%s\n" +
            "\n" +
            "1. Transaction\n" +
            "2. Exit\n" +
            "Choose option[2]:";

    /**
     * Transfer screen
     */
    public static final String[] TRANSFER_SCREEN = {
            //0
            "\nPlease enter destination account and \n" +
            "press enter to continue or \n" +
            "press cancel (Esc) to go back to Transaction: ",

            //1
            "\nPlease enter transfer amount and press enter to continue or \n" +
                    "press enter to go back to Transaction: ",

            //2
            "\nReference Number: %s\n" +
                    "press enter to continue or press enter to go back to Transaction: ",
            //3
            "\nTransfer Confirmation\n" +
                    "Destination Account : %s\n" +
                    "Transfer Amount     : $%s\n" +
                    "Reference Number    : %s\n" +
                    "\n" +
                    "1. Confirm Trx\n" +
                    "2. Cancel Trx\n" +
                    "Choose option[2]: "

    };

    /**
     * Constant values for Transaction Menu
     */
    public static final String WITHDRAW = "1", TRANSFER = "2", EXIT = "3";

    /**
     * Constant values for Withdraw Menu
     */
    public static final String WD10 = "1", WD50 = "2", WD100 = "3", OTHER = "4", BACK = "5";
    public static final String DATE_PATTERN = "yyyy-MM-dd hh:mm:ss a";


    /**
     * Constant values for Transfer Menu
     */
    public static final String SIMULATED_ESC_BTN = "esc";
    public static final String CONTINUE = "1";
    public static final String CONFIRM = "1";


    /**
     * Error msg for withdraw
     */
    public static final String ERR_MAX_AMOUNT_WITHDRAW_1000 = "\nMaximum amount to withdraw is $1000";

    /**
     * error msg for transfer
     */
    public static final String ERR_REFERENCE_NUM = "\nInvalid Reference Number";
    public static final String ERR_INSUFFICIENT_BALANCE = "\nInsufficient balance $";
    public static final String ERR_MAXIMUM_AMOUNT_1000 = "\nMaximum amount to transfer is $1000";
    public static final String ERR_MINIMUM_AMOUNT_1 = "\nMinimum amount to transfer is $1";

    /**
     * Other
     */
    public static final String NUMERIC_ONLY_RGX = "^\\d+$";
    public static final String ERR_INVALID_ACCOUNT_NUMBER_PIN = "\nInvalid Account Number/PIN";
    public static final String ERR_INVALID_ACCOUNT = "\nInvalid Account";
    public static final String ERR_INVALID_AMOUNTS = "\nInvalid Amounts";
    public static final String ERR_MINIMUM_6_LENGTH = "\n%s should have 6 digits length";
    public static final String ERR_DIGIT = "\n%s should only contains numbers";
    public static final String FIELD_ACC_NUM = "Account Number";
    public static final String FIELD_PIN = "PIN";
}
