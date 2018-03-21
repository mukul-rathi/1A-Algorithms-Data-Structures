package mukulrathi.algorithms.sorting;

public class BinaryInsertionSort implements SortingAlgo{
/*
This is a modification of traditional insertion sort - instead of doing a linear
search when identifying the lowest element greater than key, we use a binary search
to reduce number of comparisons.
 */
    @Override
    public int[] sort(int[] A){
        //Initially A[0] sorted (since 1 element so by definition)
        for(int i=1;i<A.length;i++){
            /*Pre-condition: A[0..i-1] is a sorted array.
             */
            int key = A[i];
            //binary search to find lowest element greater than the key
            int low = 0;
            int high = i;
            while(low!=high){
                if(A[(low+high)/2]> key){
                    high = (low+high)/2;
                }
                else{
                    low = (low+high)/2 +1;
                }

            }
            for (int j=i; j>low;j--){
                A[j] = A[j-1];
            }
            A[low] = key;
        /*Post-condition: A[0..i] is a sorted array.
             */
        }
        //A[0....n-1] sorted i.e. A is sorted.
        return A;
    }
}
