package mukulrathi.algorithms.sorting;

public class CountingSort implements SortingAlgo{
    private int mRange;


    public CountingSort(int k){
        mRange = k;
    }

    @Override
    public int[] sort(int[] A) {

        int[] count = new int[mRange+1];
        for(int i=0; i<A.length;i++){
            count[A[i]]++;
        }

        int answer[] = new int[A.length];

        for(int i=1;i<count.length;i++){
                count[i] += count[i-1];
        }
        for (int i=A.length-1;i>=0;i--){
                answer[count[A[i]]-- -1] = A[i]; //-1 since indexed from 0...A.length -1 not 1...A.length
        }

        return answer;
    }

    //for radix sort, we need to sort by digit
    public int[] sortForRadix(int[] A, int place) {

        int[] count = new int[mRange+1];
        for(int i=0; i<A.length;i++){
            int digit = (A[i]/place) % mRange;
            count[digit]++;
        }

        int answer[] = new int[A.length];

        for(int i=1;i<count.length;i++){
            count[i] += count[i-1];
        }
        for (int i=A.length-1;i>=0;i--){
            int digit = (A[i]/place) % mRange;
            answer[count[digit]-- -1] = A[i]; //-1 since indexed from 0...A.length -1 not 1...A.length
        }

        return answer;
    }
}
