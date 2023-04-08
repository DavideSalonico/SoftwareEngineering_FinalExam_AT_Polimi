package GC_11.model;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class GameView{

    private final Game model;

    public GameView(Game model){
        if (model == null){
            throw new IllegalArgumentException();
        }
        this.model = model;
    }

    public Board getBoard() { return model.getBoard(); }

    public List<Player> getPlayers(){ return model.getPlayers(); }
}
