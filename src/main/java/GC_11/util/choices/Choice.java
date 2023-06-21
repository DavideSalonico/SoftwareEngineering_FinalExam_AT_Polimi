package GC_11.util.choices;

import GC_11.controller.Controller;
import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.exceptions.IllegalMoveException;
import GC_11.exceptions.NotEnoughFreeSpacesException;
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

    public Choice(Player player, List<String> params, ChoiceType type) throws IllegalArgumentException {
        this.type = type;
        this.player = player;
        this.params = params;
    }

    public abstract void executeOnServer(Controller controller)
            throws IllegalMoveException, ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException, RemoteException;

    public void executeOnClient(View view)
            throws IllegalMoveException, ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {
        PropertyChangeEvent evt = new PropertyChangeEvent(
                this,
                "CHOICE",
                null,
                this);
        view.getListener().propertyChange(evt);
        view.setShow_en(true);
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

    //Controls
    //switch(this.type){
    //case FIND_MATCH:
    //case DESELECT_TILE:
    //case SEE_PERSONALGOAL:
    //case RESET_TURN:
    //    if(params.size() != 0) throw new IllegalArgumentException();
    //    break;
    //case SEE_COMMONGOAL:
    //    if(params.size() != 1) throw new IllegalArgumentException();
    //    Integer common_checker;
    //    try{
    //        common_checker = Integer.parseInt(params.get(0));
    //    } catch(NumberFormatException e){
    //        throw new InvalidParameterException();
    //    }
    //    if(common_checker != 0 && common_checker != 1) throw new InvalidParameterException();
    //    break;
    //case INSERT_NAME:
    //    if(params.size() != 1) throw new IllegalArgumentException();
    //    //Chosen a Max for name length
    //    if(params.get(0).length() >= 64) throw new InvalidParameterException();
    //    break;
    //case LOGIN:
    //    if(params.size() != 2) throw new IllegalArgumentException();
    //    if(params.get(0).length() >= 64) throw new InvalidParameterException();
    //    //Chosen password minimum length
    //    if(params.get(1).length() < 8 || params.get(1).length() >= 64) throw new InvalidParameterException();
    //    break;
    //default:
    //    throw new IllegalArgumentException();
    //}
    //}
}
