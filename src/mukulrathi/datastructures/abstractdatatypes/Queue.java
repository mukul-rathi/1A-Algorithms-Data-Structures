package mukulrathi.datastructures.abstractdatatypes;

import mukulrathi.customexceptions.UnderflowException;
/*
    This is a FIFO (First-In-First-Out) ADT. As with Stacks, can use either arrays or linked lists
    to implement it.
 */
public interface Queue<T> {
    boolean isEmpty(); //returns true if queue is empty, false otherwise

    void put(T x); //add element x to end of queue
    //Post-condition: isEmpty()==false

    T get() throws UnderflowException; //Pre-condition: isEmpty()==false
    //remove and return first element from queue

    T first() throws UnderflowException; //Pre-condition: isEmpty()==false
        //return top element from queue without removing it
}
