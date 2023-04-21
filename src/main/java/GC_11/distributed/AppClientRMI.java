package GC_11.distributed;

import GC_11.distributed.ClientImpl;
import GC_11.distributed.ServerImpl;
import GC_11.distributed.socket.AppServer;
import GC_11.model.Game;
import GC_11.model.GameView;
import GC_11.model.Player;
import GC_11.util.Choice;
import GC_11.view.CLIview;

import java.beans.PropertyChangeEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

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
