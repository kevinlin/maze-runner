package com.kevinlin.interview.maze;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CellTest {

    private Cell cell;

    @Test
    public void constructor() throws Exception {
        cell = new Cell(7, 9, true, true, true);
        assertThat(cell.getRow(), equalTo(7));
        assertThat(cell.getCol(), equalTo(9));
        assertTrue(cell.isWall());
        assertTrue(cell.isStart());
        assertTrue(cell.isFinish());
    }

    @Test
    public void getChar() throws Exception {
        cell = new Cell(7, 9, false, false, false);
        assertThat(cell.getChar(), equalTo(' '));
        cell = new Cell(7, 9, true, false, false);
        assertThat(cell.getChar(), equalTo('X'));
        cell = new Cell(7, 9, false, true, false);
        assertThat(cell.getChar(), equalTo('S'));
        cell = new Cell(7, 9, false, false, true);
        assertThat(cell.getChar(), equalTo('F'));

    }

}
