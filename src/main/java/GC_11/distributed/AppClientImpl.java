package GC_11.distributed;

import GC_11.distributed.socket.ClientGame;
import GC_11.model.GameView;
import GC_11.model.Player;
import GC_11.view.CLIview;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class AppClientImpl {
    public static void main( String[] args ) {
        ClientGame client = new ClientGame("127.0.0.1", 4321);
        try{
            client.startClient();
        }catch (IOException e){
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }

    }
}
