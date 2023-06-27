package GC_11.distributed.socket;

import GC_11.distributed.Client;
import GC_11.network.message.GameViewMessage;
import GC_11.network.message.LobbyViewMessage;
import GC_11.network.message.MessageView;
import GC_11.network.choices.Choice;
import GC_11.view.GameCLI;
import GC_11.view.View;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class ClientSock implements PropertyChangeListener, Client {
    String ip;
    int port;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    String graphicInterface;
    private View view;


    public ClientSock(String ip, int port) {

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
        } finally {
            System.out.println("Connection established");
        }
    }

    //Only one to use
    public ClientSock(String ip, int port, String gInterface) {

        this.port = port;
        this.ip = ip;
        this.graphicInterface=gInterface;
        this.view = new GameCLI(null,this); //TODO: change this to the right view

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
        } finally {
            System.out.println("Connection established");
        }
    }

    public void notifyServer(Choice choice){
        sendMessageToServer(choice);
    }

    @Override
    public String getNickname() throws RemoteException {
        return this.view.getNickname(); //TODO
    }

    public void sendMessageToServer(Choice choice) {
        try {
            out.writeObject(choice);
            out.flush();
            out.reset();
        } catch (IOException e) {
            System.out.println("Error during sending message to server");
        }
    }

    public void sendChoiceToServer(Choice c) {
        try {
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
            System.out.println("Received choice from server: " + serverChoice);
        } catch (IOException e) {
            System.out.println("Error during receiving message from server. Check server connection");
            throw new IOException();
        } catch (ClassNotFoundException e) {
            System.out.println("Error during deserialization of message from server. Check server connection");
            throw new ClassNotFoundException();
        }
    }

    public void receiveFromServer(MessageView message) throws RemoteException {
        receiveGameViewFromServer();
    }


    public void receiveGameViewFromServer() {
        try {
            MessageView msg = (MessageView) in.readObject();
            msg.executeOnClient(this);
        } catch (IOException e) {
            System.out.println("Error during receiving gameViewMessage from server. Check server connection");
        } catch (ClassNotFoundException e) {
            System.out.println("Error during deserialization of gameViewMessage from server. Check server connection");
        }
    }

    Thread readGameViewThread = new Thread(new Runnable() {
        @Override
        public void run() {
            System.out.println("Running readGameView Thread");
            boolean connectionAvailable = true;
            while (connectionAvailable) {
                try {
                    receiveGameViewFromServer();
                } catch (Exception e) {
                    connectionAvailable = false;
                }
            }
        }
    });



    /**
     * Called whenever a view is sending a choice to the server
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed. Contains the choice
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        sendChoiceToServer((Choice) evt.getNewValue());
    }

    public void startClient() {
        System.out.println("ClientSocket running");
        readGameViewThread.start();


    }

    private void closeConnection() {
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

    public void notifyServer(PropertyChangeEvent evt) {
        //if (evt.getPropertyName().equals("CHOICE"))
            //sendMessageToServer((evt.getNewValue()).toString());
    }

    public void updateViewGame(GameViewMessage gameViewMessage) {
        this.view.propertyChange(new PropertyChangeEvent(this,
                "UPDATE GAME",
                null,
                gameViewMessage));
    }

    @Override
    public int askMaxNumber() {
        //TODO
        return -1;
    }

    @Override
    public void notifyDisconnection() {
        //TODO
    }
}

