package GC_11.distributed;

import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.network.GameViewMessage;
import GC_11.network.LobbyViewMessage;
import GC_11.network.choices.Choice;
import GC_11.view.GUI.GUIModel;
import GC_11.view.GUI.GUIView;
import GC_11.view.GameCLI;
import GC_11.view.LobbyCLI;
import GC_11.view.ViewGame;
import GC_11.view.ViewLobby;
import javafx.application.Application;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Objects;
import java.util.Scanner;

public class ClientImplRMI extends UnicastRemoteObject implements ClientRMI {

    private ViewLobby viewLobby;
    private ViewGame viewGame;
    private String nickname;

    private PropertyChangeListener listener;

    private ServerRMI server;

    public ClientImplRMI(ViewLobby viewLobby) throws RemoteException {
        this.viewLobby = viewLobby;
    }

    public ClientImplRMI(ServerRMI server, String nickname) throws RemoteException {
        this.nickname = nickname;
        System.out.println("HELLO " + nickname + "!!!\n");
        viewLobby = new LobbyCLI();
        viewGame = new GameCLI(this.nickname, this);
        try {
            //System.out.println(server.toString());
            server.register(this);
            this.server = server;
        } catch (Exception e) {
            System.err.println("error in the registration: " + e.getCause() + "\n" + e.getMessage() + "\n" + e.getStackTrace() + "\n\n\n" + e.toString());
        }
    }

    public ClientImplRMI(ServerRMI server, String nickname, String choiceInterface) throws RemoteException {
        this.nickname = nickname;
        System.out.println("HELLO " + nickname + "!!!\n");
        if(Objects.equals(choiceInterface, "CLI")) {
            viewLobby = new LobbyCLI();
            viewGame = new GameCLI(this.nickname, this);
        }
        else{
            // creare la logica della lobby GUI per il momento uso la CLI
            viewLobby = new LobbyCLI();
            viewGame = new GUIModel(this.nickname, this);
            Application.launch(GUIView.class);
        }
        try {
            //System.out.println(server.toString());
            server.register(this);
            this.server = server;
        } catch (Exception e) {
            System.err.println("error in the registration: " + e.getCause() + "\n" + e.getMessage() + "\n" + e.getStackTrace() + "\n\n\n" + e.toString());
        }
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
    public void notifyServer(PropertyChangeEvent evt) throws RemoteException {
        if (evt.getPropertyName().equals("UPDATE LOBBY")) {
            //server.updateLobby();
        }
        if (evt.getPropertyName().equals("CHOICE")) {
            new Thread(() -> {
                try {
                    server.updateGame(this, (Choice) evt.getNewValue());
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }

    @Override
    public String getNickname() throws RemoteException {
        return this.nickname;
    }

    @Override
    public void updateViewLobby(LobbyViewMessage newView) {
        PropertyChangeEvent evt = new PropertyChangeEvent(
                this,
                "UPDATE LOBBY",
                null,
                newView);
        this.viewLobby.propertyChange(evt);
    }

    @Override
    public void updateViewGame(GameViewMessage newView) {
        PropertyChangeEvent evt = new PropertyChangeEvent(
                this,
                "UPDATE GAME",
                null,
                newView);
        this.viewGame.propertyChange(evt);
    }




}

