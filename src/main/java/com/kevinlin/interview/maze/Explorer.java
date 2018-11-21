package com.kevinlin.interview.maze;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * Once initialized with a maze, Explorer mains the current position (cell), facing, and history of movements;
 *
 * @author kevin
 */
public class Explorer {

    public static void main(String[] args) {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("SimpleMaze.txt")) {
            List<String> lines = IOUtils.readLines(inputStream, Charset.defaultCharset());
            Maze maze = new Maze(lines);
            Explorer explorer = new Explorer("Thomas");
            explorer.exploreMaze(maze);
            List<Movement> reverseMoves = Lists.newArrayList();
            if (explorer.recursiveSolve(explorer.getPosition(), explorer.getFacing(), Sets.newHashSet(), reverseMoves)) {
                System.out.println(String.format("%s solve the maze!", explorer.getName()));
                // Reset start position and facing as it has already been walked through
                explorer.exploreMaze(maze);
                for (Movement move : Lists.reverse(reverseMoves)) {
                    explorer.move(move);
                }
                explorer.printRoute();
            } else {
                System.out.println(String.format("Doh! %s was unable to find the path ...", explorer.getName()));
            }
            // explorer.move(Movement.MOVE_LEFT);
            // explorer.move(Movement.MOVE_LEFT);
            // explorer.move(Movement.MOVE_RIGHT);
            // explorer.move(Movement.MOVE_LEFT);
            // explorer.move(Movement.MOVE_RIGHT);
            // explorer.move(Movement.MOVE_LEFT);
            // explorer.move(Movement.MOVE_RIGHT);
            // explorer.move(Movement.MOVE_LEFT);
            // explorer.move(Movement.MOVE_RIGHT);
            // explorer.move(Movement.MOVE_FORWARD);
            // explorer.move(Movement.MOVE_LEFT);
            // explorer.move(Movement.MOVE_FORWARD);

        } catch (Exception e) {
            System.out.println("Error reading from file: " + "SimpleMaze.txt");
            e.printStackTrace();
            System.exit(0);
        }
    }

    private final String name;
    private Maze maze;
    private Cell position;
    private Facing facing;
    private List<Movement> movements;
    private Set<Cell> visited;

    public Explorer(String name) {
        this.name = name;
    }

    public void exploreMaze(Maze m) {
        this.maze = m;
        this.position = maze.getStartPoint();
        this.facing = Facing.EAST;
        this.movements = Lists.newArrayList();
        this.visited = Sets.newHashSet(position);
        System.out.println(String.format("%s is entering the maze...\n%s", name, maze));
        System.out.println(String.format("Start point:\n%s", maze.printMaze(position, facing)));
    }

    public void printRoute() {
        System.out.println("Route taken: \n" + maze.printMaze(visited));
        System.out.println(String.format("In total %d moves:", movements.size()));
        for (Movement move : movements) {
            System.out.print(move + " ");
        }
        System.out.println();
    }

    public void move(Movement move) {
        checkNotNull(maze, "Haven't enter a maze yet!");

        // System.out.println("Current position: " + position);
        switch (move) {
            case MOVE_FORWARD:
                position = maze.getAdjacentCell(position, facing);
                visited.add(position);
                break;
            case TURN_LEFT:
                facing = facing.turnLeft();
                break;
            case TURN_RIGHT:
                facing = facing.turnRight();
                break;
            default:
                throw new IllegalArgumentException("Invalid move: " + move);
        }

        movements.add(move);
        // System.out.println(String.format("Move[%d]: %s\n%s", movements.size(), move, maze.printMaze(position, facing)));
        if (position.isWall()) {
            System.out.println("You shall not pass!");
        }

        if (position.isFinish()) {
            System.out.println(String.format("Congratulations! %s, you are a true maze runner!!!", name));
        }
    }

    public void move(Movement[] moves) {
        for (Movement move : moves) {
            move(move);
        }
    }

    public boolean recursiveSolve(Cell curPosition, Facing curFacing, Set<Cell> visitedPos, List<Movement> reverseMoves) {
        checkNotNull(maze, "Haven't enter a maze yet!");

        if (curPosition.isFinish()) {
            // If you reached the exit point
            return true;
        }

        if (curPosition.isWall()) {
            // If you are on a wall or already were here
            return false;
        }

        visitedPos.add(curPosition);

        if (isFrontPassable(curPosition, curFacing)) {
            Cell nextPosition = maze.getAdjacentCell(curPosition, curFacing);
            if (!visitedPos.contains(nextPosition) && recursiveSolve(nextPosition, curFacing, visitedPos, reverseMoves)) {
                move(Movement.MOVE_FORWARD);
                reverseMoves.add(Movement.MOVE_FORWARD);
                return true;
            }
        }
        if (isLeftPassable(curPosition, curFacing)) {
            Facing nextFacing = curFacing.turnLeft();
            Cell nextPosition = maze.getAdjacentCell(curPosition, nextFacing);
            if (!visitedPos.contains(nextPosition) && recursiveSolve(nextPosition, nextFacing, visitedPos, reverseMoves)) {
                move(Movement.MOVE_LEFT);
                reverseMoves.add(Movement.MOVE_FORWARD);
                reverseMoves.add(Movement.TURN_LEFT);
                return true;
            }
        }
        if (isRightPassable(curPosition, curFacing)) {
            Facing nextFacing = curFacing.turnRight();
            Cell nextPosition = maze.getAdjacentCell(curPosition, nextFacing);
            if (!visitedPos.contains(nextPosition) && recursiveSolve(nextPosition, nextFacing, visitedPos, reverseMoves)) {
                move(Movement.MOVE_RIGHT);
                reverseMoves.add(Movement.MOVE_FORWARD);
                reverseMoves.add(Movement.TURN_RIGHT);
                return true;
            }
        }
        if (curPosition.isStart() && isBackPassable(curPosition, curFacing)) {
            Facing nextFacing = curFacing.turnAround();
            Cell nextPosition = maze.getAdjacentCell(curPosition, nextFacing);
            if (!visitedPos.contains(nextPosition) && recursiveSolve(nextPosition, nextFacing, visitedPos, reverseMoves)) {
                move(Movement.TURN_AROUND_AND_FORWARD);
                reverseMoves.add(Movement.MOVE_FORWARD);
                reverseMoves.add(Movement.TURN_LEFT);
                reverseMoves.add(Movement.TURN_LEFT);
                return true;
            }
        }

        // Out of moves
        return false;
    }

    // Helper methods
    public boolean isFrontPassable(Cell curPosition, Facing curFacing) {
        return !checkNotNull(maze, "Haven't enter a maze yet!").getAdjacentCell(curPosition, curFacing).isWall();
    }

    public boolean isLeftPassable(Cell curPoistion, Facing curFacing) {
        return !checkNotNull(maze, "Haven't enter a maze yet!").getAdjacentCell(curPoistion, curFacing.turnLeft()).isWall();
    }

    public boolean isRightPassable(Cell curPosition, Facing curFacing) {
        return !checkNotNull(maze, "Haven't enter a maze yet!").getAdjacentCell(curPosition, curFacing.turnRight()).isWall();
    }

    public boolean isBackPassable(Cell curPosition, Facing curFacing) {
        return !checkNotNull(maze, "Haven't enter a maze yet!").getAdjacentCell(curPosition, curFacing.turnAround()).isWall();
    }

    // Getters & setters
    public String getName() {
        return name;
    }

    public Cell getPosition() {
        return position;
    }

    public void setPosition(Cell position) {
        this.position = position;
    }

    public Facing getFacing() {
        return facing;
    }

    public void setFacing(Facing facing) {
        this.facing = facing;
    }

    public List<Movement> getMovements() {
        return movements;
    }

    public Set<Cell> getVisited() {
        return visited;
    }

}
