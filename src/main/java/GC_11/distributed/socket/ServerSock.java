package GC_11.distributed.socket;


import GC_11.network.Lobby;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerSock {

    Lobby lobby;
    private final int port;
    private ServerSocket serverSocket;
    private final ExecutorService executor = Executors.newCachedThreadPool();

    private List<ServerClientHandler> serverClientHandlerList;


    public ServerSock(int port){

        // Opening TCP port
        this.port=port;

    }

    public void startServer() throws IOException, ClassNotFoundException {

        // Init phase
        try{
            this.serverSocket = new ServerSocket(this.port);
            System.out.println("---Server---");
            System.out.println("Server ready on port: "+ port);

        }
        catch(IOException e){
            System.out.println("Error during initialization phase...");
            System.out.println(e.getMessage());
        }
        this.serverClientHandlerList = new ArrayList<>();
        //this.lobby=new Lobby();

        // Waiting for connection
        while(true){
            try{
                Socket socket = serverSocket.accept();
                System.out.println("Received client connection");
                System.out.println("CLIENT INFO:\nIP: "+ socket.getInetAddress()+"\nPORT:" + socket.getPort());
                ServerClientHandler sch = new ServerClientHandler(socket,this);
                this.serverClientHandlerList.add(sch);
                executor.submit(sch);
            }
            catch (IOException e){
                System.err.println(e.getMessage());
            }
        }
    }

    public void notifyAllClients(String message, ServerClientHandler sourceHandler) {
        for (ServerClientHandler sch : serverClientHandlerList) {
            if (sch != sourceHandler) {
                sch.sendMessageToClient(message);
            }
        }
    }

    public void notifyDisconnectionAllSockets(Socket socket, ServerClientHandler sourceHandler) {
        serverClientHandlerList.remove(sourceHandler);
        for (ServerClientHandler sch : serverClientHandlerList) {
            sch.notifyDisconnection(socket);
        }
    }

    public Lobby getLobby() {
        return lobby;
    }
}

