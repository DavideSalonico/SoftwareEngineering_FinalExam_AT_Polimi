package GC_11.view;

import GC_11.model.GameView;
import GC_11.model.Player;
import GC_11.util.Choice;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static java.lang.Integer.parseInt;

/**
 * Abstract class view, father of all the views,it defines the signature of all the method that the view require to print
 * all the necessary data during the Game
 */
public abstract class View implements PropertyChangeListener, Runnable{

    protected Player player;

    protected GameView modelView;
    private boolean inGame = true;

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
        while(inGame){
            show();
            Choice choice = getPlayerChoice();
            switch (choice.getChoice()){
                //Controls already made in the creation of choice client-side
                case SEE_COMMONGOAL -> seeCommonGoal(choice);
                case SEE_PERSONALGOAL -> seePersonalGoal(choice);
                default -> {
                    PropertyChangeEvent evt = new PropertyChangeEvent(
                            this,
                            "CHOICE",
                            null,
                            choice);
                    this.listener.propertyChange(evt);
                }
            }
        }
    }

    public void setListener(PropertyChangeListener listener) {
        this.listener = listener;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    protected abstract void seeCommonGoal(Choice choice);

    protected abstract void seePersonalGoal(Choice choice);
}
