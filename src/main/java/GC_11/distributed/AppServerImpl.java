package GC_11.distributed;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class AppServerImpl extends UnicastRemoteObject implements GC_11.distributed.socket.AppServer {

    private static AppServerImpl instance;

    protected AppServerImpl() throws RemoteException {
    }

    public static AppServerImpl getInstance() throws RemoteException{
        if (instance == null){
            instance = new AppServerImpl();
        }
        return instance;
    }

    public static void main(String[] args) {
        Thread rmiThread = new Thread() {
            @Override
            public void run() {
                try {
                    startRMI();
                } catch (RemoteException e) {
                    System.err.println("Cannot start RMI. This protocol will be disabled.");
                }
            }
        };

        rmiThread.start();

        try {
            rmiThread.join();
        } catch (InterruptedException e) {
            System.err.println("No connection protocol available. Exiting...");
        }
    }

    private static void startRMI() throws RemoteException {
        AppServerImpl server = getInstance();

        Registry registry = LocateRegistry.getRegistry();
        registry.rebind("server", server);
    }
    @Override
    public Server connect() throws RemoteException {
        return new ServerImpl();
    }
}
