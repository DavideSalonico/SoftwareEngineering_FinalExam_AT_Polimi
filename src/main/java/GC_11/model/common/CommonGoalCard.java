package GC_11.model.common;

import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.model.GameView;
import GC_11.model.Player;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class CommonGoalCard implements Serializable{

    private List<Player> winningPlayers = new ArrayList<>();
    private static String text;

    PropertyChangeListener listener;

    protected void givePoints(Player player){
        List<Player> oldWinning = this.winningPlayers;
        winningPlayers.add(player);
        int point = 10 - 2*winningPlayers.size();
        player.addPointsCommonGoals(point);

        PropertyChangeEvent evt = new PropertyChangeEvent(
                this,
                "CHANGED_WINNING_PLAYERS",
                oldWinning,
                this.winningPlayers);
        this.listener.propertyChange(evt);
    }

    public abstract void check(Player player) throws ColumnIndexOutOfBoundsException;

    public List<Player> getWinningPlayers() {
        return winningPlayers;
    }

    public String getText() {
        return text;
    }

    public void setListener(PropertyChangeListener listener){
        this.listener = listener;
    }
}

