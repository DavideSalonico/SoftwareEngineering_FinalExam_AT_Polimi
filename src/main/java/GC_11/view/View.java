package GC_11.view;

import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.exceptions.IllegalMoveException;
import GC_11.exceptions.NotEnoughFreeSpacesException;
import GC_11.model.GameView;
import GC_11.model.Player;
import GC_11.util.choices.Choice;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Abstract class view, father of all the views,it defines the signature of all the method that the view require to print
 * all the necessary data during the Game
 */
public abstract class View implements PropertyChangeListener, Runnable{

    protected Player player;

    protected GameView modelView;
    private boolean inGame = true;
    private boolean show_en = true;

     private PropertyChangeListener listener;

    public Player getPlayer(){
        return player;
    }

    public abstract void show();

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //System.out.println("evt" + evt);
        System.out.println("Event Type: " + evt.getPropertyName());
        this.modelView = (GameView) evt.getNewValue();
    }

    public abstract Choice getPlayerChoice();

    public void run(){
        while(inGame){
            if(show_en) show();
            Choice choice = getPlayerChoice();

            try {
                choice.executeOnClient(this);
            }
            catch (IllegalMoveException | ColumnIndexOutOfBoundsException | NotEnoughFreeSpacesException e) {
                System.out.println("Errore");
            }


            //switch (choice.getType()){
            //    //Controls already made in the creation of type client-side
            //    case SEE_COMMONGOAL -> {
            //        seeCommonGoal(choice);
            //        show_en = false;
            //    }
            //    case SEE_PERSONALGOAL -> {
            //        seePersonalGoal(choice);
            //        show_en = false;
            //    }
            //    default -> {
            //        PropertyChangeEvent evt = new PropertyChangeEvent(
            //                this,
            //                "CHOICE",
            //                null,
            //                choice);
            //        this.listener.propertyChange(evt);
            //        show_en = true;
            //    }
            //}
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

    public boolean isShow_en() {
        return show_en;
    }

    public void setShow_en(boolean show_en) {
        this.show_en = show_en;
    }

    public PropertyChangeListener getListener() {
        return this.listener;
    }
}
