package mukulrathi.datastructures.abstractdatatypes.sets;
/*
Dynamic sets add in the ability to add and remove elements
 */
public interface DynamicSet<T> extends StaticSet<T> {

    void insert(T x); //inserts an element x to the set
    void delete(T x); //removes element x from set if present in the set, does nothing if x not in set.

    DynamicSet<T> union(StaticSet<T> s); //returns the union of the current set (this) with set S

    DynamicSet<T> intersection(StaticSet<T> s); //returns the intersection of the current set (this) with set S

    DynamicSet<T> difference(StaticSet<T> s); //returns the difference of the current set (this) with set S

    boolean subset(StaticSet<T> s); //true if current set (this) is a subset of set S, false otherwise
}
