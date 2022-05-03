package dijkstra;

public interface PreviousInterface {

    /**
     * Adds the key Pivot to the VertexInterface vertex.
     *
     * @param vertex the affected VertexInterface
     * @param Pivot  the key
     */
    void affectation(VertexInterface vertex, VertexInterface Pivot); //vertex's father is pivot

    /**
     * Returns the father of the VertexInterface pre.
     *
     * @param pre the VertexInterface we want to know which key he has
     * @return a VertexInterface which is the father of pre
     */
    VertexInterface getPrevious(VertexInterface pre); //to find the father of pre
}
