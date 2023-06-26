package GC_11.network.choices;

import GC_11.controller.Controller;
import GC_11.exceptions.ExceededNumberOfPlayersException;
import GC_11.exceptions.IllegalMoveException;
import GC_11.exceptions.NameAlreadyTakenException;
import GC_11.model.Player;

import java.rmi.RemoteException;
import java.util.List;

public class SetMaxNumberChoice extends Choice{
    public SetMaxNumberChoice(Player player, List<String> params, ChoiceType type) throws IllegalMoveException {
        super(player, params, type);
    }

    @Override
    public void executeOnServer(Controller controller) throws RemoteException, ExceededNumberOfPlayersException, NameAlreadyTakenException {
        controller.setMaxPlayers(Integer.parseInt(params.get(0)));
    }
}
