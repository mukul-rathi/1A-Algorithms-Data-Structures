package mukulrathi.datastructures.abstractdatatype;

public interface Vector<T>{
    T elemAtRank(int r); //return element at rank (position) r

    void insertAtRank(int r, T elem); //Pre-condition: vector rank>=r
                                    //inserts the elem at rank r of the vector

    void replaceatRank(int r, T newElem); //Pre-condition: vector rank>=r
                                    //replaces the element with newElem at rank r of the vector

    T removeAtRank(int r); //Pre-condition: vector rank>=r
                           // removes item at rank r
                           // Post-condition - size of vector reduced by 1

}
