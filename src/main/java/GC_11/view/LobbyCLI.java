package GC_11.view;

import GC_11.distributed.ClientRei;
import GC_11.network.LobbyViewMessage;
import GC_11.util.choices.Choice;

import java.util.Scanner;

public class LobbyCLI extends ViewLobby{



    int number = 0;
    @Override
    public void show() {
        number=this.lobbyViewMessage.getMaxPlayers();
        System.out.println("#############################\n\nthe game is about to start !!! \nthere will be " + number + " players!\n");
        int counter = 1;
        for( String s : this.lobbyViewMessage.getPlayersNames()){
            System.out.println(counter + ": " + s);
            counter++;
        }
    }

    @Override
    public Choice getPlayerChoice() {
        return null;
    }

    public void run(){
            show();
    }

    @Override
    protected void seeCommonGoal(Choice choice) {

    }

    @Override
    protected void seePersonalGoal(Choice choice) {

    }

}
