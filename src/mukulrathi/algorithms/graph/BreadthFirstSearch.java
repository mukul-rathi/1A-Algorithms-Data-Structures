package mukulrathi.algorithms.graph;


import mukulrathi.customexceptions.PathNotFoundException;
import mukulrathi.customexceptions.UnderflowException;
import mukulrathi.datastructures.abstractdatatypes.Queue;
import mukulrathi.datastructures.implementations.queues.ArrayQueue;

import java.util.ArrayList;
import java.util.List;

public class BreadthFirstSearch {


    public static void BFS(Vertex source, Graph g) {
        g.initGraph();
        BFS(source);
    }

    public static void BFS(Vertex source) {
        source.distance = 0;
        source.parent = null;
        Queue<Vertex> toExplore = new ArrayQueue<Vertex>();
        toExplore.put(source);
        while (!toExplore.isEmpty()) {
            try {
                Vertex v = toExplore.get();
                for (Vertex w : v.neighbours.keySet()) {
                    if (!w.seen) {
                        w.seen = true;
                        w.parent = v;
                        w.distance = v.distance + 1;
                        toExplore.put(w);
                    }
                }
            } catch (UnderflowException e) { //not going to happen because we check if non-empty in the while loop
                e.printStackTrace();
            }

        }
    }
    public static List<Vertex> BFS_path(Vertex source, Vertex target, Graph g) throws PathNotFoundException {
        g.initGraph();
        return BFS_path(source,target);
    }

    public static List<Vertex> BFS_path(Vertex source, Vertex target) throws PathNotFoundException {
        BFS(source);
        ArrayList<Vertex> path = new ArrayList<Vertex>();
        Vertex curr = target;
        while (curr != source) {
            if(curr==null){
                throw new PathNotFoundException();
            }
            path.add(0, curr);
            curr = curr.parent;
        }
        path.add(0, source);
        return path;

    }

}
