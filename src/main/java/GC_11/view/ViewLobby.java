package GC_11.view;

import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.network.LobbyViewMessage;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;

public abstract class ViewLobby extends View {


    protected LobbyViewMessage lobbyViewMessage;


    public void setLobbyViewMessage() {
        this.lobbyViewMessage = (LobbyViewMessage) this.messageView;
        ;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.lobbyViewMessage = (LobbyViewMessage) evt.getNewValue();
        try {
            run();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (ColumnIndexOutOfBoundsException e) {
            throw new RuntimeException(e);
        }
    }
}
