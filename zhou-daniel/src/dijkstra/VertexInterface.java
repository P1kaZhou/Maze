package dijkstra;

public interface VertexInterface {

    /**
     * Returns the label (which is the Vertex type) from a given VertexInterface.
     *
     * @return a String corresponding to the Vertex label
     */
    String getLabel();

    /**
     * Returns the integer x, which is the Vertex first coordinate.
     *
     * @return an integer corresponding to the Vertex first coordinate
     */
    int getX();

    /**
     * Returns the integer y, which is the Vertex first coordinate.
     *
     * @return an integer corresponding to the Vertex second coordinate
     */
    int getY();
}
