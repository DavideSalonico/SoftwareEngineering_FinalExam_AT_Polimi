package GC_11.distributed;

import GC_11.distributed.socket.ClientSock;
import GC_11.view.LobbyTUI;
import GC_11.view.View;


import java.io.IOException;

import java.util.Scanner;

public class AppClientImpl {
    public static void main( String[] args ) {
        //Scanner input = new Scanner(System.in);
        //System.out.println("---CLIENT---\nEnter ip address of the server");
        //String ip = input.next();
        View view = new LobbyTUI();
        ClientSock client = new ClientSock(view);
        ((LobbyTUI) view).setClient(client);
        try{
            client.startClient();
        }catch (IOException e){
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
}
