package GC_11.distributed.socket;

import GC_11.util.Choice;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerGame {
    private int port;
    private ServerSocket serverSocket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public ServerGame(int port){
        this.port=port;
    }

    public void startServer() throws IOException, ClassNotFoundException {
        // Opening TCP port
        serverSocket = new ServerSocket(port);
        System.out.println("Server socket ready on port: "+port);

        // Waiting for connection
        Socket socket = serverSocket.accept();
        System.out.println("Received client connection");



        System.out.println("Initializing output stream...");
        try {
            this.outputStream=new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Got output stream");
        }
        catch (IOException e){
            System.err.println("Cannot get output stream");
            System.err.println(e.getMessage());
        }

        System.out.println("Initializing input stream...");
        try{
            this.inputStream=new ObjectInputStream(socket.getInputStream());
            System.out.println("Got input stream");
        }
        catch (IOException e){
            System.err.println("Cannot get input stream");
            System.err.println(e.getMessage());
        }


        /*while(true){
            String line = in.nextLine();
            if(line.equals("quit")){
                break;
            }else{
                out.println("Received: "+line);
                out.flush();
            }
        }
        System.out.println("Closing socket");
        in.close();
        out.close();*/
        Choice choice = (Choice) inputStream.readObject();
        System.out.println("Received: " + choice.getChoice() + choice.getParams());

        String response = "Risposta a: " + choice.getChoice() + choice.getParams();
        outputStream.writeObject(response);
        outputStream.flush();

        socket.close();
        outputStream.close();
        inputStream.close();
        serverSocket.close();
        }
}

