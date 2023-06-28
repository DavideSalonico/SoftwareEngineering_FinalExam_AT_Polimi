package GC_11.util;

import java.io.Serializable;

/**
 * ControlMatrix is a class that represents a control matrix with boolean values.
 * It provides methods to get and set values in the matrix and reset the matrix.
 */
public class ControlMatrix implements Serializable {

    private boolean[][] controlMatrix = new boolean[6][5];

    /**
     * Returns the value at the specified position in the control matrix.
     *
     * @param l the row index
     * @param c the column index
     * @return the value at the specified position
     */
    public boolean get(int l, int c) {

        return controlMatrix[l][c];

    }

    /**
     * Sets the value at the specified position in the control matrix to true.
     *
     * @param l the row index
     * @param c the column index
     */
    public void setTrue(int l, int c) {

        controlMatrix[l][c] = true;

    }

    /**
     * Resets the control matrix by creating a new matrix with all values set to false.
     */
    public void reset() {

        controlMatrix = new boolean[6][5];
    }

}
