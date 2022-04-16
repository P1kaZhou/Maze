package dijkstra;

public interface PiInterface {

    /**
     * Set a new distance for the VertexInterface x.
     *
     * @param a the VertexInterface we want to set a new distance for
     * @param x the new distance assigned to x
     */
    void setDistance(int a, VertexInterface x); // We set the distance :)

    /**
     * Returns the current assigned value for the VertexInterface x, currently stored in the Pi Table,
     * which is "presumed" to be the distance from the departure VertexInterface.
     *
     * @param x the VertexInterface we want to know which value it has assigned
     * @return the value assigned to x
     */
    int getPiValue(VertexInterface x); //To return the value set for x, stored in the Pi table
}
