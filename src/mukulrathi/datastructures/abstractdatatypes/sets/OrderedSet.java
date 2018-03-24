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

    K predecessor(K k) throws KeyNotFoundException; // Precondition: hasKey(k) ==true, min()!=k
    // returns largest key in set that is smaller than k;

    K successor(K k) throws KeyNotFoundException; // Precondition: hasKey(k) ==true, max()!=k
    // returns smallest key in set that is greater than k;
}
