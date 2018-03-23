package mukulrathi.datastructures.abstractdatatypes;
/*
Stores a mapping from keys to values - this is a function, i.e one key has at most one value
associated with it.
This is a simple interface, but practical implementations may also have a containsKey(), size() etc.
 */
public interface Dictionary<K,V> {
    void set(K k, V v); //store the (k,v) pair in dictionary -
                        // overwrite any old value associated with k
                        //Post-condition: get(k) == v
    V get(K k); //Pre-condition: a pair with key k is in dictionary
                //returns value associated with key k, without removing it from dictionary

    void delete(K k); //Pre-condition: a pair with key k already inserted in dictionary.
                        //removes key-value pair indexed by k from dictionary
}
