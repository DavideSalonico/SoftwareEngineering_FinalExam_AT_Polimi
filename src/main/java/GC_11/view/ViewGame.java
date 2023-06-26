package GC_11.view;

import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.network.message.GameViewMessage;
import GC_11.network.choices.Choice;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;

public abstract class ViewGame extends View {

    protected String nickname;
    protected boolean inGame = true;
    protected GameViewMessage modelView;
    public abstract Choice getPlayerChoice();
    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.modelView = (GameViewMessage) evt.getNewValue();
        if( !(evt.getPropertyName().equals("CHANGED_MAIN_CHAT") || evt.getPropertyName().equals("CHANGED_PRIVATE_CHAT"))
        || !this.modelView.getCurrentPlayer().equals(this.nickname)) {
            try {
                run();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            } catch (ColumnIndexOutOfBoundsException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
