package GC_11.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * TileColor represents the status that a cell of the Board can assume, enum is serializable
 */
public enum TileColor implements Serializable {
    WHITE,
    BLUE,
    PURPLE,
    YELLOW,
    CYAN,
    GREEN,
    EMPTY,
    PROHIBITED;

    private static final Random RANDOM = new Random();

    public static List<TileColor> getColors() {
        return colors();
    }

    /**
     * This method return only the colors of the tiles and not the value PROHIBITED and EMPTY
     *
     * @return List<TileColor> which includes only the colors
     */
    private static ArrayList<TileColor> colors() {
        ArrayList<TileColor> colors = new ArrayList<TileColor>();
        for (TileColor t : values()) {
            if (t != TileColor.EMPTY && t != TileColor.PROHIBITED) {
                colors.add(t);
            }
        }
        return colors;
    }

    /**
     * @return a random TileColor
     */
    public static TileColor randomColor() {
        return colors().get(RANDOM.nextInt(colors().size()));
    }

    /**
     * This method parses a String into a TileColor type
     *
     * @param s is a string
     * @return the corresponding type TileColor
     */
    public static TileColor StringToColor(String s) {
        switch (s) {
            case "WHITE":
                return WHITE;
            case "PURPLE":
                return PURPLE;
            case "GREEN":
                return GREEN;
            case "BLUE":
                return BLUE;
            case "CYAN":
                return CYAN;
            case "YELLOW":
                return YELLOW;
            default:
                return null;
        }
    }

    public static String ColorToString(TileColor tc) {
        switch (tc) {
            case WHITE:
                return "\u001B[107m  \u001B[0m";
            case PURPLE:
                return "\u001B[105m  \u001B[0m";
            case GREEN:
                return "\u001B[102m  \u001B[0m";
            case BLUE:
                return "\u001B[44m  \u001B[0m";
            case CYAN:
                return "\u001B[106m  \u001B[0m";
            case YELLOW:
                return "\u001B[103m  \u001B[0m";
            case EMPTY:
                return "□";
            case PROHIBITED:
                return "▧";
            default:
                return "ERROR";
        }
    }

}
