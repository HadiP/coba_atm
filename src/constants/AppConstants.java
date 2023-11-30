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
            "Please choose option[5]:";
    public static final String OTHER_WD_SCREEN = "Other Withdraw\n" +
            "Enter amount to withdraw:";
    public static final String CREATE_ACC_MSG = "Create Account selected!\nPlease fill or type the data as follow:\n[name, accountNumber, pin, balance]";
    public static final String BYE = "Exit app..";
    public static final String CREATE_OPT = "1";
    public static final String SELECT_OPT_RGX = "2|2+ +(\\d|\\[+\\d+])";
    public static final String EDIT_OPT_RGX = "3+ +(\\d|\\[+\\d+])";
    public static final String DEL_OPT_RGX = "4+ +(\\d|\\[+\\d+])";
    public static final String NUMERIC_ONLY_RGX = "^\\d+$";
    public static final String EXIT_OPT = "Q";

    public static final String UNSUPPORTED_MSG = "Feature still under development.";
    public static final String ACCOUNT_ADDED_MSG = "Account successfully added!";
    public static final String SELECT_REQUESTED_DATA_MSG = "Select requested data.";
    public static final String SPACE_SYM = " ";
    public static final String ERR_INVALID_ACCOUNT = "Invalid Account Number/PIN";
    public static final String ERR_MINIMUM_6_LENGTH = " should have 6 digits length";
    public static final String ERR_DIGIT = " should only contains numbers";
    public static final String ERR_DATA_NOT_FOUND_MSG = "Unable to find requested data.";
    public static final String BRACKET_SYM = "[";
    public static final String CONSOLE_LINE = "====================================================================================";

    public static final String FIELD_ACC_NUM = "Account Number";
    public static final String FIELD_PIN = "PIN";
}
