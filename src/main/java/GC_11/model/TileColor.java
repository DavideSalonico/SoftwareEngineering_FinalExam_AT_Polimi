package GC_11.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public enum TileColor {
    WHITE,
    BLUE,
    PURPLE,
    ORANGE,
    CYAN,
    GREEN,
    EMPTY,
    PROHIBITED;

    private static final Random RANDOM = new Random();

    public static List<TileColor> getColors(){
        return colors();
    }

    /**
     * This method return only the colors of the tiles and not the value PROHIBITED and EMPTY
     * @return List<TileColor> which includes only the colors
     */
    private static ArrayList<TileColor> colors(){
        ArrayList<TileColor> colors = new ArrayList<TileColor>();
        for (TileColor t : values()){
            if(t != TileColor.EMPTY && t != TileColor.PROHIBITED){
                colors.add(t);
            }
        }
        return colors;
    }

    /**
     *
     * @return a random TileColor
     */
    public static TileColor randomColor(){
        return colors().get(RANDOM.nextInt(colors().size()));
    }



}
