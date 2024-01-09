package coba.atm.view;

import coba.atm.constants.State;
import coba.atm.domain.Account;
import coba.atm.domain.list.AccountList;
import coba.atm.exception.AccountNotFoundException;
import coba.atm.exception.ValidationErrorException;
import coba.atm.service.TransferService;
import coba.atm.service.impl.TransferServiceImpl;
import coba.atm.util.PasswordUtil;

import java.util.Scanner;

import static coba.atm.constants.AppConstants.*;

public class TransferView {

    final TransferService service = new TransferServiceImpl();

    public State transferScreen(AccountList accountList, Account currentAccount) {
        State resp = State.TRANSACTION;
        Scanner sc = new Scanner(System.in);
        System.out.print(TRANSFER_SCREEN);
        String input = sc.nextLine();
        if (!SIMULATED_ESC_BTN.equals(input)) {
            try {
                //input as destination account number
                Account destinationAccount = accountList.findByAccountNumber(input);
                // trf amounts
                System.out.print(TRANSFER_SCREEN2);
                String trfAmt = sc.nextLine();
                if (trfAmt.isEmpty()) {
                    return resp;
                } else if(!currentAccount.validateBalance(trfAmt, State.TRANSFER)){
                    throw new ValidationErrorException();
                }
                String refNum = PasswordUtil.generateRandom(6);
                System.out.printf(TRANSFER_SCREEN3, refNum);
                //reference number confirmation
                input = sc.nextLine();
                if(input.isEmpty()){
                    return resp;
                }
                // reference number confirmation
                else if (!input.equals(refNum)) {
                    System.out.println(ERR_REFERENCE_NUM);
                    throw new ValidationErrorException();
                } else {
                    // confirm trf
                    System.out.printf(TRANSFER_SCREEN4, destinationAccount.getAccountNumber(), trfAmt, refNum);
                    if (CONFIRM.equals(sc.nextLine())) {
                        State s = service.transferFund(accountList, currentAccount, destinationAccount, trfAmt);
                        if(State.SUMMARY.equals(s)) {
                            resp = SummaryView.summaryScreen(currentAccount.getBalance(), trfAmt,
                                    currentAccount.getAccountNumber(), refNum);
                        }
                    }
                }
            } catch (ValidationErrorException | AccountNotFoundException e) {
                resp = State.TRANSFER;
            }
        }
        return resp;
    }

}
