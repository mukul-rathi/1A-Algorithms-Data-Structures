package mukulrathi.algorithms.graph;

import mukulrathi.datastructures.abstractdatatypes.DisjointSet;
import mukulrathi.datastructures.disjointset.LazyForest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Kruskal { //O(ElgV) since O(ElgE) but lgE = O(lg V) since between V-1 and V(V-1)/2 edges in connected graph

    //add min weight edge at each iteration unless it forms a cycle
    public List<Edge> minimumSpanningTree(Graph g){
        ArrayList<Edge> treeEdges = new ArrayList<Edge>();
        DisjointSet partition = new LazyForest();

        for(Vertex v : g.vertices){ // O(V)
            partition.addSingleton(v);
        }

        //sort edges in ascending order of weight
        Collections.sort(g.edges, (e1, e2) -> e1.weight - e2.weight); //O(E lg E)

        for(Edge e : g.edges){ //O(E) since all operations inside loop are O(1)
            Vertex p = partition.getSetWith(e.start);
            Vertex q = partition.getSetWith(e.end);

            if(p!=q){ //not part of same connected component so no cycles
                treeEdges.add(e);
                partition.merge(p, q);
            }
        }

        return treeEdges;
    }
}
