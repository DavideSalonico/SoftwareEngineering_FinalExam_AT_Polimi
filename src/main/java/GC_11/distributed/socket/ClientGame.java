package GC_11.distributed.socket;

import GC_11.model.Player;
import GC_11.util.Choice;
import GC_11.view.View;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientGame {

    private View view;
    private String ip;
    private int port;
    private Player player;

    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public ClientGame(String ip, int port){
        this.ip=ip;
        this.port=port;
        this.player=new Player();
    }

    public void startClient() throws IOException, ClassNotFoundException {
        Socket socket = new Socket(ip,port);
        System.out.println("Connection established");
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
        //

        System.out.println("Sending choice:");
        Choice choice = new Choice(player,"INSERT_NAME MATTIA");
        outputStream.writeObject(choice);
        outputStream.flush();

        String response = (String) this.inputStream.readObject();
        System.out.println("Received " + response);



        outputStream.close();
        inputStream.close();
        socket.close();
    }

}

