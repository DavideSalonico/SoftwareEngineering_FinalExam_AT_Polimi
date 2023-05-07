package GC_11.util;

import GC_11.model.Player;

public class LobbyChoice extends Choice{

    public LobbyChoice(Player player, String input) throws IllegalArgumentException {
        super(player, input);
    }

    public enum Type{
        LOGIN,
        FIND_MATCH,

    }
}
