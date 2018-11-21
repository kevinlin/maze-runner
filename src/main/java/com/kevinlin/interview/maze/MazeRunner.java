package com.kevinlin.interview.maze;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class MazeRunner {

    public static void main(String[] args) {
        @SuppressWarnings("resource") Scanner sysIn = new Scanner(System.in);

        System.out.println("Welcome, maze runner!");

        String input = null;
        while (StringUtils.isBlank(input)) {
            System.out.println("Enter your name please:");
            input = sysIn.nextLine();
        }

        Explorer mazeRunner = new Explorer(input);

        while (true) {
            input = null;
            while (StringUtils.isBlank(input)) {
                System.out.println("Enter the filename of the maze please (must exist in classpath):");
                input = sysIn.nextLine();
            }
            if ("Quit".equals(input)) {
                System.out.println("Goodbye! " + mazeRunner.getName());
                System.exit(0);
            }
            try (InputStream inputStream = ClassLoader.getSystemResourceAsStream(input)) {
                List<String> lines = IOUtils.readLines(inputStream, Charset.defaultCharset());
                Maze maze = new Maze(lines);
                mazeRunner.exploreMaze(maze);
                List<Movement> reverseMoves = Lists.newArrayList();
                if (mazeRunner.recursiveSolve(mazeRunner.getPosition(), mazeRunner.getFacing(), Sets.newHashSet(), reverseMoves)) {
                    System.out.println(String.format("%s solve the maze!", mazeRunner.getName()));
                    // Reset start position and facing as it has already been walked through
                    mazeRunner.exploreMaze(maze);
                    for (Movement move : Lists.reverse(reverseMoves)) {
                        mazeRunner.move(move);
                    }
                    mazeRunner.printRoute();
                } else {
                    System.out.println(String.format("Doh! %s was unable to find the path ...", mazeRunner.getName()));
                }
            } catch (Exception e) {
                System.out.println("Error reading from file: " + input);
                e.printStackTrace();
                System.out.println("Please try again.");
            }
        }
    }

}
