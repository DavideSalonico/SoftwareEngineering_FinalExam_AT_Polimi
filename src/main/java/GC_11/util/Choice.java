package GC_11.util;

import java.io.Serializable;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Choice implements Serializable{
    public Choice(String input) throws IllegalArgumentException {
        String[] tmp = input.split(" ");

        this.choice = Choice.Type.valueOf(tmp[0]);

        //Forse si potrebbe cambiare il tipo di tmp in modo da copiare direttamente i parametri in params e in questo switch fare i controlli
        switch(this.choice){
            case SEE_COMMONGOAL:
            case PICK_COLUMN:
                this.params.set(0, tmp[1]); //column index
            case INSERT_NAME:
                this.params.set(0, tmp[1]); //name
                break;
            case SEE_PERSONALGOAL:
            case FIND_MATCH:
                break;
            case CHOOSE_ORDER:
                //Gestire il numero di carte pescate
                this.params.set(0, tmp[1]); //first card to be inserted index
                this.params.set(1, tmp[2]); //second card to be inserted index
                this.params.set(2, tmp[3]); //third card to be inserted index
                break;
            case SELECT_TILE:
                this.params.set(0, tmp[1]); //row
                this.params.set(1, tmp[2]); //column
            case LOGIN:
                this.params.set(0, tmp[1]); //name
                this.params.set(1, tmp[2]); //password
                break;
        }
    }

    public enum Type{
        INSERT_NAME, LOGIN, FIND_MATCH, SEE_COMMONGOAL, SEE_PERSONALGOAL, SELECT_TILE, PICK_COLUMN, CHOOSE_ORDER
    }

    private List<String> params;
    private Type choice;

    public Type getChoice() {
        return choice;
    }

    public List<String> getParams() {
        return params;
    }

    //Parameters
    //Common Goal: 0 o 1 (int)
    //Personal Goal: NO PARAMS
    //Select Tile: riga e colonna (int, int)
}
