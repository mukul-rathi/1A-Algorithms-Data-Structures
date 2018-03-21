package mukulrathi.algorithms.sorting;

public class RadixSort implements SortingAlgo {
    private int mRadix;
    private CountingSort sorter;
    public RadixSort(){
        mRadix=10;
        sorter = new CountingSort(mRadix);
    }
    public RadixSort(int radix){
        mRadix=radix;
        sorter = new CountingSort(mRadix);
    }
    @Override
    public int[] sort(int[] A) {
        int mostSignifDigit = (int) Math.ceil(31*Math.log(2.0)/Math.log(mRadix));
        int place =1;
        for(int i=0; i<mostSignifDigit; i++){
            A = sorter.sortForRadix(A,place);
            place*=mRadix;
        }
        return A;
    }
}
