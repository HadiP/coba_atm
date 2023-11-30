package constants;

/**
 * App Constants
 */
public class AppConstants {

    public static final String[] WELCOMING_SCREEN = new String[] {"Enter Account Number: ",
            "Enter PIN: "};
    public static final String TRX_SCREEN = "1. Withdraw\n" +
            "2. Fund Transfer\n" +
            "3. Exit\n" +
            "Please choose option[3]: ";
    public static final String WITHDRAW_SCREEN = "1. $10\n" +
            "2. $50\n" +
            "3. $100\n" +
            "4. Other\n" +
            "5. Back\n" +
            "Please choose option[5]: ";
    public static final String OTHER_WD_SCREEN = "Other Withdraw\n" +
            "Enter amount to withdraw: ";
    public static final String SUMMARY_SCREEN = "Summary\n" +
            "Date : %s\n" +
            "Withdraw : $%s\n" +
            "Balance : $%s\n" +
            "\n" +
            "1. Transaction \n" +
            "2. Exit\n" +
            "Choose option[2]: ";
    public static final String NUMERIC_ONLY_RGX = "^\\d+$";

    public static final String UNSUPPORTED_MSG = "Feature still under development.";
    public static final String ERR_INVALID_ACCOUNT = "Invalid Account Number/PIN";
    public static final String ERR_MINIMUM_6_LENGTH = " should have 6 digits length";
    public static final String ERR_DIGIT = " should only contains numbers";
    public static final String CONSOLE_LINE = "====================================================================================";

    public static final String FIELD_ACC_NUM = "Account Number";
    public static final String FIELD_PIN = "PIN";

    /**
     * Constant values for Transaction Menu
     */
    public static final String WITHDRAW = "1", TRANSFER = "2", EXIT = "3";
    /**
     * Constant values for Withdraw Menu
     */
    public static final String WD10 = "1", WD50 = "2", WD100 = "3", OTHER = "4", BACK = "5";
    public static final String DATE_PATTERN = "yyyy-MM-dd hh:mm:ss a";

}
