package GC_11.util;

import java.io.Serializable;

public class Choice implements Serializable {
    public enum Type{
        INSERT_NAME, LOGIN, FIND_MATCH, SEE_COMMONGOAL, SEE_PERSONALGOAL, SELECT_TILE, PICK_COLUMN, CHOOSE_ORDER
    }

    String params;
}
