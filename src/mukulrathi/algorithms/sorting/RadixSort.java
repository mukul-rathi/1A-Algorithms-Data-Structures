package mukulrathi.algorithms.sorting;

public class RadixSort implements SortingAlgo {
    private int mRadix;
    private CountingSort sorter;

    public RadixSort(){
        mRadix=10; //base of radix
        sorter = new CountingSort(mRadix);
    }
    public RadixSort(int radix){
        mRadix=radix;
        sorter = new CountingSort(mRadix);
    }
    @Override
    public int[] sort(int[] A) {
        //determine the max number of digits
        int mostSignifDigit = (int) Math.ceil(31*Math.log(2.0)/Math.log(mRadix));
        int place =1; //least significant digit

        //sort the numbers by the least significant digit first, and repeat -
        //sorting by increasingly significant digits, up to the most Significant digit

        for(int i=0; i<mostSignifDigit; i++){
            A = sorter.sortForRadix(A,place); //we use Counting Sort to sort by digit
            place*=mRadix; //check next digit up
        }
        return A;
    }
}
