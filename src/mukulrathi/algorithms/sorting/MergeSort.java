package mukulrathi.algorithms.sorting;

public class MergeSort implements SortingAlgo {

    public int[] sort(int[] A) {
         mSort(A,0,A.length-1);
         return A;
    }
    private void mSort(int[] A,int start, int end) {
        //base case: if array size = 1 then already sorted

        if(start!=end){ //i.e. if size of array >1
            //recursively sort the left and right halves of the array and then merge
            int mid = (start+end)/2;
            mSort(A, start, mid);
            mSort(A,mid+1,end);
            merge(A,start,mid,end);
        }

    }

    private static void merge(int[] A,int start,int mid,int end) {
        //create an auxiliary array of size n/2 to store Left half.
        int[] temp = new int[mid - start + 1];
        for (int i = start; i <= mid; i++) {
            temp[i - start] = A[i];
        }
        //use pointers to keep track of which elements in the
        //Left and Right subarray we are comparing, and which
        //cell in the answer we are writing to.
        int lPtr=0;
        int rPtr=mid+1; //Right array = A[mid+1...end]
        int ansPtr = start;
        while(lPtr<temp.length && rPtr<=end){ //i.e whilst we haven't reached the end of
            //one of the two subarrays

            //compare the two elements in the L and R subarrays and choose the smaller one
            //and move the respective pointer along by one.
            if(temp[lPtr]<=A[rPtr]){
                A[ansPtr++] = temp[lPtr++];
            }
            else {
                A[ansPtr++] = A[rPtr++];
            }
        }
        //if we have reached end of one array, we need to copy across remainder of the
        //other array in sorted order - for the right subarray we don't need to do this
        //since R is stored in original array, but L is in aux. array so we need to
        //copy it.
        while(lPtr<temp.length){
            A[ansPtr++] = temp[lPtr++];
        }
    }
}
