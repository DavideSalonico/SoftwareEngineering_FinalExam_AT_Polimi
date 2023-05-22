package GC_11.distributed;

import GC_11.exceptions.ExceededNumberOfPlayersException;
import GC_11.exceptions.NameAlreadyTakenException;
import GC_11.model.GameViewMessage;
import GC_11.network.LobbyViewMessage;
import GC_11.view.GameCLI;
import GC_11.view.LobbyCLI;
import GC_11.view.ViewGame;
import GC_11.view.ViewLobby;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ClientImplRei extends UnicastRemoteObject implements ClientRei{

    private ViewLobby viewLobby;
    private ViewGame viewGame;
    private String nickname;

    public ClientImplRei(ViewLobby viewLobby) throws RemoteException {
        super();
        this.viewLobby = viewLobby;
    }

    public ClientImplRei(ServerRei server, String nickname) throws ExceededNumberOfPlayersException, NameAlreadyTakenException, RemoteException {
        super();
        this.nickname = nickname;
        System.out.println("HELLO " + nickname + "!!!\n");
        viewLobby = new LobbyCLI();
        viewGame = new GameCLI(null);
        try {
            //System.out.println(server.toString());
            server.register(this);
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
    @Override
    public void updateViewLobby(LobbyViewMessage newView) {
        this.viewLobby.update(newView);
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

    public void run() {
        viewLobby.run();
    }
}
