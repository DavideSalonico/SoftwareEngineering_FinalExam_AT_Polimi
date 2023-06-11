package GC_11.distributed;


import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AppServerRMI {
    public static void main( String[] args ) throws RemoteException {
        try {
            //System.setProperty("java.rmi.server.hostname", "indirizzo ip corretto");
            System.out.println("***** Constructing server implementation *****\n");
            ServerImplRei server = new ServerImplRei();
            System.out.println("***** Getting the registry *****\n");
            Registry registry = LocateRegistry.createRegistry(4321);
            System.out.println("***** Binding server implementation to registry *****\n");
            registry.rebind("server", server);
            System.out.println("***** Waiting for clients *****\n");
            System.out.println(server);
            System.out.println(server.getRef());
            System.out.println(registry.list());
        } catch (Exception e){
            System.err.println("server error: "+ e.getMessage());
        }
    }
}
