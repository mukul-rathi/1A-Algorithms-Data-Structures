package mukulrathi.unittests.heaps;
import mukulrathi.customexceptions.UnderflowException;
import mukulrathi.customexceptions.ValueNotPresentException;
import mukulrathi.datastructures.implementations.heaps.BinaryMinHeap;

import java.util.Collection;
import java.util.Comparator;

public class MinHeapUnitTests<T> extends BinaryMinHeap<T> {

    //constructors
    public MinHeapUnitTests(Comparator<? super T> comp) {
        super(comp);
    }

    public MinHeapUnitTests(Collection<T> list, Comparator<? super T> comp) {
        super(list, comp);
    }

    //check min-heap property not violated
    public boolean minHeapProperty() {
        for(int i=0; i<mHeap.size();i++){
            //if the node is greater than either of its children then min-heap property violated
            if(leftChild(i)<mHeap.size()&&mComp.compare(mHeap.get(i),mHeap.get(leftChild(i)))>0 ){
                return false;
            }
            if (rightChild(i)<mHeap.size()&&mComp.compare(mHeap.get(i),mHeap.get(rightChild(i)))>0){
                return false;
            }
        }
        return true; //no nodes violated property
    }


    public boolean indexCheck() {
        //check the indexOf hashmap has been kept up to do with the correct values
        for (int i = 0; i < mHeap.size(); i++) {
            if (!indexOf.containsKey(mHeap.get(i)) || indexOf.get(mHeap.get(i)) != i) { //if indexOf doesn't contain object
                //or it points to wrong index
                return false; //indexOf hashmap is not correct

            }
        }
        return true; //all indexOf value->index pairs match up
    }

    private boolean unitTestPass(){
        return minHeapProperty()&&indexCheck();
    }
    private String unitTestResult() {

        String results = "\n UNIT TESTS:  \n";
        results += "Min Heap Property satisfied? " + minHeapProperty() + "\n";
        results += "Indices match up? " + indexCheck() + "\n";
        return results;
    }


    @Override
    public void insert(T x){
        System.out.print((unitTestPass())?"": ("Pre: Insert error:" + unitTestResult()));
        super.insert(x);
        System.out.print((unitTestPass())?"": ("Post: Insert error:" + unitTestResult()));
    }

    @Override
    public void delete(T x) throws ValueNotPresentException {
        System.out.print((unitTestPass())?"": ("Pre: Delete error:" + unitTestResult()));
        super.delete(x);
        System.out.print((unitTestPass())?"": ("Post: Delete error:" + unitTestResult()));
    }
    @Override
    public T extractMin() throws UnderflowException {
        System.out.print((unitTestPass())?"": ("Pre: Extract-min error:" + unitTestResult()));
        T val = super.extractMin();
        System.out.print((unitTestPass())?"": ("Post: Extract-min error:" + unitTestResult()));

        return val;
    }
    @Override
    public void decreaseKey(T x) throws ValueNotPresentException {
        System.out.print((unitTestPass())?"": ("Pre: Decrease-key error:" + unitTestResult()));
        super.decreaseKey(x);
        System.out.print((unitTestPass())?"": ("Post: Decrease-key error:" + unitTestResult()));
    }


}