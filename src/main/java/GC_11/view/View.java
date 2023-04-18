package GC_11.view;

import GC_11.model.GameView;
import GC_11.model.Player;
import GC_11.util.Choice;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Abstract class view, father of all the views,it defines the signature of all the method that the view require to print
 * all the necessary data during the Game
 */
public abstract class View implements PropertyChangeListener, Runnable{

    protected Player player;

    protected GameView modelView;

    //Controller must register
    PropertyChangeListener listener;

    public Player getPlayer(){
        return player;
    }

    public abstract void show();

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("evt" + evt);
        System.out.println("Event Type: " + evt.getPropertyName());
        this.modelView = (GameView) evt.getNewValue();
    }

    public abstract Choice getPlayerChoice();

    public void run(){
        show();
        Choice.Type choice = getPlayerChoice();
        PropertyChangeEvent evt = new PropertyChangeEvent(
                this,
                "CHOICE",
                null,
                choice);
        this.listener.propertyChange(evt);
    }

    public void setListener(PropertyChangeListener listener) {
        this.listener = listener;
    }
}
