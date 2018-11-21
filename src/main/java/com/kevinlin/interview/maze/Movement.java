package com.kevinlin.interview.maze;

public enum Movement {

    // Basic moves
    MOVE_FORWARD("\u21E7"), TURN_LEFT("\u21B0"), TURN_RIGHT("\u21B1");

    // Compound moves
    public static final Movement[] MOVE_LEFT = {TURN_LEFT, MOVE_FORWARD};
    public static final Movement[] MOVE_RIGHT = {TURN_RIGHT, MOVE_FORWARD};
    public static final Movement[] TURN_AROUND_AND_FORWARD = {TURN_LEFT, TURN_LEFT, MOVE_FORWARD};

    private String symbol;

    Movement(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }

}
