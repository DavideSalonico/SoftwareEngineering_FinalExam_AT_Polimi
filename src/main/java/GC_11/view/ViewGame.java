package GC_11.view;

import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.network.GameViewMessage;
import GC_11.network.choices.Choice;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;

public abstract class ViewGame extends View {

    protected String nickname;
    protected boolean inGame = true;
    protected boolean alreadyReading = false;

    protected Object lock = new Object();
    protected GameViewMessage modelView;

    public abstract Choice getPlayerChoice();

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.modelView = (GameViewMessage) evt.getNewValue();
        synchronized (lock) {
            try {
                show();
            } catch (ColumnIndexOutOfBoundsException e) {
                System.err.println(e.getMessage());
            }
            if (!alreadyReading) {
                alreadyReading = true;
                new Thread(this::getPlayerChoice).start();
            }
        }
    }

    /*
            if( !(evt.getPropertyName().equals("CHANGED_MAIN_CHAT") || evt.getPropertyName().equals("CHANGED_PRIVATE_CHAT"))
        || !this.modelView.getCurrentPlayer().equals(this.nickname))
     */

}
