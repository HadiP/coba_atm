package constants;

/**
 * App Constants
 */
public class AppConstants {

    public static final String[] WELCOMING_MSG = new String[] {"Enter Account Number: ",
            "Enter PIN: "};
    public static final String TRX_MSG = "Hi!\nPlease choose option below;" +
            "\n- Create new Account: 1" +
            "\n- Select Account 2 [id]" +
            "\n- Edit Account: 3 [id]" +
            "\n- Delete Account 4 [id]" +
            "\n- Exit (Q)";
    public static final String CREATE_ACC_MSG = "Create Account selected!\nPlease fill or type the data as follow:\n[name, accountNumber, pin, balance]";
    public static final String BYE = "Exit app..";
    public static final String CREATE_OPT = "1";
    public static final String SELECT_OPT_RGX = "2|2+ +(\\d|\\[+\\d+])";
    public static final String EDIT_OPT_RGX = "3+ +(\\d|\\[+\\d+])";
    public static final String DEL_OPT_RGX = "4+ +(\\d|\\[+\\d+])";
    public static final String EXIT_OPT = "Q";

    public static final String UNSUPPORTED_MSG = "Feature still under development.";
    public static final String ACCOUNT_ADDED_MSG = "Account successfully added!";
    public static final String SELECT_REQUESTED_DATA_MSG = "Select requested data.";
    public static final String SPACE_SYM = " ";
    public static final String INVALID_ACCOUNT = "Invalid Account Number/PIN";
    public static final String DATA_NOT_FOUND_MSG = "Unable to find requested data.";
    public static final String BRACKET_SYM = "[";
    public static final String CONSOLE_LINE = "====================================================================================";
}
