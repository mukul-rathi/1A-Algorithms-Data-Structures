package mukulrathi.datastructures.disjointset;

import mukulrathi.algorithms.graph.Vertex;
import mukulrathi.datastructures.abstractdatatypes.DisjointSet;

import java.util.HashMap;

//implement set as a tree with representative = root of tree

public class DeepForest extends DisjointSet {
    HashMap<Vertex, Integer> disjointSets; // keep track of the roots of each disjoint set and their heights
    @Override
    public Vertex getSetWith(Vertex x) { //O(h) where h = height of tree
        while (x.parent !=null){
            x = x.parent;
        }
        return x;
    }

    @Override
    public void addSingleton(Vertex x) { //O(1)
        x.parent = null;
        disjointSets.put(x, 0); // x is a tree with height 0;
    }

    @Override
    public void merge(Vertex t, Vertex u) { //O(1) - assuming vertices t and u are roots

        //to double check we will get the roots just in case
        Vertex v = getSetWith(t);
        Vertex w = getSetWith(u);

        //union by rank heuristic - to minimise height point lower-height root to higher-height root
        if(disjointSets.get(v)< disjointSets.get(w)){
            v.parent = w;
            disjointSets.put(w, Math.max(disjointSets.get(v) + 1, disjointSets.get(w))); //update height of w if increased
            disjointSets.remove(v);
        }
        else{
            w.parent = v;
            disjointSets.put(v, Math.max(disjointSets.get(w) + 1, disjointSets.get(v))); //update height of v if increased
            disjointSets.remove(w);
        }
    }


}
