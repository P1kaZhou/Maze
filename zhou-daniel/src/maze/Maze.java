package maze;

import dijkstra.GraphInterface;
import dijkstra.VertexInterface;
import ui.PanelBoxes;

import java.io.*;
import java.util.ArrayList;
import java.lang.String;

//The model
public class Maze implements GraphInterface {

    /**
     * The height of the maze
     */
    private int mazeHeight;
    /**
     * The width of the maze
     */
    private int mazeWidth;

    /**
     * The array of arrays of VertexInterfaces which is the maze, first coordinate is x and second is y.
     */
    final VertexInterface[][] maze;

    /**
     * Set the maze with mazeHeight lines, each length has mazeWidth cases
     *
     * @param mazeHeight the number of lines of the maze
     * @param mazeWidth  the length of each line, supposed to be the same
     */
    public Maze(int mazeHeight, int mazeWidth) {
        this.maze = new VertexInterface[mazeHeight][mazeWidth];
        this.mazeHeight = mazeHeight;
        this.mazeWidth = mazeWidth;
    }

    /**
     * Returns the height of the maze
     *
     * @return the number of lines of a maze
     */
    public int getMazeHeight() {
        return mazeHeight;
    }

    /**
     * Return the length of a line of the maze
     *
     * @return the width of the maze
     */
    public int getMazeWidth() {
        return mazeWidth;
    }

    /**
     * Sets the current height of the maze to mazeHeight
     *
     * @param mazeHeight the new maze height
     */
    public void setMazeHeight(int mazeHeight) {
        this.mazeHeight = mazeHeight;
    }

    /**
     * Sets the current width of the maze to mazeWidth
     *
     * @param mazeWidth the new maze width
     */

    public void setMazeWidth(int mazeWidth) {
        this.mazeWidth = mazeWidth;
    }

    /**
     * Returns the departure case, i.e. the one with the label "D".
     *
     * @return the departure case, null if there aren't
     * @throws MazeIssueException If there are zero/two or more occurrences of a departure case
     */
    public VertexInterface getDeparture() throws MazeIssueException {
        int departureCount = 0;
        VertexInterface departure = null;
        for (VertexInterface[] vertexList : maze) {
            for (VertexInterface vertex : vertexList) {
                if (vertex.getLabel().equals("D")) {
                    departure = vertex;
                    departureCount++;
                    if (departureCount > 1) {
                        throw new MazeIssueException(vertex.getX(), vertex.getY(), "D");
                        //Forgot whether it's X then Y, or the reverse
                    }
                }
            }
        }
        if (departureCount == 0) {
            throw new MazeIssueException(this.mazeHeight, this.mazeWidth, "D");
        }
        return departure;
    }

    /**
     * Returns the arrival case, i.e. the one with the label "A".
     *
     * @return the arrival case
     * @throws MazeIssueException If there are zero/more than two occurrences of an arrival case
     */
    public VertexInterface getArrival() throws MazeIssueException {
        VertexInterface arrival = null;
        int count = 0;
        for (VertexInterface[] vertexList : maze) {
            for (VertexInterface vertex : vertexList) {
                if (vertex.getLabel().equals("A")) {
                    arrival = vertex;
                    count++;
                    if (count > 1) {
                        throw new MazeIssueException(vertex.getX(), vertex.getY(), "A");
                    }
                }
            }
        }
        if (count == 0) {
            throw new MazeIssueException(this.mazeHeight, this.mazeWidth, "A");
        }
        return arrival;
    }

    //We don't need to add the walls
    public ArrayList<VertexInterface> getAllVertices() {
        ArrayList<VertexInterface> allVerticesList = new ArrayList<>();
        for (VertexInterface[] vertexList : maze) {
            for (VertexInterface vertex : vertexList) {
                if (!vertex.getLabel().equals("W")) {
                    allVerticesList.add(vertex);
                }
            }
        }
        return allVerticesList;
    }

    public ArrayList<VertexInterface> getSuccessors(VertexInterface vertex) {
        int x = vertex.getX();
        int y = vertex.getY();
        //We add everything, making sure it is compatible
        ArrayList<VertexInterface> ListSuccessors = new ArrayList<>();
        if (y != 0) { //If vertex is not at the far left, then...
            if (!maze[x][y - 1].getLabel().equals("W")) {
                ListSuccessors.add(maze[x][y - 1]); //Here, we are adding the vertex at the left
            }
        }
        if (y != (mazeWidth - 1)) { //If vertex is not at the far right, then...
            if (!maze[x][y + 1].getLabel().equals("W")) {
                ListSuccessors.add(maze[x][y + 1]); //Here, we are adding the vertex at the right
            }
        }
        if (x != 0) { //If vertex is not at the far top, then...
            if (!maze[x - 1][y].getLabel().equals("W")) {
                ListSuccessors.add(maze[x - 1][y]); //Here, we are adding the vertex at the top
            }
        }
        if (x != (mazeHeight - 1)) { //If vertex is not at the far bottom, then...
            if (!maze[x + 1][y].getLabel().equals("W")) {
                ListSuccessors.add(maze[x + 1][y]); //Here, we are adding the vertex at the bottom
            }
        }
        return ListSuccessors;
    }

    public int getDistance(VertexInterface src, VertexInterface dst) {
        return 1; //Because in the successors, there are only non walls (it is made sure in dijkstra + getSuccessors)
    } //The implementation will obviously be different for something else besides a maze

    /**
     * Returns the label from maze[x][y].
     *
     * @param x the first coordinate of the VertexInterface
     * @param y the second coordinate of the VertexInterface
     * @return a String corresponding to the label of maze[x][y]
     */
    public String getLabelFromCoords(int x, int y) {
        return maze[x][y].getLabel();
    }


