package mukulrathi.algorithms.graph;

import java.util.ArrayList;
import java.util.List;

public class TopologicalSort { //O(V+E) since just depth first search

    public static List<Vertex> topologicalSort (Graph g) {
        ArrayList<Vertex> totalOrder = new ArrayList<Vertex>();
        g.initGraph();
        for(Vertex v : g.vertices){
            if(!v.seen){
                visit(v, totalOrder);
            }
        }
        return totalOrder;
    }

    private static void visit(Vertex v, ArrayList<Vertex> totalOrder) {
        v.seen = true;
        for (Vertex w : v.neighbours.keySet()) {

            if (!w.seen) {
                w.seen = true;
                w.parent = v;
                w.distance = v.distance + 1;
                visit(w, totalOrder);
            }
        }
        totalOrder.add(0, v); // prepend the vertex to the total order;
    }
}
