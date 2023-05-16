package GC_11.distributed;

import GC_11.distributed.socket.ClientSock;
import GC_11.view.LobbyTUI;
import GC_11.view.View;


import java.io.IOException;

import java.util.Scanner;

public class AppClientImpl {
    public static void main( String[] args ) {

        View view = new LobbyTUI();
        ClientSock client = new ClientSock(view);
        ((LobbyTUI) view).setClient(client);
        client.setIp("127.0.0.1");
        client.setPort(4321);
        try{
            client.startClient();
        }catch (IOException e){
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
}
