package GC_11.model.common;

import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.model.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class CommonGoalCard implements Serializable {

    private List<Player> winningPlayers = new ArrayList<>();
    private String text;

    public void givePoints(Player player){
        winningPlayers.add(player);
        int point = 10 - 2*winningPlayers.size();
        player.addPoints(point);
    }
    public abstract void check(Player player) throws ColumnIndexOutOfBoundsException;

    public List<Player> getWinningPlayers() {
        return winningPlayers;
    }

    public String getText() {
        return text;
    }
}

