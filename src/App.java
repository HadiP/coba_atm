import domain.Account;
import domain.CommonEntity;

import java.time.LocalDateTime;
import java.util.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class App {
    private static final String WELCOMING_MSG = "Hi!\nPlease choose option below;" +
            "\n- Create new Account: 1" +
            "\n- Select Account 2 [id]" +
            "\n- Edit Account: 3 [id]" +
            "\n- Delete Account 4 [id]" +
            "\n- Exit (Q)";
    private static final String CREATE_ACC_MSG = "Create Account selected!\nPlease fill or type the data as follow:\n[name occupation]";
    private static final String BYE = "Exit app..";
    public static final String CREATE_OPT = "1";
    public static final String SELECT_OPT_RGX = "2|2+ +(\\d|\\[+\\d+])";
    public static final String EDIT_OPT_RGX = "3+ +(\\d|\\[+\\d+])";
    public static final String DEL_OPT_RGX = "4+ +(\\d|\\[+\\d+])";
    public static final String EXIT_OPT = "Q";

    public static final String UNSUPPORTED_MSG = "Feature still under development.";
    public static final String ACCOUNT_ADDED_MSG = "Account successfully added!";
    public static final String SELECT_REQUESTED_DATA_MSG = "Select requested data.";
    public static final String SPACE_SYM = " ";
    public static final String DATA_NOT_FOUND_MSG = "Unable to find requested data.";
    public static final String BRACKET_SYM = "[";
    public static final String CONSOLE_LINE = "====================================================================================";
    public static final String DEFAULT_USER = "Admin";
    public static final String DEFAULT_USER_OCP = "Administrator";

    public static void main(String[] args) {
        List<Account> accList = new ArrayList<>();
        accList.add(new Account(1L, LocalDateTime.now(), DEFAULT_USER, DEFAULT_USER_OCP));
        String option;
        do {
            System.out.println(WELCOMING_MSG);
            Scanner sc = new Scanner(System.in);
            option = sc.nextLine();
            if(option.equals(CREATE_OPT)){
                System.out.println(CREATE_ACC_MSG);
                String[] input = sc.nextLine().split(SPACE_SYM);
                Long lastId = accList.get(accList.size()-1).getId();
                Account a = new Account(lastId+1, LocalDateTime.now(), input[0], input[1]);
                accList.add(a);
                System.out.println(ACCOUNT_ADDED_MSG);
                showData(a);
            } else if(option.matches(SELECT_OPT_RGX)) {
                System.out.println(SELECT_REQUESTED_DATA_MSG);
                if(option.contains(SPACE_SYM)) {
                    Account a = find(accList, extractId(option));
                    if(a != null) {
                        showData(a);
                    } else {
                        System.out.println(DATA_NOT_FOUND_MSG);
                    }
                } else {
                    showData(accList);
                }
            } else if(option.matches(EDIT_OPT_RGX)) {
                System.out.println(UNSUPPORTED_MSG);
            } else if(option.matches(DEL_OPT_RGX)) {
                System.out.println(UNSUPPORTED_MSG);
            }
        } while (!EXIT_OPT.equals(option));
        System.out.println(BYE);
    }

    /**
     * find Account by id {@link Account}
     * @param accounts list of Account
     * @param id of Account
     * @return Account
     */
    private static Account find(List<Account> accounts, long id){
        Comparator<Account> idCmp = Comparator.comparing(CommonEntity::getId);
        int idx = Collections.binarySearch(accounts, new Account(id), idCmp);
        if(idx >= 0) return accounts.get(idx);
        return null;
    }

    private static long extractId(String option){
        String id = option.split(SPACE_SYM)[1];
        return Long.parseLong(id.contains(BRACKET_SYM) ? id.substring(0,1) : id);
    }

    private static void showData(List<Account> accounts){
        System.out.println(CONSOLE_LINE);
        accounts.forEach(System.out::println);
        System.out.println(CONSOLE_LINE);
    }

    private static void showData(Account account) {
        showData(Collections.singletonList(account));
    }
}