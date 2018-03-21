package mukulrathi.algorithms.sorting;

public class HeapSort implements SortingAlgo{

    private int mSize;
    private int[] mHeap;

    public HeapSort(){
    }

    public HeapSort(int[] A){
        mHeap = A;
        mSize = mHeap.length;

        for(int i=mSize/2;i>=0;i--){
            maxHeapify(i);
        }

    }

    private void maxHeapify(int i) {
        int largest;
        if (leftChild(i)<= (mSize-1) && mHeap[leftChild(i)]>mHeap[i]){
            largest = leftChild(i);
        }
        else{
            largest=i;
        }
        if(rightChild(i) <= (mSize-1) && mHeap[rightChild(i)]>mHeap[largest]){
            largest = rightChild(i);
        }

        if(largest!=i){
            int temp = mHeap[i];
            mHeap[i] = mHeap[largest];
            mHeap[largest] = temp;
            maxHeapify(largest);
        }

    }

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
        if (mSize==0) throw new UnderflowException();
        int max = mHeap[0];
        mHeap[0] = mHeap[mSize-- -1];
        maxHeapify(0);
        return max;

    }

    public void insert(char ch) {

        if (mSize == mHeap.length){
            int[] newHeap = new int[2*mSize];
            for(int i=0; i<mSize;i++){
                newHeap[i] = mHeap[i];
            }
            mHeap = newHeap;
        }
        mHeap[mSize] = -2147483648; //min value of an int
        increaseKey(mSize,Character.toLowerCase(ch));
        mSize++;


    }

    private void increaseKey(int i, char key) {
        if (key < mHeap[i]) throw new AssertionError();
        mHeap[i] = key;
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
        for (int i=mSize-1; i>=1;--i){
            int temp = mHeap[0];
            mHeap[0] = mHeap[i];
            mHeap[i] = temp;
            mSize--;
            maxHeapify(0);
        }

    }

    @Override
    public int[] sort(int[] A) {
        HeapSort sorted = new HeapSort(A);
        sorted.sort();
        return sorted.mHeap;
    }


}
