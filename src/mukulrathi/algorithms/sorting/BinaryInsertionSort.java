package mukulrathi.algorithms.sorting;

public class BinaryInsertionSort implements SortingAlgo{

    @Override
    public int[] sort(int[] A){
        for(int i=1;i<A.length;i++){
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

        }
        return A;
    }
}
