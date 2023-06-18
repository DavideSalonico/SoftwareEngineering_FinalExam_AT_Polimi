package GC_11;

import GC_11.distributed.ClientImplRei;
import GC_11.distributed.ServerImplRei;
import GC_11.distributed.ServerRei;
import GC_11.exceptions.ExceededNumberOfPlayersException;
import GC_11.exceptions.NameAlreadyTakenException;

import java.rmi.RemoteException;
import java.util.Random;
import java.util.Scanner;

public class AppRei {
    String nickname;


    public static void main( String[] args ) throws ExceededNumberOfPlayersException, NameAlreadyTakenException, RemoteException {
        Random random = new Random();
        int[] idArray = random.ints(6, 44, 32).distinct().toArray();
        for(int i : idArray){
            System.out.println(i);
        }

    }
}
