package mukulrathi.datastructures.disjointset;

import mukulrathi.algorithms.graph.Vertex;
import mukulrathi.datastructures.abstractdatatypes.DisjointSet;

import java.util.HashMap;


//use laziness to only clean up when necessary and get best of flat forest's O(1) getSetWith and deepForest's O(1) merge

public class LazyForest extends DisjointSet { //aggregate cost of m operations of which n are singleton operations =O(m alpha_n)
    // where the sequence alpha_n is so slowly growing that effectively constant

    HashMap<Vertex, Integer> disjointSets; // keep track of the roots of each disjoint set and their upper bounds of heights
    //here we only maintain upper bound on height - don't actually update during path compression since that is expensive.
    @Override



    //walk up tree to find root and then set x and all intermediate nodes to point to root - this flattening is
    //known as the path compression heuristic

    public Vertex getSetWith(Vertex x) {
        if(x.parent!=x){ //if not root (root points to itself)
            x.parent = getSetWith(x.parent);
        }
        return x.parent;
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

        // v and w are roots of trees
        //union by rank heuristic - to minimise height point lower-height root to higher-height root
        if (disjointSets.get(v)< disjointSets.get(w)){
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
