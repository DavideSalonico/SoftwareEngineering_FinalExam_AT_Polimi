package GC_11.view;

import GC_11.model.GameViewMessage;
import GC_11.util.choices.Choice;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;

public abstract class ViewGame extends View{

    public abstract Choice getPlayerChoice();

    protected GameViewMessage modelView;



    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    protected abstract void seeCommonGoal(Choice choice);

    protected abstract void seePersonalGoal(Choice choice);

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.modelView = (GameViewMessage) evt.getNewValue();
        run();
    }
}
