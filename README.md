# Maze Test

Requirements stated in `README.txt`

# Requirement
* Java 1.8
* Maven 4.0

# How to build?
```
mvn clean compile assembly:single
```

# How to run?
### Generate new maze for testing
```
java -classpath target/maze-test-1.0-SNAPSHOT-jar-with-dependencies.jar MazeGenerator
```

### User Story 1
```
java -classpath target/maze-test-1.0-SNAPSHOT-jar-with-dependencies.jar Maze ExampleMaze.txt
```

### User Story 2
```
java -classpath target/maze-test-1.0-SNAPSHOT-jar-with-dependencies.jar Explorer
```

### User Story 3
```
java -classpath target/maze-test-1.0-SNAPSHOT-jar-with-dependencies.jar MazeRunner
```

# Implementation Explanation
The logic of the maze solving comes from: [Maze solving algorithm](https://en.wikipedia.org/wiki/Maze_solving_algorithm). Recursive algorithm was chosen simply because it appear to be the simpliest.
In real-world, there should be multiple implementations of maze solving strategies for explorer to choose from.
