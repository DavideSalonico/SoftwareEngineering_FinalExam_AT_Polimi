package GC_11.distributed;

import GC_11.distributed.socket.ClientGame;
import GC_11.model.GameView;
import GC_11.model.Player;
import GC_11.view.CLIview;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class AppClientImpl {
    public static void main( String[] args ) {
        Scanner input = new Scanner(System.in);
        System.out.println("---CLIENT---\nEnter ip address of the server");
        String ip = input.next();
        ClientGame client = new ClientGame(ip, 4321);
        try{
            client.startClient();
        }catch (IOException e){
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }

    }
}
