package GC_11;

import GC_11.distributed.ClientImpl;
import GC_11.distributed.ServerImpl;
import GC_11.model.Game;
import GC_11.model.GameView;
import GC_11.model.Player;
import GC_11.util.Choice;
import GC_11.view.CLIview;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class AppClientRMI {

    public static void main( String[] args ) throws RemoteException {

        ServerImpl server = null;
        try {
            server = new ServerImpl();
        }
        catch(RemoteException e){
            System.out.println("Remote Exception");
        }


        ClientImpl client = new ClientImpl(server);

        //Choice choice = new Choice("SEE_COMMONGOAL 0");
        Choice.Type choice = Choice.Type.SEE_COMMONGOAL;

        Scanner s = new Scanner(System.in);
        String input = s.next();
        while (!input.equals("EXIT")){
            input= s.next();
            PropertyChangeEvent pce = new PropertyChangeEvent("Client of player","CHOICE",null,input);
            client.propertyChange(pce);
        }

    }
}
