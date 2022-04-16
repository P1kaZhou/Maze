package dijkstra;

public interface ASetInterface {

    /**
     * Adds the argument pivot into the A set.
     *
     * @param pivot the VertexInterface we want to add to the A set
     */
    void union(VertexInterface pivot); // For adding a vertex in A

    /**
     * Checks whether vertex is, or not, in A.
     *
     * @param vertex the vertex we want to check
     * @return true if vertex is in A, false if not
     */
    boolean has(VertexInterface vertex);
}

