package mukulrathi.algorithms.sorting;

import mukulrathi.customexceptions.UnderflowException;

/*
We implement heap-sort using an underlying max-heap data structure
 */
public class HeapSort implements SortingAlgo{

    private int mSize;
    private int[] mHeap; //the max-heap is stored in an array

    public HeapSort(){
    }

    public HeapSort(int[] A){
        mHeap = A;
        mSize = mHeap.length;
        //construct a max-heap by repeatedly resolving max-heap violations as you go up the heap
        //NB A[mSize2+1...mSize-1] are leaves so no need to run max-heapify
        // A[0] = root so highest level of heap
        for(int i=mSize/2;i>=0;i--){
            maxHeapify(i);
        }

    }
    //Max heap property = value of parent >= (values of its children)
    //Max-heapify checks if property violated for subtree rooted at node, and if so recursively fixes it.
    private void maxHeapify(int i) {
        //determine largest of the parent, left and right child
        int largest;
        if (leftChild(i)<= (mSize-1) && mHeap[leftChild(i)]>mHeap[i]){ //check if leftChild exists, if so compare
            largest = leftChild(i);
        }
        else{
            largest=i;
        }
        if(rightChild(i) <= (mSize-1) && mHeap[rightChild(i)]>mHeap[largest]){ //ditto with rightChild
            largest = rightChild(i);
        }
        //if parent isn't largest we have a max-heap property violation
        if(largest!=i){
            //swap parent with largest of the two children
            int temp = mHeap[i];
            mHeap[i] = mHeap[largest];
            mHeap[largest] = temp;
            //now we are unsure if the subtree rooted at the largest child satisfies max-heap property
            //so we recurse.
            maxHeapify(largest);
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


    public int getMax() throws UnderflowException {
        if (mSize==0) throw new UnderflowException(); //heap is empty

        //swap max element (root) with last element and reduce size by 1 so effectively removes element
        int max = mHeap[0];
        mHeap[0] = mHeap[mSize-- -1];
        //after swap need to maintain max-heap property
        maxHeapify(0);
        return max;

    }

    public void insert(int val) {
        //we double size of array if array is full -> leads to O(1) amortized insert
        if (mSize == mHeap.length){
            int[] newHeap = new int[2*mSize];
            for(int i=0; i<mSize;i++){
                newHeap[i] = mHeap[i];
            }
            mHeap = newHeap;
        }
        //we put the min value (-infinity) at end of tree
        mHeap[mSize] = -Integer.MIN_VALUE;
        increaseKey(mSize,val); //set last node to the val, and bubble it up to the right position in heap
        mSize++;


    }

    private void increaseKey(int i, int key) {
        if (key < mHeap[i]) throw new AssertionError(); //we want key>=the original value
        mHeap[i] = key;
        //check if the value parent of the node < node's value, if so swap them and go up a level and
        //repeat
        while(i>0 && mHeap[parent(i)]<mHeap[i]){
            int temp = mHeap[i];
            mHeap[i] = mHeap[parent(i)];
            mHeap[parent(i)] = temp;
            i=parent(i);
        }

    }

    public int getLength() {
        return mSize;
    }

    public void sort(){
        //keep putting the next largest value at end of array - so build up list from largest-> smallest
        for (int i=mSize-1; i>=1;--i){
            //put root (max value at end of heap array)
            int temp = mHeap[0];
            mHeap[0] = mHeap[i];
            mHeap[i] = temp;
            mSize--;
            //restore max-heap property to find out next largest value
            maxHeapify(0);
        }

    }

    @Override
    public int[] sort(int[] A) {
        //create a max-heap
        HeapSort sorted = new HeapSort(A);
        //run heapsort on heap
        sorted.sort();
        //return the underlying array (now sorted)
        return sorted.mHeap;
    }


}
