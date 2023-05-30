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
    public void propertyChange(PropertyChangeEvent evt){
        //Questo metodo viene chiamato dal client ogni qualvolta riceve un messaggio dal server
        this.messageView = (MessageView) evt.getNewValue();
        //run(); --> Aggiorna la view vista dall'utente (la grafica)

    }

    public abstract void run();

    public void setListener(PropertyChangeListener listener) {
        this.listener = listener;
    }

}
