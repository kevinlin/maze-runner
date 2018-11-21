package com.kevinlin.interview.maze;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.kevinlin.interview.maze.Facing.EAST;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Maze maintains the whole matrix of cells, start/finish and the adjacent cells for any given cell
 *
 * @author kevin
 */
public class Maze {
    public static final char WALL = 'X';
    public static final char SPACE = ' ';
    public static final char START = 'S';
    public static final char FINISH = 'F';
    public static final Cell INVISIBLE_WALL = new Cell(-1, -1, true);

    private final int numRows, numCols;
    private final Cell[][] matrix;
    private final Set<Cell> cells = new HashSet<>();
    private final Cell startPoint, finishPoint;

    public static void main(String[] args) throws Exception {
        if (args.length != 1 || StringUtils.isBlank(args[0])) {
            System.out.println("You mush provide a valid input filename available in classpath");
        }
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream(args[0])) {
            List<String> lines = IOUtils.readLines(inputStream, Charset.defaultCharset());
            Maze maze = new Maze(lines);
            System.out.println(maze);
        } catch (Exception e) {
            System.out.println("Error reading from file: " + args[0]);
            e.printStackTrace();
            System.exit(0);
        }
    }

    public Maze(List<String> rows) {
        if (rows == null) {
            throw new MazeCreationException("Missing input");
        }
        if (rows.size() <= 2) {
            throw new MazeCreationException("Too few rows: %d", rows.size());
        }

        numRows = rows.size();

        String firstRow = rows.get(0);
        if (firstRow == null || firstRow.length() <= 2) {
            throw new MazeCreationException("Invalid row[%d]: '%s'", 0, firstRow);
        }
        numCols = firstRow.length();

        matrix = new Cell[numRows][numCols];

        for (int y = 0; y < numRows; y++) {
            String row = rows.get(y);
            if (row.length() != numCols) {
                throw new MazeCreationException("Incorrect number of columns at row[%d]: '%s'", y, row);
            }
            for (int x = 0; x < numCols; x++) {
                char cellChar = row.charAt(x);
                Cell cell;
                if (cellChar == SPACE) {
                    cell = new Cell(y, x);
                } else if (cellChar == WALL) {
                    cell = new Cell(y, x, true);
                } else if (cellChar == START) {
                    cell = new Cell(y, x, false, true, false);
                } else if (cellChar == FINISH) {
                    cell = new Cell(y, x, false, false, true);
                } else {
                    throw new MazeCreationException("Invalie cell character[%d, %d]: '%c'", y, x, cellChar);
                }
                matrix[y][x] = cell;
                cells.add(cell);
            }
        }

        // Validate there is one and only one Start and Finish cell
        List<Cell> startCells = cells.stream().filter(Cell::isStart).collect(Collectors.toList());
        if (startCells.size() != 1) {
            throw new MazeCreationException("Expected one and only one Start cell, got: %d - %s", startCells.size(), startCells);
        }
        startPoint = startCells.get(0);
        List<Cell> finishCells = cells.stream().filter(Cell::isFinish).collect(Collectors.toList());
        if (finishCells.size() != 1) {
            throw new MazeCreationException("Expected one and only one Finish cell, got: %d - %s", finishCells.size(), finishCells);
        }
        finishPoint = finishCells.get(0);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(String.format("<<<<< %d X %d Maze >>>>>\n", numRows, numCols));
        sb.append(printMaze(INVISIBLE_WALL, EAST));
        sb.append("Number of Wall cells: ").append(getNumberOfWalls()).append("\n");
        sb.append("Number of Space cells: ").append(getNumberOfSpaces()).append("\n");
        return sb.toString();
    }

    public String printMaze(Cell position, Facing facing) {
        checkNotNull(position, "Postion cannobe be null");
        checkNotNull(facing, "Facing cannot be null");

        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < numCols + 2; x++) {
            sb.append('#');
        }
        sb.append("\n");
        for (int y = 0; y < numRows; y++) {
            sb.append('#');
            for (int x = 0; x < numCols; x++) {
                if (position.getRow() == y && position.getCol() == x) {
                    sb.append(facing);
                } else {
                    sb.append(matrix[y][x].getChar());
                }
            }
            sb.append("#\n");
        }
        for (int x = 0; x < numCols + 2; x++) {
            sb.append('#');
        }
        sb.append("\n");

        return sb.toString();
    }

    public String printMaze(Set<Cell> visited) {
        checkNotNull(visited, "Visited cells cannobe be null");

        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < numCols + 2; x++) {
            sb.append('#');
        }
        sb.append("\n");
        for (int y = 0; y < numRows; y++) {
            sb.append('#');
            for (int x = 0; x < numCols; x++) {
                Cell cell = matrix[y][x];
                if (visited.contains(cell)) {
                    sb.append("*");
                } else {
                    sb.append(cell.getChar());
                }
            }
            sb.append("#\n");
        }
        for (int x = 0; x < numCols + 2; x++) {
            sb.append('#');
        }
        sb.append("\n");

        return sb.toString();
    }

    public int getNumberOfWalls() {
        return Long.valueOf(cells.stream().filter(Cell::isWall).count()).intValue();
    }

    public int getNumberOfSpaces() {
        return Long.valueOf(cells.stream().filter(c -> !c.isWall() && !c.isStart() && !c.isFinish()).count()).intValue();
    }

    public Cell getStartPoint() {
        return startPoint;
    }

    public Cell getFinishPoint() {
        return finishPoint;
    }

    /**
     * Convenient method for getCell()
     *
     * @param coordinate - [x, y]
     * @return {@link Cell} if exists, or invisible wall
     */
    public Cell getCell(int[] coordinate) {
        if (coordinate == null || coordinate.length != 2) {
            throw new IllegalArgumentException("Invaid coordinate: " + Arrays.toString(coordinate));
        }

        return matrix[coordinate[0]][coordinate[1]];
    }

    public Cell getCell(int row, int col) {
        if (row < 0 || row >= numRows || col < 0 || col >= numCols) {
            // Return invisible wall for out-of-bound coordinates
            return INVISIBLE_WALL;
        }

        return matrix[row][col];
    }

    public Cell getAdjacentCell(Cell cell, Facing facing) {
        if (cell == null || facing == null) {
            throw new IllegalArgumentException("Cell and facing cannot be null!");
        }

        int row = cell.getRow(), col = cell.getCol();
        switch (facing) {
            case EAST:
                col++;
                break;
            case SOUTH:
                row++;
                break;
            case WEST:
                col--;
                break;
            case NORTH:
                row--;
                break;
            default:
                throw new IllegalArgumentException("Unknonw facing: " + facing);
        }

        return getCell(row, col);
    }

}
