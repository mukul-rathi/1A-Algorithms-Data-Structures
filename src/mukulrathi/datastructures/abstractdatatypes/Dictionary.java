package mukulrathi.datastructures.abstractdatatypes;

import mukulrathi.customexceptions.KeyNotFoundException;

/*
Stores a mapping from keys to values - this is a function, i.e one key has at most one value
associated with it.
A dictionary is like a dynamic set optimised for lookup.
This is a simple interface, but practical implementations may also have a containsKey(), size() etc.

To implement Sets/Dictionaries we have multiple potential options:
    Vector: Use direct addressing - if keys in range [a...b] then subtract a to give index into vector
            O(1) lookup but inefficient space-wise if number of keys << b-a.

    Lists: each node stores key,value pair - get() does linear search through list.
            set() - either adds to front of list -> so get() would consider first item with key
                    when traversing as actual value (lazy - means cost of get() increases)
                    or scans through list first and replaces if key already present

    Arrays: good for static sets (i.e no adding/removing) and also if keys ordered as can binary search
            to lookup key - O(lg n).

 */
public interface Dictionary<K extends Comparable<K>,V> {
    void set(K k, V v); //store the (k,v) pair in dictionary -
                        // overwrite any old value associated with k
                        //Post-condition: get(k) == v
    V get(K k) throws KeyNotFoundException; //Pre-condition: a pair with key k is in dictionary
                //returns value associated with key k, without removing it from dictionary

    void delete(K k) throws KeyNotFoundException; //Pre-condition: a pair with key k already inserted in dictionary.
                        //removes key-value pair indexed by k from dictionary
}
