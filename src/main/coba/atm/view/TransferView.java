package coba.atm.view;

import coba.atm.constants.State;
import coba.atm.domain.Account;
import coba.atm.domain.list.AccountList;
import coba.atm.domain.validation.AccountValidation;
import coba.atm.domain.wrapper.Pair;
import coba.atm.exception.AccountNotFoundException;
import coba.atm.exception.ValidationErrorException;
import coba.atm.service.TransferService;
import coba.atm.service.impl.TransferServiceImpl;
import coba.atm.util.ReferenceNumberUtil;

import java.util.Scanner;

import static coba.atm.constants.AppConstants.*;

/**
 * Transfer screen class.
 * This class handle the screen interaction in the transfer stage.
 */
public class TransferView {

    private final AccountList accountList;
    private final TransferService service;
    private final Scanner sc;
    public TransferView(AccountList accountList){
        this.accountList = accountList;
        this.service = new TransferServiceImpl();
        sc = new Scanner(System.in);
    }

    public State transferScreen(Account currentAccount) {
        State resp = State.TRANSACTION;
        System.out.print(TRANSFER_SCREEN);
        String input = sc.nextLine();
        if (!SIMULATED_ESC_BTN.equals(input)) {
            try {
                // trf amounts
                System.out.print(TRANSFER_SCREEN2);
                String trfAmt = sc.nextLine();
                if (!trfAmt.isEmpty()) {
                    Pair<Boolean, String> resu = AccountValidation.getInstance().validateBalance(currentAccount.getBalance(), trfAmt, State.TRANSFER);
                    if (!resu.getO()) {
                        System.out.println(resu.getO2());
                        resp = State.TRANSFER;
                    } else {
                        // Also to reduce cognitive complexity level, reference number process separated from main method.
                        // input as destination account number, since it was not an escape button
                        resp = processReferenceNumber(currentAccount, input, trfAmt, resp);
                    }
                }
            } catch (Exception e) {
                resp = State.TRANSFER;
            }
        }
        return resp;
    }

    private State processReferenceNumber(Account currentAccount, String destAccountNum,
                                         String trfAmt, State resp) throws AccountNotFoundException, ValidationErrorException {
        Account destinationAccount = accountList.findByAccountNumber(destAccountNum);
        String refNum = ReferenceNumberUtil.generateRandom();
        System.out.printf(TRANSFER_SCREEN3, refNum);
        String input = sc.nextLine();
        // reference number confirmation
        if (refNum.equals(input)) {
            // confirm trf
            System.out.printf(TRANSFER_SCREEN4, destinationAccount.getAccountNumber(), trfAmt, refNum);
            if (CONFIRM.equals(sc.nextLine())) {
                State s = service.transferFund(accountList, currentAccount, destinationAccount, trfAmt);
                if (State.SUMMARY.equals(s)) {
                    resp = SummaryView.summaryScreen(currentAccount.getBalance(), trfAmt,
                            currentAccount.getAccountNumber(), refNum);
                }
            }
        } else if (!input.isEmpty()) {
            System.out.println(ERR_REFERENCE_NUM);
            resp = State.TRANSFER;
        }

        return resp;
    }

}
