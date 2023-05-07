package GC_11.util;

import GC_11.model.Player;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class GameChoice extends Choice{
    public GameChoice(Player player, String input) throws IllegalArgumentException {
        super(player, input);
    }

    public enum Type{
        SEE_COMMONGOAL,
        SEE_PERSONALGOAL,
        SELECT_TILE,
        DESELECT_TILE,
        PICK_COLUMN,
        CHOOSE_ORDER,
        RESET_TURN
    }
}
