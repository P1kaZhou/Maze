package dijkstra;

import java.util.HashSet;

public class ASet extends HashSet<VertexInterface> implements ASetInterface {

    /**
     * Instantiates a HashSet which will contain every vertex whose shortest path length has been found.
     */
    public ASet() {
        super(0);
    }

    public void union(VertexInterface pivot) {
        this.add(pivot);
    }

    public boolean has(VertexInterface vertex) {
        return this.contains(vertex);
    }
}
