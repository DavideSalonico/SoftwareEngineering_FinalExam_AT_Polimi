package GC_11.view;

import GC_11.distributed.ClientRei;
import GC_11.network.LobbyViewMessage;
import GC_11.util.Choice;

import java.util.Scanner;

public class LobbyCLI extends ViewLobby{



    int number = 0;
    @Override
    public void show() {
        number=this.lobbyViewMessage.getMaxPlayers();
        System.out.println("the game is about to start !!! \nthere will be " + number + " players!");
        int counter = 1;
        for( String s : this.lobbyViewMessage.getPlayersNames()){
            System.out.println(counter + ": " + s);
            counter++;
        }
    }

    public void run(){
        while(inGame) {
            if (show_en) show();
            show_en = false;
        }
    }

    public void update (LobbyViewMessage lvm){
        this.lobbyViewMessage=lvm;
        this.show();
    }

}
