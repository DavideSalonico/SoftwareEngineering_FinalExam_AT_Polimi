package GC_11.util.choices;

import GC_11.controller.Controller;
import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.exceptions.IllegalMoveException;
import GC_11.exceptions.NotEnoughFreeSpacesException;
import GC_11.model.Player;
import GC_11.view.View;

import java.util.List;

public class SendMessageChoice extends Choice{
    public SendMessageChoice(Player player, List<String> params, ChoiceType type) {
        super(player, params, type);

        //TODO: check if params are valid
    }

    @Override
    public void executeOnServer(Controller controller) throws IllegalMoveException, ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {
        controller.sendMessage(params);
    }
}
