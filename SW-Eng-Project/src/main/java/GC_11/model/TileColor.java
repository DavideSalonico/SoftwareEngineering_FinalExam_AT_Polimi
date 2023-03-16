package GC_11;

import java.util.Arrays;
import java.util.Collections;
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

    public static TileColor randomColor(){
        return values()[RANDOM.nextInt(values().length)];
    }

}
