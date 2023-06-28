package GC_11;

import GC_11.distributed.Client;
import GC_11.view.GUI.GUI;
import GC_11.view.GameCLI;
import GC_11.view.View;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientApp {

    public static final String STYLEPATH = String.valueOf(ClientApp.class.getResource("/css/style.css"));

    public static String IP_SERVER;
    public static int SOCKET_PORT;
    public static int RMI_PORT;

    public static View view;
    public static Client client;

    public static void main (String[] args) throws RemoteException {
        //executorService = Executors.newCachedThreadPool();
        //setDefault();
        if (args.length > 0) {
            if (args[0].equals("GUI")) {
                view = new GUI();
            } else
                view = new GameCLI();
        }
    }
}

