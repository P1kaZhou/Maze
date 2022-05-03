package dijkstra;

import java.util.Hashtable;

public class Pi extends Hashtable<VertexInterface, Integer> implements PiInterface {

    /**
     * Creates a Hashtable associating every vertex with its shortest path.
     */
    public Pi() {
        super();
    }

    public void setDistance(int a, VertexInterface x) {
        this.put(x, a);
    }

    public int getPiValue(VertexInterface x) {
        return this.get(x);
    }
}
