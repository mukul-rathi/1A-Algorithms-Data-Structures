package mukulrathi.algorithms.graph;

import mukulrathi.customexceptions.PathNotFoundException;
import mukulrathi.customexceptions.UnderflowException;
import mukulrathi.datastructures.abstractdatatypes.Stack;
import mukulrathi.datastructures.implementations.stack.ArrayStack;

import java.util.ArrayList;
import java.util.List;

public class DepthFirstSearch {
    public DepthFirstSearch(Graph g){
        for (Vertex v: g.vertices){
            v.distance = Integer.MAX_VALUE;
            v.seen = false;
            v.parent = null;
        }
    }
    public static void DFS(Vertex source) {
        source.distance = 0;
        source.parent = null;
        Stack<Vertex> toExplore = new ArrayStack<Vertex>();
        toExplore.push(source);
        while (!toExplore.isEmpty()) {
            try {
                Vertex v = toExplore.pop();
                for (Vertex w : v.neighbours) {
                    if (!w.seen) {
                        w.seen = true;
                        w.parent = v;
                        w.distance = v.distance + 1;
                        toExplore.push(w);
                    }
                }
            } catch (UnderflowException e) { //not going to happen because we check if non-empty in the while loop
                e.printStackTrace();
            }

        }
    }

    public static List<Vertex> DFS_path(Vertex source, Vertex target) throws PathNotFoundException {
        DFS(source);
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
