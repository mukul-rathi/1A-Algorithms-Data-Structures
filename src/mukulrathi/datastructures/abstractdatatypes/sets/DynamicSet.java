package mukulrathi.datastructures.abstractdatatypes.sets;

import mukulrathi.customexceptions.KeyNotFoundException;

/*
Dynamic sets add in the ability to add and remove elements
 */
public interface DynamicSet<K> extends StaticSet<K> {

    void insert(K k); //inserts an element k to the set
    void delete(K k) throws KeyNotFoundException; //removes element k from set if present in the set, does nothing if k not in set.

    DynamicSet<K> union(DynamicSet<K> s); //returns the union of the current set (this) with set S

    DynamicSet<K> intersection(DynamicSet<K> s); //returns the intersection of the current set (this) with set S

    DynamicSet<K> difference(DynamicSet<K> s); //returns the difference of the current set (this) with set S i.e  this\S in set notation

    boolean subset(DynamicSet<K> s); //true if set S is a subset of current set (this) , false otherwise
}
