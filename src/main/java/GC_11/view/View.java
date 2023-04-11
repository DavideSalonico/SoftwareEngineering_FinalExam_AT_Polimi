package GC_11.view;

import GC_11.model.GameView;
import GC_11.model.Player;
import GC_11.util.Choice;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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
        Choice choice = getPlayerChoice();
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
