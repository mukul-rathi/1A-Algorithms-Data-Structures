package mukulrathi.algorithms.sorting;

import java.util.Random;

public class QuickSort implements SortingAlgo {
    private boolean mRandomised; //true => randomised pivot, false => pivot = left-most element

    public QuickSort(){
        mRandomised = false;
    }
    public QuickSort(boolean randomised){
        mRandomised = randomised;
    }


    private int partition(int[] A, int p, int q){ //note we are considering A[p...q] inclusive
        if(mRandomised){ //we choose a random element and swap it with the left-most element
            //so code from here on = same for both versions of quicksort.
            Random r = new Random();
            int randomPivot = r.nextInt(q-p) + p;
            int temp = A[randomPivot];
            A[randomPivot] = A[p];
            A[p] = temp;
        }

        int pivot = A[p]; //pivot = left-most element
        int boundary = p; //all elements to the left and including the boundary element are <=pivot
        for(int j=p+1;j<=q;j++){
            if(A[j]<=pivot){
                boundary++; //increase boundary by one
                //swap the element so in the boundary
                int temp = A[j];
                A[j] = A[boundary];
                A[boundary] = temp;
            }
        }
        //currently we have all elements A[p+1...boundary] <= pivot, with A[p] = pivot

        //swap pivot and last element to left of boundary so A[p...boundary-1] <= pivot
        //A[boundary]=pivot and A[boundary+1...q] >=pivot
        A[p] = A[boundary];
        A[boundary] = pivot;
        return boundary; //index of pivot


    }

    private void sortHelper(int[] A, int p, int q){ //note we are considering A[p...q] inclusive
        //if p==q then base case: size of array = 1 so sorted
        if(p<q){ //i.e size of Array>1
            //partition the array into two sub-arrays left and right of pivot and
            //recursively sort those.
            int r = partition(A, p, q);  //position of pivot
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
