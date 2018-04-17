package mukulrathi.datastructures.implementations.heaps;

import mukulrathi.customexceptions.UnderflowException;
import mukulrathi.customexceptions.ValueNotPresentException;
import mukulrathi.datastructures.abstractdatatypes.PriorityQueue;

import java.util.*;

public class MinHeap<T> extends PriorityQueue<T> {
    private ArrayList<T> mHeap = new ArrayList<T>();
    private HashMap<T,Integer> indexOf = new HashMap<T, Integer>(); //this is used for O(1) access to the
    // location of the item in the heap


    public MinHeap(Comparator<? super T> comp) {
        super(comp);
    }

    public MinHeap(Collection<T> list, Comparator<? super T> comp){
        super(comp);
        mHeap.addAll(list);
        for(int i=0;i<mHeap.size();i++){ //add the new values to indexOf for O(1) access
            indexOf.put(mHeap.get(i),i);
        }
        for(int i=mHeap.size()/2;i>=0;i--){ //construct min-heap from unordered ArrayList
            minHeapify(i);
        }

    }


    //helper functions to find parent, rightChild and leftChild of node i.
    private int rightChild(int i) {
        return 2*i+2;
    }

    private int leftChild(int i) {
        return 2*i+1;
    }

    private int parent(int i) {
        return (i-1)/2;
    }

    //helper function to swap elements at indices i and j in the heap
    private void swap(int i, int j) {
        //update indices in hashmap
        indexOf.put(mHeap.get(i),j);
        indexOf.put(mHeap.get(j),i);

        T temp = mHeap.get(i);
        mHeap.set(i,mHeap.get(j));
        mHeap.set(j,temp);


    }

    //min-heapify checks, and if necessary fixes any min-heap property violations for the sub-heap rooted at index i
    private void minHeapify(int i) {
        //determine smallest of the parent, left and right child
        int smallest;
        if (leftChild(i) <= (mHeap.size() - 1) && mComp.compare(mHeap.get(leftChild(i)), mHeap.get(i)) < 0) {//check if leftChild exists, if so compare
            smallest = leftChild(i);
        } else {
            smallest = i;
        }
        if (rightChild(i) <= (mHeap.size() - 1) && mComp.compare(mHeap.get(rightChild(i)), mHeap.get(i)) < 0) { //ditto with rightChild
            smallest = rightChild(i);
        }
        //if parent isn't smallest we have a min-heap property violation
        if (smallest != i) {
            //swap parent with smallest of the two children
            swap(i, smallest);
            //now we are unsure if the subtree rooted at the smallest child satisfies min-heap property
            //so we recurse.
            minHeapify(smallest);
        }
    }





    @Override
    public void insert(T x) {
        mHeap.add(x); //we add item to the array (i.e. effectively it has key=MAX_VALUE)
        try {
            decreaseKey(x); //let the value bubble up to the correct place in heap
        } catch (ValueNotPresentException e) { //this will not be thrown since we just added x, so must be there
            e.printStackTrace();
        }

    }

    @Override
    public T first() throws UnderflowException {
        if (mHeap.size()==0) throw new UnderflowException();
        else return mHeap.get(0);
    }

    @Override
    public T extractMin() throws UnderflowException {
        if (mHeap.size()==0) throw new UnderflowException();
        T minVal = mHeap.get(0);
        try {
            delete(minVal);
        } catch (ValueNotPresentException e) { //this won't be executed as we know minVal is in the heap
            e.printStackTrace();
        }
        return minVal;
    }

    @Override
    public void delete(T x) throws ValueNotPresentException {
        if(!indexOf.containsKey(x)) throw new ValueNotPresentException();
        int origIndex = indexOf.get(x);
        //swap with last element in heap
        swap(origIndex,mHeap.size()-1);
        //remove element (now at last index
        mHeap.remove(mHeap.size()-1);
        //we may now have min-heap violation at origIndex because of swap, so rectify this
        minHeapify(origIndex);
    }

    @Override
    public void decreaseKey(T x) throws ValueNotPresentException {
        if(!indexOf.containsKey(x)) throw new ValueNotPresentException();
        int i = indexOf.get(x); //find item's position in heap

        //now we may need to bubble the value up the tree, since if its parent key > its key that is a min-heap
        //property violation, so we swap them
        while(i>0 && mComp.compare(mHeap.get(parent(i)),mHeap.get(i))>0){
           swap(i,parent(i));
            i=parent(i);
        }

    }

    @Override
    public void merge(PriorityQueue<T> otherQ) { //this is O(m*lgn) since each of the m inserts is O(lg n)
        while(!otherQ.isEmpty()){
            try {
                insert(otherQ.extractMin());
            } catch (UnderflowException e) { //this won't happen since we've checked otherQ is not empty
                e.printStackTrace();
            }
        }

    }

    @Override
    public boolean isEmpty() { //true if heap empty, false otherwise
        return (mHeap.size()==0);
    }

    public void merge(MinHeap<T> otherQ) { //if the other queue is a min-heap, then we can merge in O(m+n) time
        int origSize = mHeap.size();
        mHeap.addAll(otherQ.mHeap);
        for(int i=origSize;i<mHeap.size();i++){ //add the new values to indexOf for O(1) access
            indexOf.put(mHeap.get(i),i);
        }
        for(int i=mHeap.size()/2;i>=0;i--){
            minHeapify(i);
        }
    }

    //finally, we want to print this for debugging purposes
    @Override
    public String toString() {
        String result = "\n Min Heap: \n";
        int numNodesAtLevel = 1; //max number of nodes in heap of this height - initially heap = 1 node
        int i=0;
        while(i<mHeap.size()){
            result+=mHeap.get(i++) + " "; //add current node and increment counter (so considers next node)
            if(i==numNodesAtLevel){ //we need to add another level to heap
                result+="\n";
                numNodesAtLevel= 2*numNodesAtLevel+1;//the number of nodes in a heap with an additional level
            }

        }
        return result;
    }
}
