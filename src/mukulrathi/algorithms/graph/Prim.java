package mukulrathi.algorithms.graph;

import java.util.PriorityQueue;

public class Prim { // O(E+VlgV) if using Fib Heap since O(lg V) pop min and O(1) insert just like Dijkstra

    //each step we choose greedily the vertex whose edge weight to a vertex in the tree is the smallest.

    public static void minimumSpanningTree(Graph g){
            if (g.vertices.size()==0) return ;
            g.initGraph();
            minimumSpanningTree(g.vertices.get(0));
        }
        public static void minimumSpanningTree(Vertex source){
            source.distance = 0; //here distance is the weight of the min weight edge from a vertex in the tree.
            source.parent = null;
            PriorityQueue<Vertex> toExplore = new PriorityQueue<Vertex>();
            toExplore.add(source);
            while (!toExplore.isEmpty()) {
            Vertex v = toExplore.remove();
            v.isInTree = true;
            for (Vertex w : v.neighbours.keySet()) {
                if (!w.isInTree && v.neighbours.get(w) <  w.distance){ //relax w.distance if edge weight < w.distance.
                    w.parent = v;
                    w.distance = v.neighbours.get(w); //distance from tree
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
