package GC_11.distributed.socket;

import GC_11.distributed.Client;
import GC_11.network.message.GameViewMessage;
import GC_11.network.message.LobbyViewMessage;
import GC_11.network.message.MessageView;
import GC_11.network.choices.Choice;
import GC_11.view.GUI.GUIModel;
import GC_11.view.GUI.GUIView;
import GC_11.view.GameCLI;
import GC_11.view.LobbyCLI;
import GC_11.view.View;
import javafx.application.Application;

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
    GameViewMessage gameViewMessage;
    private View view;
    private LobbyCLI lobbyCLI=null;
    private String nickname;


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
        this.lobbyCLI=new LobbyCLI();

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
        String choiceString = choice.toString();
        sendMessageToServer(choiceString);
    }

    public void sendMessageToServer(String s) {
        try {
            out.writeObject(s);
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

    Thread readThread = new Thread(new Runnable() {

        @Override
        public void run() {
            System.out.println("Running read Thread");
            boolean connectionAvailable = true;
            while (connectionAvailable) {
                try {
                    receiveMessageFromServer();
                } catch (IOException | ClassNotFoundException e) {
                    connectionAvailable = false;
                }
            }
        }
    });

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

    Client self = this;
    Thread writeThread = new Thread(new Runnable() {
        Scanner inputLine = new Scanner(System.in);

        @Override
        public void run() {
            System.out.println("Running write Thread");
            while (true) {
                //System.out.println("Insert message to send to server");
                String s = inputLine.nextLine();
                if (nickname == null || nickname.isEmpty()) {
                    if (gameViewMessage.getMessage().equals("Hi! Welcome to the game! Please, insert your nickname:")) {
                        nickname = s;
                        setView(new GameCLI(nickname, self));
                    }
                }
                sendMessageToServer(s);
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
        //readThread.start();
        //writeThread.start();

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
        if (evt.getPropertyName().equals("CHOICE"))
            sendMessageToServer((evt.getNewValue()).toString());
    }

    public void updateViewGame(GameViewMessage gameViewMessage) {
        this.view.propertyChange(new PropertyChangeEvent(this,
                "UPDATE GAME",
                null,
                gameViewMessage));
    }

    public String getNickname() {
        return this.nickname;
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

