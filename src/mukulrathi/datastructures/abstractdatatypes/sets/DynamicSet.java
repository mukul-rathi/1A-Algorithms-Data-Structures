package mukulrathi.datastructures.abstractdatatypes.sets;
/*
Dynamic sets add in the ability to add and remove elements
 */
public interface DynamicSet<K> extends StaticSet<K> {

    void insert(K x); //inserts an element x to the set
    void delete(K x); //removes element x from set if present in the set, does nothing if x not in set.

    DynamicSet<K> union(StaticSet<K> s); //returns the union of the current set (this) with set S

    DynamicSet<K> intersection(StaticSet<K> s); //returns the intersection of the current set (this) with set S

    DynamicSet<K> difference(StaticSet<K> s); //returns the difference of the current set (this) with set S

    boolean subset(StaticSet<K> s); //true if current set (this) is a subset of set S, false otherwise
}
