package GC_11.model.common;

import GC_11.exceptions.columnIndexOutOfBoundsException;
import GC_11.model.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class CommonGoalCard {

    private List<Player> winningPlayers = new ArrayList<>();
    private String text;

    public void givePoints(Player player){
        winningPlayers.add(player);
        int point = 10 - 2*winningPlayers.size();
        player.addPoints(point);
    }
    public abstract void check(Player player) throws columnIndexOutOfBoundsException;

    public List<Player> getWinningPlayers() {
        return winningPlayers;
    }

    public String getText() {
        return text;
    }
}

