package mukulrathi.datastructures.abstractdatatypes.sets;
/*
* A static set is filled once, and only used for lookup
*/

public interface StaticSet<T> {
    boolean isEmpty(); //true if empty set, false otherwise

    boolean hasKey(T x); //return true if set contains key x

    T chooseAny(); //Precondition: isEmpty()==false
                    //returns any arbitrary key from set

    int size(); // returns cardinality of set
}
