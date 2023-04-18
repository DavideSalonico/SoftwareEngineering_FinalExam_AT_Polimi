package GC_11;

import GC_11.model.Triplet;
import GC_11.model.TileColor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TripletTest {

    @DisplayName("Basic attribute check")
    @Test
    void testTriplet() {
        Triplet triplet = new Triplet(1, 2, TileColor.WHITE);
        assertEquals(1, triplet.getRow());
        assertEquals(2, triplet.getCol());
        assertEquals(TileColor.WHITE, triplet.getColor());
    }

    @DisplayName("Compare Triplets")
    @Test
    void testEquality() {
        Triplet triplet1 = new Triplet(1, 2, TileColor.WHITE);
        Triplet triplet2 = new Triplet(1, 2, TileColor.WHITE);
        Triplet triplet3 = new Triplet(3, 4, TileColor.BLUE);

        assertEquals(triplet1.getColor(), triplet2.getColor());
        assertNotEquals(triplet1.getRow(), triplet3.getRow());
    }
}
