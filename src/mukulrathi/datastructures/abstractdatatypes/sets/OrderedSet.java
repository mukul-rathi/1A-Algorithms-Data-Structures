package mukulrathi.datastructures.abstractdatatypes.sets;

import mukulrathi.customexceptions.KeyNotFoundException;
import mukulrathi.customexceptions.UnderflowException;

/*
This adds a total ordering on the set
 */
public interface OrderedSet<K extends Comparable<K>> extends DynamicSet<K> {
    K min() throws UnderflowException; // Precondition: isEmpty() ==false
             // returns min element in set

    K max() throws UnderflowException;// Precondition: isEmpty() ==false
    // returns max element in set

    K predecessor(K x) throws KeyNotFoundException; // Precondition: hasKey(k) ==true, min()!=x
    // returns largest key in set that is smaller than x;

    K successor(K x); // Precondition: hasKey(k) ==true, max()!=x
    // returns smallest key in set that is greater than x;
}
