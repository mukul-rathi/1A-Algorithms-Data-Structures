package mukulrathi.datastructures.abstractdatatypes;

public interface Stack<T> {
    boolean isEmpty(); //returns true if stack is empty, false otherwise

    void push(T x); //add element x to top of stack
                    //Post-conditions: isEmpty()==false, top()==x

    T pop(); //Pre-condition: isEmpty()==false
            //remove and return top element from stack

    T top(); //Pre-condition: isEmpty()==false
            //return top element from stack without removing it

}
