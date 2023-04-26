package GC_11.distributed.socket;

import GC_11.distributed.Client;
import GC_11.util.Choice;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerGame {
    private int port;
    private ServerSocket serverSocket;
    private ExecutorService executor = Executors.newCachedThreadPool();

    private HashMap<String, Socket> socketsMap;


    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public ServerGame(int port){

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
        this.socketsMap=new HashMap<String,Socket>();

        // Waiting for connection
        while(true){
            try{
                Socket socket = serverSocket.accept();
                System.out.println("Received client connection");
                System.out.println("CLIENT INFO:\nIP: "+ socket.getInetAddress()+"\nPORT:" + socket.getPort());
                executor.submit(new ServerClientHandler(socket));
            }
            catch (IOException e){
                System.err.println(e.getMessage());
            }
        }
    }

}

