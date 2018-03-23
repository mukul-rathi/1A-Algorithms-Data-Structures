package mukulrathi.datastructures.abstractdatatype;

public interface List<T>{

    boolean isEmpty(); //returns true if empty, false otherwise

    T head(); //Pre-condition: isEmpty()==false
              //returns first element of list - without removing it

    void prepend(T x); //add element x to beginning of list
    //Post-conditions: isEmpty()==false and head()==x;

    List<T> tail(); //Pre-condition: isEmpty()==false
                    // return list of elements except first, without removing it
    void setTail(List<T> newTail); //Pre-condition: isEmpty()==false
                // replace tail with newTail

}
