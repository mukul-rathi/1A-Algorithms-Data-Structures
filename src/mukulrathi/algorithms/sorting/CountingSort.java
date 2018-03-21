package mukulrathi.algorithms.sorting;

public class CountingSort implements SortingAlgo{
    private int mRange;


    public CountingSort(int k){
        mRange = k;
    }

    @Override
    public int[] sort(int[] A) { //this is O(n+k)

        int[] count = new int[mRange+1]; //keep track of the number of counts for each number in range 0...mRange inclusive
        for(int i=0; i<A.length;i++){
            count[A[i]]++; //increment the relevant number
        }

        //we now store cumulative counts i.e for cell i the no. of elements <=i.
        for(int i=1;i<count.length;i++){
                count[i] += count[i-1];
        }

        int answer[] = new int[A.length]; //sorted answer array

        //we traverse list backwards to maintain original ordering of equal elements (stable sort)
        for (int i=A.length-1;i>=0;i--){
                answer[count[A[i]]-- -1] = A[i]; // -1 since indexed from 0...A.length -1 not 1...A.length
                //NB we decrement counter for count[A[i]] so that the next element we encounter with same
                //value goes in place before it.
        }

        return answer;
    }

    //for radix sort, we need to sort by digit, so counting sort is modified slightly to
    //compare the digit, but sort the actual numbers.
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
