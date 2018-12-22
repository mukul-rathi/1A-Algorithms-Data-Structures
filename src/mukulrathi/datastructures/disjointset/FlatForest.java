package mukulrathi.datastructures.disjointset;

import mukulrathi.algorithms.graph.Vertex;
import mukulrathi.datastructures.abstractdatatypes.DisjointSet;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

//Flat Forest - each element in a set has a pointer to the representative of the set

//implement each set as a linked list with rep = head of list
public class FlatForest extends DisjointSet{  //aggregate cost of m operations of which n are singleton operations = O(m+nlgn)
    HashMap<Vertex,LinkedList<Vertex>> disjointSets = new  HashMap<Vertex,LinkedList<Vertex>>(); //a list of disjoint sets indexed by their representatives

    @Override
    public Vertex getSetWith(Vertex x) { //O(1)
        return x.parent;
    }

    @Override
    public void addSingleton(Vertex x) {//O(1)
        LinkedList<Vertex> singleton = new LinkedList<Vertex>();
        singleton.add(x);
        x.parent = x; //x is representative for singleton
        disjointSets.put(x, singleton);
    }

    @Override
    public void merge(Vertex u, Vertex v) {  //O(n) where n = number of items in smaller set - expensive

        //get disjoint sets that u and v are part of
        LinkedList<Vertex> disjointsetU = disjointSets.get(u.parent); //u.parent = representative of disjoint set u is part of
        LinkedList<Vertex> disjointsetV = disjointSets.get(v.parent);


        //weighted union heuristic - merge smaller set into bigger set
        if(disjointsetU.size() < disjointsetV.size()){
             disjointSets.remove(u.parent);
            disjointsetV.addAll(disjointsetU);
        }
        else {
            disjointSets.remove(v.parent);
            disjointsetU.addAll(disjointsetV);
        }
    }
}
