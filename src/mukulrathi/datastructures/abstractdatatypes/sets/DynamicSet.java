package mukulrathi.datastructures.abstractdatatypes.sets;
/*
Dynamic sets add in the ability to add and remove elements
 */
public interface DynamicSet<K> extends StaticSet<K> {

    void insert(K k); //inserts an element k to the set
    void delete(K k); //removes element k from set if present in the set, does nothing if k not in set.

    DynamicSet<K> union(StaticSet<K> s); //returns the union of the current set (this) with set S

    DynamicSet<K> intersection(StaticSet<K> s); //returns the intersection of the current set (this) with set S

    DynamicSet<K> difference(StaticSet<K> s); //returns the difference of the current set (this) with set S

    boolean subset(StaticSet<K> s); //true if current set (this) is a subset of set S, false otherwise
}
