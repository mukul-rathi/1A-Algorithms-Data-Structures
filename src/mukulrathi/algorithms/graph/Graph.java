package mukulrathi.algorithms.graph;

import java.util.List;

public class Graph {
    public List<Vertex> vertices;
    public  void initGraph() {
        for (Vertex v : this.vertices) {
            v.distance = Integer.MAX_VALUE;
            v.seen = false;
            v.parent = null;
        }
    }
}
