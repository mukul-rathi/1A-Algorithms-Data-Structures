package mukulrathi.algorithms.sorting;

public class SelectionSort implements SortingAlgo {

    @Override
    public int[] sort(int[] A) {
        //each loop we find the smallest number out of the remaining numbers
        for (int i = 0; i < A.length; i++) {
            //Pre-condition: A[0...i-1] is sorted and A[i-1]<= all of A[i...n-1]


            //go through A[i..n-1] to  find the next-smallest number
            int min = i;
            for(int j=i+1;j<A.length;j++){
                if (A[j]<A[min]){
                    min =j;
                }
            }

            //swap min number with A[i]
            int temp = A[i];
            A[i] = A[min];
            A[min] = temp;
            //so Post-condition: A[0....i] is sorted nad A[i]<=all of A[i+1...n-1]
        }
        //A[0...n-1] is sorted => A is sorted.
        return A;
    }
}
