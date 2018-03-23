package mukulrathi.datastructures.abstractdatatypes;

import mukulrathi.customexceptions.UnderflowException;

/*
Deque = Double-ended queue
This is a variant on the queue, where you can add or remove items from both ends.

Stacks and Queues can be seen as restricted Deques, where only one put and one get are allowed.
 */
public interface Deque<T> {
    boolean isEmpty(); //returns true if deque is empty, false otherwise

    void putFront(T x);
    void putRear(T x); //Post-condition for both : isEmpty()==false

    T getFront() throws UnderflowException;
    T getRear() throws UnderflowException; //Pre-condition for both: isEmpty()==false
}
