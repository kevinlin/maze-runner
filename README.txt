Maze Test
=========

This coding example will form the basis of your interview with Excelian (should you progress), you should be ready to explain any and all of the choices you have made when writing the solution.
There is no stated time limit but we would not envisage it would take longer than a couple of hours.

The solution must be representative of what you would produce 'on the job', by that we mean it must be clear, maintainable, demonstrably bug-free and tested.

The zipped project provided uses either:
* Apache Maven (Java)
* Solution project for Microsoft Visual Studio Community Edition (C#)

You should feel free to use any other tools you are more comfortable with.  

There are 2 classes in it and you should feel free to change these in any way you see fit, including deleting them and starting again.

The test is based on exploring any arbitrary maze (one is provided).

User Story 1
------------

As a world famous explorer of Mazes I would like a maze to exist so that I can explore it

Acceptance Criteria:

* A Maze (as illustrated in ExampleMaze.txt) consists of walls 'X', Empty spaces ' ', one and only one Start point 'S' and one and only one exit 'F'.

* After a maze has been created the number of walls and empty spaces should be available to me.

* After a maze has been created I should be able to put in a coordinate and know what exists at that point.


User Story 2
------------

As a world famous explorer of Mazes I would like to exist in a maze and be able to navigate it so that I can explore it

Acceptance Criteria:

* Given a maze the explorer should be able to drop in to the Start point.

* An explorer in a maze must be able to:
    Move forward;
    Turn left and right;
    Understand what is in front of them;
    Understand all movement options from their given location;
    Have a record of where they have been.


UserStory 3
-----------	
* An explorer must be able to automatically explore a maze and find the exit, and on exit they must be able to state the route they took in an understandable fashion.
