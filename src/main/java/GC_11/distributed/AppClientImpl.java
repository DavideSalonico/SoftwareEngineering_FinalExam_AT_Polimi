package GC_11.distributed;

import GC_11.distributed.socket.ClientSock;
import GC_11.view.LobbyTUI;
import GC_11.view.View;


import java.io.IOException;

import java.util.Scanner;

public class AppClientImpl {
    public static void main( String[] args ) {
        ClientSock client = new ClientSock("127.0.0.1",4321);
        client.startClient();
    }
}
