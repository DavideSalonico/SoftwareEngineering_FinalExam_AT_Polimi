package GC_11.network.choices;

import GC_11.controller.Controller;
import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.exceptions.IllegalMoveException;
import GC_11.exceptions.NotEnoughFreeSpacesException;
import GC_11.model.Player;

import java.rmi.RemoteException;
import java.util.List;

public class MaxPlayersChoice extends Choice{
    public MaxPlayersChoice(Player player, List<String> params, ChoiceType type) throws IllegalMoveException {
        super(player, params, type);

        if (params.size() != 1) throw new IllegalMoveException();
        if (Integer.parseInt(params.get(0)) < 2 || Integer.parseInt(params.get(0)) > 4) throw new IllegalMoveException();
    }

    @Override
    public void executeOnServer(Controller controller){
        controller.setMaxPlayers(Integer.parseInt(params.get(0)));
    }
}
