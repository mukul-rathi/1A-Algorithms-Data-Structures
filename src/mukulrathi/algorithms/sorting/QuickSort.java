package mukulrathi.algorithms.sorting;

import java.util.Random;

public class QuickSort implements SortingAlgo {
    private boolean mRandomised;

    public QuickSort(){
        mRandomised = false;
    }
    public QuickSort(boolean randomised){
        mRandomised = randomised;
    }


    private int partition(int[] A, int p, int q){
        if(mRandomised){
            Random r = new Random();
            int randomPivot = r.nextInt(q-p) + p;
            int temp = A[randomPivot];
            A[randomPivot] = A[p];
            A[p] = temp;
        }
        int pivot = A[p];
        int boundary = p;
        for(int j=p+1;j<=q;j++){
            if(A[j]<=pivot){
                boundary++;
                int temp = A[j];
                A[j] = A[boundary];
                A[boundary] = temp;
            }
        }
        A[p] = A[boundary];
        A[boundary] = pivot;
        return boundary;


    }


    private void sortHelper(int[] A, int p, int q){
        if(p<q){
            int r = partition(A, p, q);
            sortHelper(A,p,r-1);
            sortHelper(A,r+1,q);

        }
    }

    @Override
    public int[] sort(int[] A) {
        sortHelper(A,0,A.length-1);
        return A;
    }
}
