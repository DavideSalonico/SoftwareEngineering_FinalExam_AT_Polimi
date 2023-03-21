package GC_11.model.common;

import GC_11.exceptions.columnIndexOutOfBoundsException;
import GC_11.model.Player;

import java.util.List;

public abstract class CommonGoalCard {

    private List<Player> winningPlayers;
    private String text;

    public abstract int calculatePoints(Player player);
    public abstract void check(Player player) throws columnIndexOutOfBoundsException;

    public List<Player> getWinningPlayers() {
        return winningPlayers;
    }

    public String getText() {
        return text;
    }
}

