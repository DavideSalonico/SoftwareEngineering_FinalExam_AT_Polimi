package GC_11.network.choices;

import GC_11.controller.Controller;
import GC_11.exceptions.ExceededNumberOfPlayersException;
import GC_11.exceptions.IllegalMoveException;
import GC_11.exceptions.NameAlreadyTakenException;
import GC_11.model.Player;

import java.rmi.RemoteException;
import java.util.List;

/**
 * AddNicknameChoice is a subclass of Choice representing the choice to add a player's nickname.
 */
public class AddNicknameChoice extends Choice{

    /**
     * Constructs an AddNicknameChoice object.
     *
     * @param player the player associated with the choice.
     * @param params the parameters for the choice.
     * @param type   the type of the choice.
     * @throws IllegalMoveException if the move is illegal.
     */
    public AddNicknameChoice(Player player, List<String> params, ChoiceType type) throws IllegalMoveException {
        super(player, params, type);
    }

    /**
     * Executes the choice on the server by adding the player's nickname to the lobby.
     *
     * @param controller the controller to execute the choice on.
     * @throws RemoteException                   if a remote error occurs.
     * @throws ExceededNumberOfPlayersException   if the maximum number of players is exceeded.
     * @throws NameAlreadyTakenException          if the nickname is already taken.
     */
    @Override
    public void executeOnServer(Controller controller) throws RemoteException, ExceededNumberOfPlayersException, NameAlreadyTakenException {
        controller.getLobby().addPlayer(params.get(0));
    }
}
