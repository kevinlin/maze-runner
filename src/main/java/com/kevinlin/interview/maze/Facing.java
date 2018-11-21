package com.kevinlin.interview.maze;

public enum Facing {

    EAST(0, "\u2192"), SOUTH(1, "\u2193"), WEST(2, "\u2190"), NORTH(3, "\u2191");

    private int index;
    private String symbol;

    Facing(int index, String symbol) {
        this.index = index;
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }

    private Facing indexOf(int idx) {
        switch (idx) {
            case 0:
                return EAST;
            case 1:
                return SOUTH;
            case 2:
                return WEST;
            case 3:
                return NORTH;
            default:
                throw new IllegalArgumentException("Invalid index: " + idx);
        }
    }

    public Facing turnLeft() {
        return indexOf((this.index + 3) % 4);
    }

    public Facing turnRight() {
        return indexOf((this.index + 1) % 4);
    }

    public Facing turnAround() {
        return indexOf((this.index + 2) % 4);
    }

}
