package GC_11.model;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class GameView{

    private final Game model;
    //TODO:[RAM] remove model reference and copy every significant attribute

    public GameView(Game model){
        if (model == null){
            throw new IllegalArgumentException();
        }
        this.model = model;
    }

    public Board getBoard() { return model.getBoard(); }

    public List<Player> getPlayers(){ return model.getPlayers(); }

    public Player getCurrentPlayer(){ return model.getCurrentPlayer(); }
}
