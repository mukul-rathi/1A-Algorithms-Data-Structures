package mukulrathi.algorithms.sorting;

public class MergeSort implements SortingAlgo {

    public int[] sort(int[] A) {
         mSort(A,0,A.length-1);
         return A;
    }

    private void mSort(int[] A,int start, int end) {
        if(start!=end){
            int mid = (start+end)/2;
            mSort(A, start, mid);
            mSort(A,mid+1,end);
            merge(A,start,mid,end);
        }
    }

    private static void merge(int[] A,int start,int mid,int end) {
        int[] temp = new int[mid - start + 1];
        for (int i = start; i <= mid; i++) {
            temp[i - start] = A[i];
        }
        int lPtr=0;
        int rPtr=mid+1;
        int ansPtr = start;
        while(lPtr<temp.length && rPtr<=end){
            if(temp[lPtr]<=A[rPtr]){
                A[ansPtr++] = temp[lPtr++];
            }
            else {
                A[ansPtr++] = A[rPtr++];
            }
        }
        while(lPtr<temp.length){
            A[ansPtr++] = temp[lPtr++];
        }
    }
}
