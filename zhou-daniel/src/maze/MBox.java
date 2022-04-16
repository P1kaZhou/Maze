package maze;

import dijkstra.VertexInterface;

public abstract class MBox implements VertexInterface {

    /**
     * The first coordinate of the maze case, final because it won't change.
     */
    private final int x;

    /**
     * The second coordinate of the maze Case, final because it won't change.
     */
    private final int y;

    /**
     * The label corresponding to the maze case type, "A" for Arrival, "D" for Departure, "E" for Empty and finally
     * "W" for Wall.
     */
    private final String label;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return getLabel() + "Box (" + getX() + ", " + getY() + ")";
    }

    /**
     * The constructor of every box of the maze, set every proper parameter into the one given in argument.
     *
     * @param x     the first coordinate of the box
     * @param y     the second coordinate of the box
     * @param label the box type
     */
    public MBox(int x, int y, String label) {
        this.x = x;
        this.y = y;
        this.label = label;
    }


}
