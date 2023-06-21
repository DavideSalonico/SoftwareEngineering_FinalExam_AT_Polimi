package GC_11.distributed;

import GC_11.distributed.socket.ClientSock;

import java.util.Scanner;

public class AppClientImpl {
    public static void main(String[] args) {
        System.out.println("Inserire indirizzo ip del server: ");
        Scanner s = new Scanner(System.in);
        String serverIp = s.nextLine();
        ClientSock client = new ClientSock(serverIp, 4322);
        client.startClient();
    }
}
