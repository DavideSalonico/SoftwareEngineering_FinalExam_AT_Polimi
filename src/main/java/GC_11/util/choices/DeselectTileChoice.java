package GC_11.util.choices;

import GC_11.controller.Controller;
import GC_11.exceptions.IllegalMoveException;
import GC_11.model.Player;

import java.util.List;

public class DeselectTileChoice extends Choice{

    public DeselectTileChoice(Player player, List<String> params, ChoiceType type) throws IllegalArgumentException {
        super(player, params, type);

        if(params.size() != 0) throw new IllegalArgumentException();
    }

    @Override
    public void executeOnServer(Controller controller) throws IllegalMoveException {
        controller.deselectTile(params);
    }
}
