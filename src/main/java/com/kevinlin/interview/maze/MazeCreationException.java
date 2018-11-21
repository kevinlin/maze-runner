package com.kevinlin.interview.maze;

public class MazeCreationException extends RuntimeException {

    public MazeCreationException(String message, Object... args) {
        super(String.format(message, args));
    }

    public MazeCreationException(String message) {
        super(message);
    }

}
