package com.kevinlin.interview.maze;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class MazeTest {

    private Maze maze;

    @Before
    public void setUp() throws Exception {
        //@formatter:off
        List<String> threeXthree = Arrays.asList(
                "  F",
                " XX",
                "  S");
        //@formatter:on
        maze = new Maze(threeXthree);
    }

    @Test
    public void getNumberOfWalls() throws Exception {
        assertThat(maze.getNumberOfWalls(), equalTo(2));
    }

    @Test
    public void getNumberOfSpaces() throws Exception {
        assertThat(maze.getNumberOfSpaces(), equalTo(5));
    }

    @Test
    public void getCell_OutOfBounds() throws Exception {
        assertThat(maze.getCell(3, 0), equalTo(Maze.INVISIBLE_WALL));
        assertThat(maze.getCell(2, 3), equalTo(Maze.INVISIBLE_WALL));
    }

    @Test
    public void getCell_center() throws Exception {
        int[] coordinate = new int[]{1, 1};
        Cell cell = maze.getCell(coordinate);
        assertThat(cell.getCoordinate(), equalTo(coordinate));
        assertThat(cell.getChar(), equalTo('X'));
    }

    @Test
    public void getCell_topLeft() throws Exception {
        int[] coordinate = new int[]{0, 0};
        Cell cell = maze.getCell(coordinate);
        assertThat(cell.getCoordinate(), equalTo(coordinate));
        assertThat(cell.getChar(), equalTo(' '));
    }

    @Test
    public void getCell_topRight() throws Exception {
        int[] coordinate = new int[]{0, 2};
        Cell cell = maze.getCell(coordinate);
        assertThat(cell.getCoordinate(), equalTo(coordinate));
        assertThat(cell.getChar(), equalTo('F'));
    }

    @Test
    public void getCell_bottomLeft() throws Exception {
        int[] coordinate = new int[]{2, 0};
        Cell cell = maze.getCell(coordinate);
        assertThat(cell.getCoordinate(), equalTo(coordinate));
        assertThat(cell.getChar(), equalTo(' '));
    }

    @Test
    public void getCell_bottomRight() throws Exception {
        int[] coordinate = new int[]{2, 2};
        Cell cell = maze.getCell(coordinate);
        assertThat(cell.getCoordinate(), equalTo(coordinate));
        assertThat(cell.getChar(), equalTo('S'));
    }

    @Test
    public void getAdjacentCell() throws Exception {
        Cell bottomRight = maze.getCell(2, 2);
        assertThat(maze.getAdjacentCell(bottomRight, Facing.EAST), equalTo(Maze.INVISIBLE_WALL));
        assertThat(maze.getAdjacentCell(bottomRight, Facing.SOUTH), equalTo(Maze.INVISIBLE_WALL));
        assertThat(maze.getAdjacentCell(bottomRight, Facing.WEST), equalTo(maze.getCell(2, 1)));
        assertThat(maze.getAdjacentCell(bottomRight, Facing.NORTH), equalTo(maze.getCell(1, 2)));
    }

}
