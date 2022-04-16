package dijkstra;

import java.util.ArrayList;

import static java.lang.Integer.MAX_VALUE; //We need max_value to initialize PiTable at the beginning

public class Dijkstra {

    /**
     * Applies Dijkstra's algorithm to the graph g, with the departure vertex r
     *
     * @param g The graph we are considering
     * @param r The vertex we're starting from
     * @return A PreviousInterface associating every reachable vertex starting from r to one predecessor
     * (they must have at least one)
     */
    public static PreviousInterface dijkstraPrevious(GraphInterface g, VertexInterface r) { //Public because test
        ASetInterface a = new ASet(); // First point
        a.union(r); //First point also
        VertexInterface pivot = r; //Second point
        PreviousInterface parents = new Previous(); //We are initializing the Previous Table there
        PiInterface piTable = new Pi(); //Initialization of Pi here
        ArrayList<VertexInterface> vertexList = g.getAllVertices();
        for (VertexInterface vertex : vertexList) {
            if (!vertex.equals(r)) {
                piTable.setDistance(MAX_VALUE, vertex); //Fourth point
            } else {
                piTable.setDistance(0, vertex); //We do the third line here
            }
        }
        int n = vertexList.size();
        for (int i = 1; i < n; i++) { //Fifth point
            ArrayList<VertexInterface> pivotSuccessors = g.getSuccessors(pivot);
            for (VertexInterface vertex : pivotSuccessors) {
                if (!a.has(vertex) &&
                        (piTable.getPiValue(pivot) + g.getDistance(pivot, vertex)) < piTable.getPiValue(vertex)) {
                    piTable.setDistance(piTable.getPiValue(pivot) + g.getDistance(pivot, vertex), vertex);
                    parents.affectation(vertex, pivot); //We are adding stuff in piTable and parents
                }
            }
            int nextPivotValue = MAX_VALUE;
            for (VertexInterface vertex : vertexList) { //We are looking for the vertices y not in A
                // where pi is the minimum
                int piVertex = piTable.getPiValue(vertex); //Because it looks nice
                if (!a.has(vertex) && piVertex < nextPivotValue) {
                    pivot = vertex;
                    nextPivotValue = piVertex;
                }
            } //We have the minimum Pi(y) with y not in A at the end of this iteration
            a.union(pivot);
        }
        return parents;
    }
}