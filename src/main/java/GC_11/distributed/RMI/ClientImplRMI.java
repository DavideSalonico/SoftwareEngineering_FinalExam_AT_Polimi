package GC_11.distributed.RMI;

import GC_11.distributed.Client;
import GC_11.distributed.ServerRMI;
import GC_11.network.message.GameViewMessage;
import GC_11.network.message.LobbyViewMessage;
import GC_11.network.message.MessageView;
import GC_11.network.choices.Choice;
import GC_11.view.*;
import GC_11.view.GUI.GUIModel;
import GC_11.view.GUI.GUIView;
import javafx.application.Application;

import java.beans.PropertyChangeEvent;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Objects;
import java.util.Scanner;

public class ClientImplRMI extends UnicastRemoteObject implements Client, Serializable {

    private transient ViewLobby viewLobby;
    private transient ViewGame viewGame;
    private String nickname;
    private ServerRMI serverRMI;

    public ClientImplRMI(ViewLobby viewLobby) throws RemoteException {
        this.viewLobby = viewLobby;
    }

    public ClientImplRMI(ServerRMI serverRMI, String nickname) throws RemoteException {
        this.nickname = nickname;
        System.out.println("HELLO " + nickname + "!!!\n");
        viewLobby = new LobbyCLI();
        viewGame = new GameCLI(this.nickname, this);
        try {
            //System.out.println(serverRMI.toString());
            serverRMI.register(this);
            this.serverRMI = serverRMI;
        } catch (Exception e) {
            System.err.println("error in the registration: " + e.getCause() + "\n" + e.getMessage() + "\n" + e.getStackTrace() + "\n\n\n" + e.toString());
        }
    }

    public ClientImplRMI(ServerRMI serverRMI, String nickname, String choiceInterface) throws RemoteException {
        this.nickname = nickname;
        System.out.println("HELLO " + nickname + "!!!\n");
        if(Objects.equals(choiceInterface, "CLI")) {
            viewLobby = new LobbyCLI();
            viewGame = new GameCLI(this.nickname, this);
            Application.launch(GUIView.class);
        }
        else{
            // creare la logica della lobby GUI per il momento uso la CLI
            viewLobby = new LobbyCLI();
            viewGame = new GUIModel(this.nickname, this);
        }
        try {
            serverRMI.register(this);
            this.serverRMI = serverRMI;
        } catch (RemoteException e) {
            System.err.println("error in the registration: " + e.getCause() + "\n" + e.getMessage() + "\n" + e.getStackTrace() + "\n\n\n" + e.toString());
        }
    }

    public String getNickname() {
        return this.nickname;
    }

    public int askMaxNumber() {
        boolean b = true;
        int number = 0;
        while (b) {
            Scanner inputLine = new Scanner(System.in);
            System.out.println("How many players do you want to play with ?");
            number = inputLine.nextInt();

            if (number < 5 && number > 1) {
                b = false;
            } else {
                System.out.println("matches only go from 2 to 4 players");
            }
        }
        return number;
    }

    @Override
    public View getView() {
        return this.viewGame;
    }

    @Override
    public void notifyDisconnection() throws RemoteException{
        //TODO
    }

    @Override
    public void notifyServer(Choice choice) throws RemoteException {
            new Thread(() -> {
                try {
                    serverRMI.receiveMessage(choice);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }).start();
    }

    public void updateViewLobby(LobbyViewMessage newView) {
        PropertyChangeEvent evt = new PropertyChangeEvent(
                this,
                "UPDATE LOBBY",
                null,
                newView);
        this.viewLobby.propertyChange(evt);
    }

    public void updateViewGame(GameViewMessage newView) {
        PropertyChangeEvent evt = new PropertyChangeEvent(
                this,
                "UPDATE GAME",
                null,
                newView);
        this.viewGame.propertyChange(evt);
    }

    @Override
    public void receiveFromServer(MessageView message) throws RemoteException {
        if(message instanceof LobbyViewMessage){
            updateViewLobby((LobbyViewMessage) message);
        }
        else if(message instanceof GameViewMessage){
            updateViewGame((GameViewMessage) message);
        }
    }


}

