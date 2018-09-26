package mukulrathi.algorithms.graph;

import java.util.PriorityQueue;

public class Dijkstra {

    public static void dijkstra(Vertex source, Graph g){
        g.initGraph();
        dijkstra(source);
    }
    public static void dijkstra(Vertex source){
        source.distance = 0;
        source.parent = null;
        PriorityQueue<Vertex> toExplore = new PriorityQueue<Vertex>();
        toExplore.add(source);
        while (!toExplore.isEmpty()) {
        }
                Vertex v = toExplore.remove();
                for (Vertex w : v.neighbours.keySet()) {
                    if (w.distance < (v.distance + v.neighbours.get(w))){
                        w.parent = v;
                        w.distance = v.distance + v.neighbours.get(w);
                        //if w in neighbours we'd decrease key
                        toExplore.add(w);
                    }
                }




    }
}
