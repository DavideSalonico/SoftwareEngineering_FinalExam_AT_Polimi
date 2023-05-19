package GC_11.view;

import GC_11.model.Player;
import GC_11.network.MessageView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static java.lang.Integer.parseInt;

/**
 * Abstract class view, father of all the views,it defines the signature of all the method that the view require to print
 * all the necessary data during the Game
 */
public abstract class View implements PropertyChangeListener{

    protected Player player;
    protected MessageView messageView;
    boolean inGame = true;


    //Controller must register
     PropertyChangeListener listener;

    public Player getPlayer(){
        return player;
    }

    public abstract void show();

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //System.out.println("evt" + evt);
        System.out.println("Event Type: " + evt.getPropertyName());
        this.messageView = (MessageView) evt.getNewValue();
    }



    public abstract void run();

    public void setListener(PropertyChangeListener listener) {
        this.listener = listener;
    }

}
