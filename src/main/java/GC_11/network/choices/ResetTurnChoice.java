package GC_11.network.choices;

import GC_11.controller.Controller;
import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.exceptions.IllegalMoveException;
import GC_11.exceptions.NotEnoughFreeSpacesException;
import GC_11.model.Player;

import java.util.List;

public class ResetTurnChoice extends Choice {

    public ResetTurnChoice(Player player, List<String> params, ChoiceType type) throws IllegalArgumentException {
        super(player, params, type);

        if (params.size() != 0) throw new IllegalArgumentException();
    }

    @Override
    public void executeOnServer(Controller controller) throws IllegalMoveException, ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {
        controller.resetTurn(params);
    }
}
