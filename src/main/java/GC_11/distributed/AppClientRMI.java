package GC_11.distributed;

/*
public class AppClientRMI {

    public static void main( String[] args ) throws RemoteException, NotBoundException {

        Registry registry = LocateRegistry.getRegistry();
        Server server = (Server) registry.lookup("server");

        ClientImpl client = new ClientImpl(server);

        PropertyChangeEvent evt = new PropertyChangeEvent(
                "Client RMI",
                "Evento",
                null,
                "Messaggio"
        );


        client.update(client.getServer(),evt);

    }
}
*/