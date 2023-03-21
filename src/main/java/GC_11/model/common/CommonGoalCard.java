package GC_11.model.common;

import GC_11.model.Player;

import java.util.List;

public abstract class CommonGoalCard {

    private List<Player> winningPlayers;
    private String text;

    public abstract int calculatePoints(Player p);
    public abstract boolean check();

    public List<Player> getWinningPlayers() {
        return winningPlayers;
    }

    public String getText() {
        return text;
    }
}