    /**
     * Returns maze[x][y].
     *
     * @param x the first coordinate of the VertexInterface
     * @param y the second coordinate of the VertexInterface
     * @return the VertexInterface corresponding to the given coordinates
     */
    public VertexInterface getVertex(int x, int y) {
        return maze[x][y];
    }

    /**
     * Sets the width and the height of the maze in accordance with the width and height of fileName.
     * Does not care if the all lines doesn't have the same length, or if it contains illegal characters.
     * Will assume that the default width is equal to the length of the first line (has repercussions in
     * initFromTextFile)
     *
     * @param fileName the maze in .txt format
     */
    public final void setParametersFromTextFile(String fileName) {
        File file = new File(fileName);
        FileReader fr;
        BufferedReader br = null;
        int linesNumber = 0;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String line = br.readLine();
            this.setMazeWidth(line.length());
            linesNumber++;
            for (line = br.readLine(); line != null; line = br.readLine()) {
                linesNumber++;
            }
            this.setMazeHeight(linesNumber);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Reads the fileName, and instantiates the maze in accordance to the data in fileName.
     * The uniques "D" and "A" will respectively correspond to the uniques DBox and ABox.
     * The "E" and "W" labels will respectively correspond to the EBoxes and WBoxes.
     *
     * @param fileName the file being read, source of maze's instantiation
     * @throws IOException          when an exception regarding Input/Output occurs
     * @throws MazeReadingException when the read maze is illegal (illegal characters, or zero/two departure/arrival
     *                              cases)
     */
    public final void initFromTextFile(String fileName) throws MazeReadingException, IOException {
        this.setParametersFromTextFile(fileName); //We get the number of lines of the current file,
        //as well as the length of the first line
        File file = new File(fileName);
        FileReader fr;
        BufferedReader br = null;
        int linesNumber = 0;
        try {
            int arrivalCount = 0;
            int departureCount = 0;
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                if (line.length() != this.mazeWidth) {
                    //We should be getting abscissa from the length of the first line
                    //Set it
                    //And finally verify the length of all next lines
                    throw new MazeReadingException(fileName, linesNumber, "This line does not have "
                            + this.mazeWidth + " boxes");
                }
                int i = linesNumber; //=> index of the rank of the line we're currently reading
                for (int j = 0; j < this.mazeWidth; j++) {//=> index of the character being read in the line
                    if (line.charAt(j) == 'A') {
                        if (arrivalCount == 0) {
                            maze[i][j] = new ABox(i, j);
                        } else {
                            throw new MazeReadingException(fileName, linesNumber, "Two occurrences of the A label.");
                        } //There can be more, but stopping at two is enough
                        arrivalCount++;
                    } else if (line.charAt(j) == 'D') {
                        if (departureCount == 0) {
                            maze[i][j] = new DBox(i, j);
                        } else {
                            throw new MazeReadingException(fileName, linesNumber, "Two occurrences of the D label.");
                        } //Same reason
                        departureCount++;
                    } else if (line.charAt(j) == 'E') {
                        maze[i][j] = new EBox(i, j);
                    } else if (line.charAt(j) == 'W') {
                        maze[i][j] = new WBox(i, j);
                    } else {
                        throw new MazeReadingException(fileName, linesNumber, "Illegal character found. ");
                    }
                }
                linesNumber++;
            }
            if (arrivalCount == 0 || departureCount == 0) {
                throw new MazeReadingException(fileName, linesNumber, "No occurrence of either departure or " +
                        "arrival found");
            }
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Instantiates a new Maze in accordance with the user view.
     *
     * @param boxArray an array of arrays which represents what the user sees
     * @throws MazeIssueException if the displayed maze is illegal (No arrival/departure case, most likely)
     */
    public final void readFromMazeBoxArray(PanelBoxes boxArray) throws MazeIssueException {
        int arrivalCount = 0;
        int departureCount = 0;
        for (int i = 0; i < boxArray.getPanelBoxesHeight(); i++) {
            for (int j = 0; j < boxArray.getPanelBoxesWidth(); j++) {
                String label = boxArray.getPanelBoxes(i, j).getBoxLabel();
                if (label.equals("A")) {
                    if (arrivalCount == 0) {
                        this.maze[i][j] = new ABox(i, j);
                    } else {
                        this.maze[i][j] = new EBox(i, j);
                        throw new MazeIssueException(i, j, "A");
                    }
                    arrivalCount++;
                } else if (label.equals("D")) {
                    if (departureCount == 0) {
                        this.maze[i][j] = new DBox(i, j);
                    } else {
                        this.maze[i][j] = new EBox(i, j);
                    }
                    departureCount++;
                } else if (label.equals("E")) {
                    this.maze[i][j] = new EBox(i, j);
                } else if (label.equals("W")) {
                    this.maze[i][j] = new WBox(i, j);
                }
            }
        }
        if (arrivalCount == 0 || departureCount == 0) {
            throw new MazeIssueException(boxArray.getPanelBoxesHeight(), boxArray.getPanelBoxesWidth(), "D, or A");
        }
    }

    /**
     * Saves the current maze into a new file with the name fileName. If fileName does not exist, it is created.
     * Otherwise, it's deleted and created again.
     *
     * @param fileName the name of the file which the maze data is stored
     */
    public final void saveToTextFile(String fileName) {
        try (PrintWriter pw = new PrintWriter(fileName)) {
            for (int i = 0; i < this.mazeHeight; i++) {
                for (int j = 0; j < this.mazeWidth; j++) {
                    String label = maze[i][j].getLabel();
                    if (j != this.mazeWidth - 1) {
                        pw.print(label);
                    } else {
                        pw.println(label);
                    }
                }
            }
        } catch (FileNotFoundException | SecurityException e) {
            e.printStackTrace();
        }
    }
}