package mukulrathi.datastructures.abstractdatatypes.sets;
/*
* A static set is filled once, and only used for lookup
*/

import mukulrathi.customexceptions.KeyNotFoundException;

public interface StaticSet<K> {
    boolean isEmpty(); //true if empty set, false otherwise

    boolean hasKey(K k); //return true if set contains key k

    K chooseAny() throws KeyNotFoundException; //Precondition: isEmpty()==false
                    //returns any arbitrary key from set

    int size(); // returns cardinality of set
}
