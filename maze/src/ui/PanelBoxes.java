package ui;

//With this class, the view never interacts directly with the maze and vice-versa
//The only moments it does, it's through the ActionListeners
public class PanelBoxes {

    private final int height;
    private final int width;
    private final MazeBox[][] panelBoxes;

    /**
     * The constructor of the array of arrays of boxes.
     *
     * @param height The height of the maze
     * @param width  The width of the maze
     */
    public PanelBoxes(int height, int width) {
        this.height = height;
        this.width = width;
        this.panelBoxes = new MazeBox[height][width];
    }

    /**
     * Set the new MazeBox at (i,j) to box.
     *
     * @param box The new MazeBox
     * @param i   The first coordinate of panelBoxes
     * @param j   The second coordinate of panelBoxes
     */
    public void setBox(MazeBox box, int i, int j) {
        this.panelBoxes[i][j] = box;
    }

    /**
     * Returns the current width of the maze.
     *
     * @return The width of the maze.
     */
    public int getPanelBoxesWidth() {
        return width;
    }

    /**
     * Returns the current height of the maze.
     *
     * @return The current maze height
     */
    public int getPanelBoxesHeight() {
        return height;
    }

    /**
     * Returns the MazeBox at (i,j)
     *
     * @param i The first coordinate of the MazeBox
     * @param j The second coordinate of the MazeBox
     * @return The MazeBox corresponding to the given parameters
     */
    public MazeBox getPanelBoxes(int i, int j) {
        return panelBoxes[i][j];
    }
}
