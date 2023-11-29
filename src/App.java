import domain.Account;
import domain.list.AccountList;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static constants.AppConstants.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class App {

    public static void main(String[] args) {
        for (;;) {
            System.out.print(WELCOMING_MSG[0]);
            Scanner sc = new Scanner(System.in);
            //account number
            String option = sc.nextLine();
            if(option.length() == 6){
                System.out.println();
            }
            Account accExist = AccountList.findByAccountNumber(option);
            System.out.print(WELCOMING_MSG[1]);
            option = sc.nextLine();
            if (accExist == null || !accExist.getPin().equals(option)) {
                System.out.println(INVALID_ACCOUNT);
            } else {
                do {
                    System.out.println(TRX_MSG);
                    option = sc.nextLine();
                    if (option.equals(CREATE_OPT)) {
                        System.out.println(CREATE_ACC_MSG);
                        String[] input = sc.nextLine().split(SPACE_SYM);
                        Long lastId = AccountList.select().get(AccountList.select().size() - 1).getId();
                        Account a = new Account(lastId + 1, LocalDateTime.now(), input[0], input[1], input[2], new BigDecimal(input[3]));
                        AccountList.select().add(a);
                        System.out.println(ACCOUNT_ADDED_MSG);
                        showData(a);
                    } else if (option.matches(SELECT_OPT_RGX)) {
                        System.out.println(SELECT_REQUESTED_DATA_MSG);
                        if (option.contains(SPACE_SYM)) {
                            Account a = AccountList.find(extractId(option));
                            if (a != null) {
                                showData(a);
                            } else {
                                System.out.println(DATA_NOT_FOUND_MSG);
                            }
                        } else {
                            showData(AccountList.select());
                        }
                    } else if (option.matches(EDIT_OPT_RGX)) {
                        System.out.println(UNSUPPORTED_MSG);
                    } else if (option.matches(DEL_OPT_RGX)) {
                        System.out.println(UNSUPPORTED_MSG);
                    }
                } while (!EXIT_OPT.equals(option));
                System.out.println(BYE);
            }
        }
    }

    private static long extractId(String option) {
        String id = option.split(SPACE_SYM)[1];
        return Long.parseLong(id.contains(BRACKET_SYM) ? id.substring(1, id.length()-1) : id);
    }

    private static void showData(List<Account> accounts) {
        System.out.println(CONSOLE_LINE);
        accounts.forEach(System.out::println);
        System.out.println(CONSOLE_LINE);
    }

    private static void showData(Account account) {
        showData(Collections.singletonList(account));
    }
}