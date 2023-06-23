package GC_11.network.choices;

import GC_11.controller.Controller;
import GC_11.exceptions.IllegalMoveException;
import GC_11.model.Player;

import java.rmi.RemoteException;
import java.util.List;

public class DeselectTileChoice extends Choice {

    public DeselectTileChoice(Player player, List<String> params, ChoiceType type) throws IllegalMoveException {
        super(player, params, type);

        if (params.size() != 0) throw new IllegalMoveException();
    }

    @Override
    public void executeOnServer(Controller controller) throws RemoteException {
        controller.deselectTile(params);
    }
}
