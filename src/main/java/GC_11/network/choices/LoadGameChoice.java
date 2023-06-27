package GC_11.network.choices;

import GC_11.controller.Controller;
import GC_11.controller.JsonWriter;
import GC_11.exceptions.ExceededNumberOfPlayersException;
import GC_11.exceptions.IllegalMoveException;
import GC_11.exceptions.NameAlreadyTakenException;
import GC_11.model.Game;
import GC_11.model.Player;
import GC_11.network.message.GameViewMessage;

import java.rmi.RemoteException;
import java.util.List;

public class LoadGameChoice extends Choice{
    public LoadGameChoice(Player player, List<String> params, ChoiceType type) throws IllegalMoveException {
        super(player, params, type);
    }

    @Override
    public void executeOnServer(Controller controller) throws RemoteException, ExceededNumberOfPlayersException, NameAlreadyTakenException {
        controller.setOldGameAvailable(false);
        Game newGame;
        if (params.get(0).equalsIgnoreCase("si") || params.get(0).equalsIgnoreCase("yes")){
            newGame = JsonWriter.loadGame();
            newGame.getBoard().setListener(newGame);
            newGame.setListener(controller.getServer());
        }
        else{
             newGame = new Game(controller.getLobby().getPlayers(),controller.getServer());
        }
        controller.setGame(newGame);
        controller.getServer().notifyClients(new GameViewMessage(controller.getGame(),null));
    }
}
