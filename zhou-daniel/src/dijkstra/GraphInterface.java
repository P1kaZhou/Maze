package dijkstra;

import java.util.ArrayList;

public interface GraphInterface {

    /**
     * Returns the distance between a and b, two VertexInterface given in argument, which are linked by an arc.
     *
     * @param src the first vertex we're considering
     * @param dst the second vertex which we want the distance
     * @return the distance between a and b
     */
    int getDistance(VertexInterface src, VertexInterface dst); //We kind of(?) need this although the distance is 1 for maze

    /**
     * For a given VertexInterface, returns all its successors, ie adjacent vertices
     *
     * @param vertex a vertex
     * @return all successors of vertex
     */
    ArrayList<VertexInterface> getSuccessors(VertexInterface vertex); //Self-explanatory, I guess

    /**
     * Returns all VertexInterface from a given GraphInterface (the one instanced here in this case).
     *
     * @return an ArrayList of VertexInterfaces
     */
    ArrayList<VertexInterface> getAllVertices(); //We need a list of all vertices for dijkstra
}
