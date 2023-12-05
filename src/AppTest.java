import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import listener.TransferNKeyListener;

import java.util.Scanner;

public class AppTest {

    public static void main(String... args) throws NativeHookException {
        GlobalScreen.registerNativeHook();
        TransferNKeyListener listener = new TransferNKeyListener();
        GlobalScreen.addNativeKeyListener(listener);
        System.out.println("Please enter to continue or press escape to exit");
        //Scanner sc = new Scanner(System.in);
        long end = System.currentTimeMillis()+10000;
        for(;System.currentTimeMillis() < end;) {
            if (TransferNKeyListener.EVENT_CANCELED.equals(listener.getEventStatus())) {
                System.out.println("Exit detected!");
                System.exit(200);
                return;
            } else if (TransferNKeyListener.EVENT_EXIT.equals(listener.getEventStatus())) {
                break;
            }
        }
        System.out.print("Continue, please type something: ");
        Scanner sc = new Scanner(System.in);
        String test = sc.nextLine();
        System.out.println("Echo: "+ test);
        System.exit(0);
    }

}
