package mukulrathi.datastructures.abstractdatatypes.sets;
/*
This adds a total ordering on the set
 */
public interface OrderedSet<T> extends DynamicSet<T> {
    T min(); // Precondition: isEmpty() ==false
             // returns min element in set

    T max();// Precondition: isEmpty() ==false
    // returns max element in set

    T predecessor(T x); // Precondition: hasKey(k) ==true, min()!=x
    // returns largest key in set that is smaller than x;

    T successor(T x); // Precondition: hasKey(k) ==true, max()!=x
    // returns smallest key in set that is greater than x;
}
