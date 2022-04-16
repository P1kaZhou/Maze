package dijkstra;

import java.util.Hashtable;

public class Previous extends Hashtable<VertexInterface, VertexInterface> implements PreviousInterface {

    /**
     * Instantiates a Hashtable associating every reachable vertex to its predecessor.
     */
    public Previous() {
        super();
    }

    public void affectation(VertexInterface vertex, VertexInterface Pivot) {
        this.put(vertex, Pivot); //vertex's father is Pivot
    }

    public VertexInterface getPrevious(VertexInterface pre) {
        return this.get(pre);
    }

}
