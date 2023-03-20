package GC_11.model;

import java.util.ArrayList;
import java.util.List;

public class Lobby {

    private static int lobbyNumber;
    private int lobbyId;
    private int maxPlayers;
    private List<Player> players;

    public Lobby(int maxPlayers){
        this.maxPlayers=maxPlayers;
        players = new ArrayList<Player>();
        this.lobbyId = lobbyNumber;
        this.lobbyNumber++;
    }

    public synchronized void addPlayer(Player player){
        if (players.size()< maxPlayers && !players.contains(player)){
            players.add(player);
        }
    }

    public void removePlayer(Player player){
        if (players.contains(player)){
            players.remove(player);
        }
    }

    public boolean isFull(){
        return (players.size()==maxPlayers);
    }

    public boolean hasPlayer(Player player){
        return (players.contains(player));
    }

    public List<Player> getPlayers() {
        return players;
    }

    public static int getLobbyNumber() {
        return lobbyNumber;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

}
