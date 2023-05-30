package GC_11.distributed.socket;


import GC_11.distributed.Client;
import GC_11.distributed.ClientRei;
import GC_11.model.GameViewMessage;
import GC_11.model.Player;
import GC_11.network.LobbyViewMessage;
import GC_11.network.MessageView;
import GC_11.util.Choice;
import GC_11.view.View;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class ClientSock implements PropertyChangeListener{

    String ip;
    int port;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private View view;



     public ClientSock(String ip, int port){

         this.port = port;
         this.ip = ip;

         try {
             System.out.println("Connecting to server on port " + port);
             socket = new Socket(ip, port);
             out = new ObjectOutputStream(socket.getOutputStream());
             in = new ObjectInputStream(socket.getInputStream());
         } catch (UnknownHostException e) {
             System.out.println("Unknown host");
             e.printStackTrace();
         } catch (IOException e) {
             System.out.println("Error in loading streams");
             e.printStackTrace();
         }
         finally {
                System.out.println("Connection established");
         }
     }

    public void sendMessageToServer(String s){
            try {
                out.writeObject(s);
                out.flush();
                out.reset();
            } catch (IOException e) {
                System.out.println("Error during sending message to server");
            }
     }

     public void sendChoiceToServer(Choice c){
         try{
             out.writeObject(c);
             out.flush();
             out.reset();
         } catch (IOException e) {
             System.out.println("Error during sending message to server");
         }
     }

     public void receiveMessageFromServer() throws IOException, ClassNotFoundException {

         try {
             String serverChoice = (String) in.readObject();
             System.out.println("Received choice from server: "+ serverChoice);
         } catch (IOException e) {
             System.out.println("Error during receiving message from server. Check server connection");
             throw new IOException();
         } catch (ClassNotFoundException e) {
             System.out.println("Error during deserialization of message from server. Check server connection");
             throw new ClassNotFoundException();
         }
     }

     public void receiveGameViewFromServer(){
            try {
                MessageView messageView = (MessageView) in.readObject();
                System.out.println("Received gameViewMessage from server: "+ messageView.toString());
                // Una volta ricevuto il messaggio notifico la view
                this.view.propertyChange(new PropertyChangeEvent(this,"gameViewMessage",null,messageView));
            } catch (IOException e) {
                System.out.println("Error during receiving gameViewMessage from server. Check server connection");
            } catch (ClassNotFoundException e) {
                System.out.println("Error during deserialization of gameViewMessage from server. Check server connection");
            }
     }

     Thread readThread = new Thread(new Runnable() {

         @Override
         public void run() {
             System.out.println("Running read Thread");
             boolean connectionAvailable = true;
             while (connectionAvailable)
             {
                 try {
                     receiveMessageFromServer();
                 } catch (IOException | ClassNotFoundException e) {
                     connectionAvailable=false;
                 }
             }
         }
     });

     Thread readGameViewThread = new Thread(new Runnable() {

         @Override
         public void run() {
             System.out.println("Running readGameViewThread Thread");
             boolean connectionAvailable = true;
             while (connectionAvailable)
             {
                 try {
                     receiveGameViewFromServer();
                 } catch (Exception e) {
                     connectionAvailable=false;
                 }
             }
         }
     });

     Thread writeThread = new Thread(new Runnable() {
         Scanner inputLine = new Scanner(System.in);
         @Override
         public void run() {
             System.out.println("Running write Thread");
             while (true){
                 System.out.println("Insert message to send to server");
                 String s = inputLine.nextLine();
                 sendMessageToServer(s);
             }
         }
     });


    /**
     *  Called whenever a view is sending a choice to the server
     * @param evt A PropertyChangeEvent object describing the event source
     *          and the property that has changed. Contains the choice
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
         sendChoiceToServer((Choice) evt.getNewValue());
    }

    public void startClient() {
        System.out.println("ClientSocket running");
        readThread.start();
        writeThread.start();
    }

    private void closeConnection(){
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Error during closing connection");
        }
    }

    public void setView(View view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }
}

