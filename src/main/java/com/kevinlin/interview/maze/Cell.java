package com.kevinlin.interview.maze;

public class Cell {

    private final int row, col;
    private final boolean isWall, isStart, isFinish;

    public Cell(int row, int col) {
        // Default cell is space, not start or finish
        this(row, col, false, false, false);
    }

    public Cell(int row, int col, boolean isWall) {
        this(row, col, isWall, false, false);
    }

    public Cell(int row, int col, boolean isWall, boolean isStart, boolean isFinish) {
        this.row = row;
        this.col = col;
        this.isWall = isWall;
        this.isStart = isStart;
        this.isFinish = isFinish;
    }

    @Override
    public String toString() {
        return String.format("[%d, %d]->'%c'", row, col, getChar());
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int[] getCoordinate() {
        return new int[]{row, col};
    }

    public boolean isWall() {
        return isWall;
    }

    public boolean isStart() {
        return isStart;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public char getChar() {
        if (isWall) {
            return Maze.WALL;
        }
        if (isStart) {
            return Maze.START;
        }
        if (isFinish) {
            return Maze.FINISH;
        }
        return Maze.SPACE;
    }

}
