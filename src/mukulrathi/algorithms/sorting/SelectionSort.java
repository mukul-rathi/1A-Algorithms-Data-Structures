package mukulrathi.algorithms.sorting;

public class SelectionSort implements SortingAlgo {

    @Override
    public int[] sort(int[] A) {
        for (int i = 0; i < A.length; i++) {
            int min = i;
            for(int j=i+1;j<A.length;j++){
                if (A[j]<A[min]){
                    min =j;
                }
            }
            int temp = A[i];
            A[i] = A[min];
            A[min] = temp;
        }
        return A;
    }
}
