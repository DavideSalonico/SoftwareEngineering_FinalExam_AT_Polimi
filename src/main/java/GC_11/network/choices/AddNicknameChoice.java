package GC_11.network.choices;

import GC_11.controller.Controller;
import GC_11.exceptions.ExceededNumberOfPlayersException;
import GC_11.exceptions.IllegalMoveException;
import GC_11.exceptions.NameAlreadyTakenException;
import GC_11.model.Player;

import java.rmi.RemoteException;
import java.util.List;

public class AddNicknameChoice extends Choice{

    public AddNicknameChoice(Player player, List<String> params, ChoiceType type) throws IllegalMoveException {
        super(player, params, type);
    }

    // TODO: HANDLE EXCEPTIONS
    @Override
    public void executeOnServer(Controller controller) throws RemoteException, ExceededNumberOfPlayersException, NameAlreadyTakenException {

        controller.getLobby().addPlayer(params.get(0));

    }
}
