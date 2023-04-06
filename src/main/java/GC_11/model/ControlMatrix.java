package GC_11.model;

public class ControlMatrix {

    private boolean[][] controlMatrix = new boolean[6][5];

    public boolean get(int l, int c) {

        return controlMatrix[l][c];

    }

    public void setTrue(int l, int c) {

        controlMatrix[l][c] = true;

    }

    public void reset() {

        controlMatrix = new boolean[6][5];

    }

}
