package GC_11;

import GC_11.distributed.ClientImplRei;
import GC_11.distributed.ServerImplRei;
import GC_11.distributed.ServerRei;
import GC_11.exceptions.ExceededNumberOfPlayersException;
import GC_11.exceptions.NameAlreadyTakenException;

import java.util.Scanner;

public class AppRei {
    String nickname;


    public static void main( String[] args ) throws ExceededNumberOfPlayersException, NameAlreadyTakenException {
        Scanner inputLine = new Scanner(System.in);

        System.out.println("what's your nickname?");
        String nickname = inputLine.nextLine();
        ServerRei server = new ServerImplRei();
        ClientImplRei client = new ClientImplRei(server, nickname);
        //client.run();
    }
}
