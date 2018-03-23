package mukulrathi.datastructures.abstractdatatypes;

import mukulrathi.customexceptions.UnderflowException;

public interface Stack<T> {
    boolean isEmpty(); //returns true if stack is empty, false otherwise

    void push(T x); //add element x to top of stack
                    //Post-conditions: isEmpty()==false, top()==x

    T pop() throws UnderflowException; //Pre-condition: isEmpty()==false
            //remove and return top element from stack

    T top() throws UnderflowException; //Pre-condition: isEmpty()==false
            //return top element from stack without removing it

}
