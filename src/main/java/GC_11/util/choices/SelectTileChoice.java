package GC_11.util.choices;

import GC_11.controller.Controller;
import GC_11.exceptions.IllegalMoveException;
import GC_11.model.Player;

import java.security.InvalidParameterException;
import java.util.List;

public class SelectTileChoice extends Choice{
    public SelectTileChoice(Player player, List<String> params, ChoiceType type) throws IllegalArgumentException {
        super(player, params, type);

        if(params.size() != 2) throw new IllegalArgumentException();
        Integer row_checker, col_checker;
        try{
            row_checker = Integer.parseInt(params.get(0));
            col_checker = Integer.parseInt(params.get(1));
        } catch(NumberFormatException e){
            throw new InvalidParameterException();
        }
        if(row_checker < 0 || row_checker >= 9 || col_checker < 0 || col_checker >= 9 ) throw new InvalidParameterException();
    }

    @Override
    public void executeOnServer(Controller controller) throws IllegalMoveException {
        controller.selectTile(params);
    }
}
