package GC_11.model;

import java.util.ArrayList;
import java.util.List;

public class Lobby {

    private final int lobbyID;
    private static int lobbyNumber;
    private int maxPlayers;

    private List<String> playersNames;

    private List<Game> gamesList;

    public Lobby(int maxPlayers){
        this.maxPlayers=maxPlayers;
        playersNames = new ArrayList<String>();
        this.lobbyID=lobbyNumber;
        lobbyNumber++;
    }

    public boolean nameAlreadyTaken(String playerName){
        return playersNames.contains(playerName);
    }

    public synchronized void addPlayer(String playerName){
        if (playersNames.size()< maxPlayers && !playersNames.contains(playerName)){
            playersNames.add(playerName);
        }
    }

    public void removePlayer(String playerName){
        if (playersNames.contains(playerName)){
            playersNames.remove(playerName);
        }
    }

    public boolean isFull(){
        return (playersNames.size()==maxPlayers);
    }

    public boolean hasPlayer(String playerName){
        return (playersNames.contains(playerName));
    }

    public List<String> getPlayers() {
        return playersNames;
    }

    public int getLobbyID() {
        return lobbyID;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void startGame(){
        if(playersNames.size()==maxPlayers)
            this.gamesList.add(new Game());
    }

}
