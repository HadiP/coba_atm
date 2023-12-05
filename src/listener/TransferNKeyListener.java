package listener;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import domain.Account;
import domain.list.AccountList;
import util.PasswordUtil;

import java.math.BigDecimal;
import java.util.Scanner;

import static constants.AppConstants.*;

/**
 * Native key listener class used to detect keyboard press event in console app
 * without using java swing/iframe library.
 */
public class TransferNKeyListener implements NativeKeyListener {

    public static final String EVENT_EXIT = "exit";
    public static final String EVENT_EXIT_TX = "exit_tx";
    public static final String EVENT_CANCELED = "cancel";
    public static final String EVENT_STARTED = "started";
    private volatile String eventStatus = EVENT_STARTED;

    public TransferNKeyListener() {
    }

    public synchronized String getEventStatus(){
        return eventStatus;
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
            if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
                //cancel
                System.out.println("Escape Pressed!");
                eventStatus = EVENT_CANCELED;
            } else if (e.getKeyCode() == NativeKeyEvent.VC_ENTER){
                System.out.println("Enter Pressed!");
                eventStatus = EVENT_EXIT;
            }
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        //System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        //System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
    }

}
