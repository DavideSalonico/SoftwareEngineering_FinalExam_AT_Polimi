package GC_11.distributed;

import GC_11.distributed.socket.ServerSock;


import java.io.IOException;


public class AppServerImpl  {

    public static void main(String[] args){
        ServerSock server = new ServerSock(4321);
        try{
            server.startServer();
        }
        catch(IOException | ClassNotFoundException e){
            System.err.println(e.getMessage());
        }
    }

}
