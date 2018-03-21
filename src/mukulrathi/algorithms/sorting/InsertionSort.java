package mukulrathi.algorithms.sorting;

public class InsertionSort implements SortingAlgo {

    @Override
    public int[] sort(int[] A){
        //Initially A[0] sorted (since 1 element so by definition)
        for(int i=1;i<A.length;i++){
            /*Pre-condition: A[0..i-1] is a sorted array.
             */
            int key = A[i];
            int j = i-1;
            while (j>=0 && A[j]>=key){
                //keep shifting elements along to the right
                // until key>A[j] i.e. you've found its place in
                //sorted array.
                A[j+1] = A[j];
                j--;
            }
            A[j+1] = key;
            /*Post-condition: A[0..i] is a sorted array.
             */
        }
        //A[0....n-1] sorted i.e. A is sorted.
        return A;
    }
}
