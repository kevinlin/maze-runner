package com.kevinlin.interview.maze;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class FacingTest {

    @Test
    public final void testTurnLeft() throws Exception {
        assertThat(Facing.EAST.turnLeft(), equalTo(Facing.NORTH));
        assertThat(Facing.SOUTH.turnLeft(), equalTo(Facing.EAST));
        assertThat(Facing.WEST.turnLeft(), equalTo(Facing.SOUTH));
        assertThat(Facing.NORTH.turnLeft(), equalTo(Facing.WEST));
    }

    @Test
    public final void testTurnRight() throws Exception {
        assertThat(Facing.EAST.turnRight(), equalTo(Facing.SOUTH));
        assertThat(Facing.SOUTH.turnRight(), equalTo(Facing.WEST));
        assertThat(Facing.WEST.turnRight(), equalTo(Facing.NORTH));
        assertThat(Facing.NORTH.turnRight(), equalTo(Facing.EAST));
    }

    @Test
    public void testTurnAround() throws Exception {
        assertThat(Facing.EAST.turnAround(), equalTo(Facing.WEST));
        assertThat(Facing.SOUTH.turnAround(), equalTo(Facing.NORTH));
        assertThat(Facing.WEST.turnAround(), equalTo(Facing.EAST));
        assertThat(Facing.NORTH.turnAround(), equalTo(Facing.SOUTH));
    }

}
