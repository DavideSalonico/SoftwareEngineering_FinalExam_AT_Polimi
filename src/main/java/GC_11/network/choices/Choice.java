package GC_11.network.choices;

import GC_11.controller.Controller;
import GC_11.distributed.ServerMain;
import GC_11.exceptions.*;
import GC_11.model.Player;
import GC_11.view.View;

import java.beans.PropertyChangeEvent;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;

public abstract class Choice implements Serializable {
    protected ChoiceType type;
    protected List<String> params;
    protected Player player;

    public Choice(Player player, List<String> params, ChoiceType type) throws IllegalMoveException {
        this.type = type;
        this.player = player;
        this.params = params;
    }

    public abstract void executeOnServer(Controller controller) throws RemoteException, ExceededNumberOfPlayersException, NameAlreadyTakenException;

    public void executeOnClient(View view)
            throws IllegalMoveException, ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {
        PropertyChangeEvent evt = new PropertyChangeEvent(
                this,
                "CHOICE",
                null,
                this);
        view.getListener().propertyChange(evt);
    }

    public List<String> getParams() {
        return params;
    }

    public Player getPlayer() {
        return player;
    }

    public ChoiceType getType() {
        return type;
    }

    public String toString(){
        String choiceStringified = type.toString();
        for (String p : params){
            choiceStringified += " "+p;
        }
        return choiceStringified;
    }
}
