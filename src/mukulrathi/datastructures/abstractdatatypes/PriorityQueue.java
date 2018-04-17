package mukulrathi.datastructures.abstractdatatypes;

import java.util.Comparator;

/*
    The PriorityQueue ADT holds a dynamic set of items, each of which are assigned a priority/key
    and offers convenient access to the item with highest priority (/smallest key).
 */

public abstract class PriorityQueue<T>{
    //All classes that extend this should supply a comparator
    protected Comparator<? super T> mComp;

    public PriorityQueue(Comparator<? super T> comp){
        mComp= comp;
    }

    public abstract void insert(T x); // add item to queue

    public abstract T first(); // return item with smallest key (without removing it from queue)

    public abstract T extractMin();// return item with smallest key and remove it from queue)

    public abstract void delete(T x); //Pre-condition: x is in queue
                                        //Remove x from queue

    public abstract void decreaseKey(T x); //decrease key of x (NB for ease of implementation, we have removed the
                                            // Key parameter and relaxed the pre-condition that Key < x.key)

    public abstract void merge(PriorityQueue<T> otherQ); //merge this queue with the supplied queue



}

