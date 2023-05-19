package GC_11.distributed;

import GC_11.exceptions.ExceededNumberOfPlayersException;
import GC_11.exceptions.NameAlreadyTakenException;
import GC_11.model.GameViewMessage;
import GC_11.network.LobbyViewMessage;
import GC_11.view.LobbyCLI;
import GC_11.view.ViewGame;
import GC_11.view.ViewLobby;

import java.util.Scanner;

public class ClientImplRei implements ClientRei{

    private ViewLobby viewLobby;
    private ViewGame viewGame;
    private String nickname;

    public ClientImplRei(ServerRei server, String nickname) throws ExceededNumberOfPlayersException, NameAlreadyTakenException {
        this.nickname = nickname;
        System.out.println("HELLO " + nickname + "!!!");
        viewLobby = new LobbyCLI();
        server.register(this);
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
        System.out.println("fatto!");
    }

    @Override
    public void updateViewGame(GameViewMessage newView) {

    }

    public String getNickname() {
        return nickname;
    }

    public void run() {
        viewLobby.run();
    }
}
