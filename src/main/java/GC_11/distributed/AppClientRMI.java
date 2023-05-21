package GC_11.distributed;


import GC_11.exceptions.ExceededNumberOfPlayersException;
import GC_11.exceptions.NameAlreadyTakenException;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class AppClientRMI {

    public static void main( String[] args ) throws RemoteException, NotBoundException, ExceededNumberOfPlayersException, NameAlreadyTakenException {
        Scanner inputLine = new Scanner(System.in);

        System.out.println("what's your nickname?");
        String nickname = inputLine.nextLine();
        System.out.println("***** Getting the registry *****\n");
        Registry registry = LocateRegistry.getRegistry();
        String[] e = registry.list();
        for (int i=0; i<e.length; i++){
            System.out.println(e[i]);
        }
        System.out.println("***** looking up for the server *****\n");
        ServerRei server = (ServerRei) registry.lookup("server");
        System.out.println("***** Creating a client rei implementation *****\n");
        ClientImplRei client = new ClientImplRei(server, nickname);
        client.run();
/*
        PropertyChangeEvent evt = new PropertyChangeEvent(
                "Client RMI",
                "Evento",
                null,
                "Messaggio"
        );


        client.update(client.getServer(),evt);
*/
    }
}
