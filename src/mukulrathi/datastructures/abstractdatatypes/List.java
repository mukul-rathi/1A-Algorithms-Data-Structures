package mukulrathi.datastructures.abstractdatatypes;

import mukulrathi.customexceptions.UnderflowException;

public interface List<T>{

    boolean isEmpty(); //returns true if empty, false otherwise

    T head() throws UnderflowException; //Pre-condition: isEmpty()==false
              //returns first element of list - without removing it

    void prepend(T x); //add element x to beginning of list
    //Post-conditions: isEmpty()==false and head()==x;

    List<T> tail() throws UnderflowException; //Pre-condition: isEmpty()==false
                    // return list of elements except first, without removing it
    void setTail(List<T> newTail) throws UnderflowException; //Pre-condition: isEmpty()==false
                // replace tail with newTail

}
