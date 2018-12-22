package mukulrathi.datastructures.abstractdatatypes;

import mukulrathi.algorithms.graph.Vertex;

//Disjoint set data structure keeps track of elements belonging to a group of disjoint sets - has three operations
public abstract class DisjointSet{

    public abstract Vertex getSetWith(Vertex x); //return identifying member of set x is part of
    public abstract void addSingleton(Vertex x); //create  a new set with just x in it
    public abstract void merge(Vertex v, Vertex w); //merge the two sets which v and w are in respectively.

}
