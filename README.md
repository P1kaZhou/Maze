# Maze project

## Packages

There are 3 main packages, each with their assigned goal/tasks to perform

- [dijkstra](https://gitlab.telecom-paris.fr/2021INF103/groupe2/zhou-daniel/-/tree/master/src/dijkstra)
- [maze](https://gitlab.telecom-paris.fr/2021INF103/groupe2/zhou-daniel/-/tree/master/src/maze)
- [ui](https://gitlab.telecom-paris.fr/2021INF103/groupe2/zhou-daniel/-/tree/master/src/ui)

### The dijkstra package

#### Main information
The main goal of the dijkstra package is to implement the algorithm of the same name, discovered by Edsger W. Dijkstra,
which gives, for a given departure vertex V, at the end of all iterations, all shortest paths (and their respective length) 
to all reachable vertices.
Here, the graph is a maze, and two cases are adjacent if they share a common "side".
The algorithm will "solve" the maze and return a Hashtable with, for every case, the next case to follow to arrive at 
the departure case. A case is an empty box, or the goal arrival box.
The algorithm can be found [here](https://gitlab.telecom-paris.fr/2021INF103/groupe2/zhou-daniel/-/blob/master/src/dijkstra/dijkstra.java).

#### *What does every class/interface of the package dijkstra do?*

- The [ASetInterface](https://gitlab.telecom-paris.fr/2021INF103/groupe2/zhou-daniel/-/blob/master/src/dijkstra/ASetInterface.java) is implemented by the [ASet](https://gitlab.telecom-paris.fr/2021INF103/groupe2/zhou-daniel/-/blob/master/src/dijkstra/ASet.java) 
class, which instantiates a [HashTable](https://docs.oracle.com/javase/7/docs/api/java/util/HashSet.html) that will contain every vertex whose shortest path has been found.
- The [GraphInterface](https://gitlab.telecom-paris.fr/2021INF103/groupe2/zhou-daniel/-/blob/master/src/dijkstra/GraphInterface.java) is implemented by the [Maze](https://gitlab.telecom-paris.fr/2021INF103/groupe2/zhou-daniel/-/blob/master/src/maze/Maze.java) class is the graph on which dijkstra's algorithm will be working on. We can get all vertex and the distances of all neighbours for a given vertex.
- The [PiInterface](https://gitlab.telecom-paris.fr/2021INF103/groupe2/zhou-daniel/-/blob/master/src/dijkstra/PiInterface.java) is implemented by the [Pi](https://gitlab.telecom-paris.fr/2021INF103/groupe2/zhou-daniel/-/blob/master/src/dijkstra/Pi.java) class, 
which instantiates an [HashSet](https://docs.oracle.com/javase/8/docs/api/java/util/Hashtable.html), which associates every vertex from the graph to an [Integer](https://docs.oracle.com/javase/7/docs/api/java/lang/Integer.html) which is (after the end of the maze) the shortest path length starting from the departure vertex.
- The [PreviousInterface](https://gitlab.telecom-paris.fr/2021INF103/groupe2/zhou-daniel/-/blob/master/src/dijkstra/PreviousInterface.java) is implemented by the [Previous](https://gitlab.telecom-paris.fr/2021INF103/groupe2/zhou-daniel/-/blob/master/src/dijkstra/Previous.java) class,
also instantiates an HashSet  which associates every vertex so its previous one, i.e. the one to follow to go to reach the departure with the shortest path length (there can be several paths).
- The [VertexInterface](https://gitlab.telecom-paris.fr/2021INF103/groupe2/zhou-daniel/-/blob/master/src/dijkstra/VertexInterface.java) implemented by the [abstract class MBox](https://gitlab.telecom-paris.fr/2021INF103/groupe2/zhou-daniel/-/blob/master/src/maze/MBox.java)
represents every vertex of the graph, with their "characteristics" to differentiate one to each other.

### The maze package

Each maze case is either an [arrival case](https://gitlab.telecom-paris.fr/2021INF103/groupe2/zhou-daniel/-/blob/master/src/maze/ABox.java), [empty case](https://gitlab.telecom-paris.fr/2021INF103/groupe2/zhou-daniel/-/blob/master/src/maze/EBox.java), [departure case](https://gitlab.telecom-paris.fr/2021INF103/groupe2/zhou-daniel/-/blob/master/src/maze/DBox.java), or [wall](https://gitlab.telecom-paris.fr/2021INF103/groupe2/zhou-daniel/-/blob/master/src/maze/WBox.java). I decided that the maze will be an array of arrays of [MBoxes](https://gitlab.telecom-paris.fr/2021INF103/groupe2/zhou-daniel/-/blob/master/src/maze/MBox.java), with a given label corresponding to their type, but also two coordinates. 
The [Maze class](https://gitlab.telecom-paris.fr/2021INF103/groupe2/zhou-daniel/-/blob/master/src/maze/Maze.java) has many methods. Most of them are self-explanatory and all of them have javadoc.
Finally, methods that are dealing with the Maze class can throw exceptions, there are 3 in the package.

#### *Still, here are more explanations*

- The private variables `mazeHeight` and `mazeWidth` are respectively for the number height and the length of the maze.
- A [MazeIssueException](https://gitlab.telecom-paris.fr/2021INF103/groupe2/zhou-daniel/-/blob/master/src/maze/MazeIssueException.java) will be thrown by the getters and setters of the departure case
when the maze contains an issue, like two or zero arrival/departure cases.
- Since Dijkstra's algorithm goes through all vertices, the `getAllVertices()` method returns an ArrayList of VertexInterfaces that has every vertex of the graph, which is in this case a maze, within it (it returns every non-wall vertex).
- For the "looking for successors" part, the `getSuccessors(VertexInterface vertex)` part returns an ArrayList of VertexInterfaces containing every successor for a given vertex (taking into account the borders of the maze of course).
- ``getDistance(VertexInterface src, VertexInterface dst)`` returns 1 by convention. This method is only called for successors, and we're considering that the distance between two successors is 1.
- ``getLabelFromCoords(int x, int y)`` returns the label of the box at the coordinates (x,y), x being the height (starting from the top and at 0).
- ``getVertex(int x, int y)`` does the same, but returns the box.
- ``setParametersFromTextFile(String fileName)`` will set `mazeHeight` and `mazeWidth` in accordance with the file number of lines and first line length.
- ``initFromTextFile(String fileName)`` will change the maze based on fileName. Throws a [MazeReadingException](https://gitlab.telecom-paris.fr/2021INF103/groupe2/zhou-daniel/-/blob/master/src/maze/MazeReadingException.java) if the maze has lines from different length.
- ``readFromMazeBoxArray(PanelBoxes boxArray)`` will change the maze with what is displayed currently at the screen (with the intermediate of a controller).
- ``saveToTextFile(String fileName)`` saves the maze into a new text file with the name fileName, assuming it's a legal one (will be asserted beforehand).

### The ui package

I created a class for every main instance:\
The [main window](https://gitlab.telecom-paris.fr/2021INF103/groupe2/zhou-daniel/-/blob/master/src/ui/MazeWindow.java) 
contains a [principal panel](https://gitlab.telecom-paris.fr/2021INF103/groupe2/zhou-daniel/-/blob/master/src/ui/MainPanel.java)
, within which there's a [maze grid](https://gitlab.telecom-paris.fr/2021INF103/groupe2/zhou-daniel/-/blob/master/src/ui/MazePanel.java) 
which characteristics may vary depending on the size of the loaded maze, assuming it's a valid one (If not, an exception will be thrown).
Regarding exceptions, a message box will open when an exception occurs.
Each case of the maze is a [box with characteristics](https://gitlab.telecom-paris.fr/2021INF103/groupe2/zhou-daniel/-/blob/master/src/ui/MazeBox.java) 
which can be changed depending on what the user does.
The main window also contains [a menu bar](https://gitlab.telecom-paris.fr/2021INF103/groupe2/zhou-daniel/-/blob/master/src/ui/MazeMenuBar.java) 
on which there are several [JMenuItems](https://docs.oracle.com/javase/7/docs/api/javax/swing/JMenuItem.html) 
which after being clicked on, will do various things.
All [PanelBoxes](https://gitlab.telecom-paris.fr/2021INF103/groupe2/zhou-daniel/-/blob/master/src/ui/PanelBoxes.java) 
instances are an array of arrays of MazeBox which is tracking the user changes so the model won't change anything.
Which means it's somehow the instance that links the maze model and what the user sees.

#### *But what does every class do in detail?*

##### [MazeWindow](https://gitlab.telecom-paris.fr/2021INF103/groupe2/zhou-daniel/-/blob/master/src/ui/MazeWindow.java)
The view and master of (almost) everything.
- ``setupMenuBar()`` will setup every element of the menu bar. Every element of the bar will have an [ActionListener](https://docs.oracle.com/javase/7/docs/api/java/awt/event/ActionListener.html) and do something if being clicked on.
- ``updateMaze()`` updates the maze whenever the user does something (it only updates the view and the controller instance).
- ``setupMaze(int height, int width)`` displays a maze with the characteristics' height x width.
- ``searchForCaseFromLabel(String label)`` looks for a case with the corresponding label. Will return null if the maze is empty (we're only looking for arrival and departure).
- ``stateChanged(ChangeEvent e)`` does something based on e (check Javadoc).
- ``loadMaze()`` opens a window to the `data` directory and asks the user to load a maze. If the maze in input is illegal, an exception will be thrown and the maze won't load.
- ``saveMaze()`` opens a window to the ``data`` directory and let the user input a file name to save (preferably without the ``.txt`` at the end, as it adds it automatically). Will not save if the maze does not have one arrival AND departure case.
- ``solveMaze()`` solves the maze displayed at the screen. Throws a [MazeSolvingException](https://gitlab.telecom-paris.fr/2021INF103/groupe2/zhou-daniel/-/blob/master/src/maze/MazeSolvingException.java) if the maze is unsolvable (i.e. no valid path from beginning to arrival).
- ``askDimensions()`` opens a [request window](https://docs.oracle.com/javase/7/docs/api/javax/swing/JOptionPane.html) up on which the user inputs its desired maze length and width (be reasonable...)
  Will open an error window up if the user tries to input either non-parsable strings or negative integers.

##### [MazeMenubar](https://gitlab.telecom-paris.fr/2021INF103/groupe2/zhou-daniel/-/blob/master/src/ui/MazeMenuBar.java)
When instantiated by the constructor, it adds everything that needs to be added to the bar and then adds the bar to the window.

*The ``File`` tab contains two items*:
- ``Load file`` : if clicked, does ``loadMaze()``
- ``Save file`` : if clicked, does ``saveMaze()``

<a></a>

*The ``Edit`` tab contains four items*:

- ``Set arrival`` will change the next empty case into the new arrival case, and the former arrival case into a vanilla empty one.
- ``Set departure`` does the same but for the departure case.
- ``Reset maze`` clears the current grid and keeps the same dimensions.
- ``Setup a custom maze`` : if clicked, does ``askDimensions()``

<a></a>

*The ``Solve`` tab contains two items*:

- ``Solve maze`` solves the maze manually if the user wants to.
- ``Auto-computing?`` does the same, but automatically everytime the maze is updated.


*Finally, there's a help button that just tells the user to refer to this readme doc (and the javadoc for more technical information).*\
*Also, there's a non-editable text field letting the user know what's the shortest path length, as well as letting them know if they decided to change the departure case, for instance.*


##### [MainPanel](https://gitlab.telecom-paris.fr/2021INF103/groupe2/zhou-daniel/-/blob/master/src/ui/MainPanel.java)
Contains the maze grid.

##### [MazePanel](https://gitlab.telecom-paris.fr/2021INF103/groupe2/zhou-daniel/-/blob/master/src/ui/MazePanel.java)
A grid which dimensions will change based on the user request.

##### [PanelBoxes](https://gitlab.telecom-paris.fr/2021INF103/groupe2/zhou-daniel/-/blob/master/src/ui/PanelBoxes.java)
Its instance tracks dynamically everything the user do to update whenever needed. Only interacts with `Maze` when needed and only when the controller says so.

##### [MazeBox](https://gitlab.telecom-paris.fr/2021INF103/groupe2/zhou-daniel/-/blob/master/src/ui/MazeBox.java)
The class that represents every case, and the whole maze in addition with ``MazePanel``. Behaves differently based on what the user decided to do beforehand:
- The static methods ``returnColorFromLabel(String label)`` and ``returnLabelFromColor(Color color)`` are basically respectively converters from label to color, and color from label.
- ``mousePressed(MouseEvent e)`` changes the grid based on what the user did beforehand (cf Javadoc).

## Testing

Every test is done with the [Main](https://gitlab.telecom-paris.fr/2021INF103/groupe2/zhou-daniel/-/blob/master/src/WindowTest.java) class.
On launch, it displays a 10x10, but if the user decides to load a maze which isn't a 10x10, if it's still valid, 
the grid will adapt by itself.\
The departure case is red, arrival is green, empty is white, wall is black, path is yellow.

### Files for testing
*Here are some files with something that differs from a regular maze*
- ``doubledeparturecasemaze.txt`` is a file with two departure cases.
- ``emptymaze.txt`` is just an empty maze.
- ``illegalshapemaze.txt`` is a maze where all lines don't have the same length.
- ``labymogus11x10.txt`` is a non square-shaped maze (11 cases height and 10 cases width).
- ``StressMaze.txt`` is a "maze" with illegal characters.

## *Need more information?*

Please message me on ``daniel.zhou@telecom-paris.fr``
