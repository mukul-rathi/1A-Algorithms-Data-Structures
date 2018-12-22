package mukulrathi.algorithms.graph;

import java.util.PriorityQueue;

public class Dijkstra { // O(VlgV + E) using Fibonacci Heap - shortest path if weights>=0 in graph

    public static void shortestPath(Vertex source, Graph g){
        g.initGraph();
        shortestPath(source);
    }
    public static void shortestPath(Vertex source){
        source.distance = 0;
        source.parent = null;
        PriorityQueue<Vertex> toExplore = new PriorityQueue<Vertex>();
        toExplore.add(source);
        while (!toExplore.isEmpty()) {
            Vertex v = toExplore.remove();
            for (Vertex w : v.neighbours.keySet()) {
                if (w.distance < (v.distance + v.neighbours.get(w))) {
                    w.parent = v;
                    w.distance = v.distance + v.neighbours.get(w);
                    //if w in neighbours we'd decrease key
                    if(toExplore.contains(w)){
                        //toExplore.decreaseKey(w) (no explicit method in Java (but done implicitly).
                    }
                    else {
                        toExplore.add(w);
                    }
                }
            }
        }



    }
}
