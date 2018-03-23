package mukulrathi.datastructures.abstractdatatypes;

import mukulrathi.customexceptions.OutOfBoundsException;

public interface Vector<T>{

    //ranks are 0-indexed by convention

    T elemAtRank(int r) throws OutOfBoundsException; //return element at rank (position) r

    void insertAtRank(int r, T val) throws OutOfBoundsException; //Pre-condition: vector rank>=r
                                    //inserts the elem at rank r of the vector

    void replaceatRank(int r, T newVal) throws OutOfBoundsException; //Pre-condition: vector rank>=r
                                    //replaces the element with newElem at rank r of the vector

    T removeAtRank(int r) throws OutOfBoundsException; //Pre-condition: vector rank>=r
                           // removes item at rank r
                           // Post-condition - size of vector reduced by 1

}
