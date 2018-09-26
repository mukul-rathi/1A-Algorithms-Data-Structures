package mukulrathi.algorithms.graph;

import mukulrathi.customexceptions.PathNotFoundException;
import mukulrathi.customexceptions.UnderflowException;
import mukulrathi.datastructures.abstractdatatypes.Stack;
import mukulrathi.datastructures.implementations.stack.ArrayStack;

import java.util.ArrayList;
import java.util.List;

public class DepthFirstSearch {


    public static void DFS(Vertex source, Graph g) {
        g.initGraph();
        DFS(source);
    }
    public static void DFS(Vertex source) {
        source.distance = 0;
        source.parent = null;
        Stack<Vertex> toExplore = new ArrayStack<Vertex>();
        toExplore.push(source);
        while (!toExplore.isEmpty()) {
            try {
                Vertex v = toExplore.pop();
                for (Vertex w : v.neighbours.keySet()) {
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
    public static List<Vertex> DFS_path(Vertex source, Vertex target, Graph g) throws PathNotFoundException {
        g.initGraph();
        return DFS_path(source,target);
    }

    public static List<Vertex> DFS_path(Vertex source, Vertex target) throws PathNotFoundException {
        DFS(source);
        ArrayList<Vertex> path = new ArrayList<Vertex>();
        Vertex curr = target;
        while (curr != source) {
            if (curr == null) {
                throw new PathNotFoundException();
            }
            path.add(0, curr);
            curr = curr.parent;
        }
        path.add(0, source);
        return path;

    }

    public static void DFS_recurse(Vertex source) {
        visit(source);
    }

    private static void visit(Vertex v) {
        v.seen = true;
        for (Vertex w : v.neighbours.keySet()) {

            if (!w.seen) {
                w.seen = true;
                w.parent = v;
                w.distance = v.distance + 1;
                visit(w);
            }
        }
    }

}
