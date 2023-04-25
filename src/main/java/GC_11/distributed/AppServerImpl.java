package GC_11.distributed;

import GC_11.distributed.socket.ServerGame;
import GC_11.network.Lobby;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class AppServerImpl  {

    public static void main(String[] args){
        ServerGame server = new ServerGame(4321);
        try{
            server.startServer();
        }
        catch(IOException | ClassNotFoundException e){
            System.err.println(e.getMessage());
        }
    }

}
