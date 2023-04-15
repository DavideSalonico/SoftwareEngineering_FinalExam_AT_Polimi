package GC_11.util;

import java.io.Serializable;

public class Choice implements Serializable{
    public enum Type{
        INSERT_NAME, LOGIN, FIND_MATCH, SEE_COMMONGOAL, SEE_PERSONALGOAL, SELECT_TILE, PICK_COLUMN, CHOOSE_ORDER
    }

    private String params;
    private Type choice;

    public Type getChoice() {
        return choice;
    }

    public String getParams() {
        return params;
    }
}
