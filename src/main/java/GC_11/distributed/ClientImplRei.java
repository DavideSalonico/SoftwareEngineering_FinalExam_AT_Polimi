package GC_11.distributed;

import GC_11.exceptions.ExceededNumberOfPlayersException;
import GC_11.exceptions.NameAlreadyTakenException;
import GC_11.model.GameViewMessage;
import GC_11.network.LobbyViewMessage;
import GC_11.util.Choice;
import GC_11.view.GameCLI;
import GC_11.view.LobbyCLI;
import GC_11.view.ViewGame;
import GC_11.view.ViewLobby;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ClientImplRei extends UnicastRemoteObject implements ClientRei {

    private ViewLobby viewLobby;
    private ViewGame viewGame;
    private String nickname;

    private PropertyChangeListener listener;

    private ServerRei server;

    public ClientImplRei(ViewLobby viewLobby) throws RemoteException {
        this.viewLobby = viewLobby;
    }

    public ClientImplRei(ServerRei server, String nickname) throws RemoteException {
        this.nickname = nickname;
        System.out.println("HELLO " + nickname + "!!!\n");
        viewLobby = new LobbyCLI();
        try {
            //System.out.println(server.toString());
            server.register(this);
            this.server = server;
        } catch (Exception e){
            System.err.println("error in the registration: "+ e.getCause() + "\n" + e.getMessage() );
        }
    }

    public int askMaxNumber() {
        boolean b = true;
        int number = 0;
        while(b) {
            Scanner inputLine = new Scanner(System.in);
            System.out.println("How many players do you want to play with ?");
            number = inputLine.nextInt();

            if(number <5 && number >1 ){
                b=false;
            }
            else{
                System.out.println("matches only go from 2 to 4 players");
            }
        }
        return number;
    }

    public void updateStartGame(GameViewMessage newView) throws RemoteException {
        viewGame = new GameCLI(newView.getPlayer(this.nickname), this);
        updateViewGame(newView);
    }

    @Override
    public void notifyServer(PropertyChangeEvent evt) throws RemoteException {
        if (evt.getPropertyName().equals("UPDATE LOBBY")) {
            //server.updateLobby();
        }
        if (evt.getPropertyName().equals("CHOICE")) {
            try {
                server.updateGame(this, (Choice) evt.getNewValue());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
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

    public String getNickname() {
        return nickname;
    }

    public void run() throws RemoteException {
        viewLobby.run();
    }

    /*public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("UPDATE LOBBY")) {
            //server.updateLobby();
        }
        if (evt.getPropertyName().equals("CHOICE")) {
            try {
                server.updateGame( this, (Choice) evt.getNewValue());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }*/

}

