package GC_11;

import GC_11.distributed.ClientImplRei;
import GC_11.distributed.ServerImplRei;
import GC_11.distributed.ServerRei;
import GC_11.exceptions.ExceededNumberOfPlayersException;
import GC_11.exceptions.NameAlreadyTakenException;

import java.rmi.RemoteException;
import java.util.Scanner;

public class AppRei {
    String nickname;


    public static void main( String[] args ) throws ExceededNumberOfPlayersException, NameAlreadyTakenException, RemoteException {
        String string = "\u001B[103m\033[4;30m  \u001B[0m";
        System.out.println(string);

    }
}
