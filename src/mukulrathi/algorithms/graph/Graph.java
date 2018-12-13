package mukulrathi.algorithms.graph;

import java.util.List;

public class Graph {
    public List<Vertex> vertices;
    public List<Edge> edges;
    public  void initGraph() {
        for (Vertex v : this.vertices) {
            v.distance = Integer.MAX_VALUE;
            v.seen = false;
            v.parent = null;
        }
    }
}
