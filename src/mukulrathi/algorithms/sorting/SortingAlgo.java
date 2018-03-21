package mukulrathi.algorithms.sorting;
/*
This is an interface that all sorting algorithms must implement
(for simplicity this is referring to integer sorting, though most of the algorithms
(i.e those except Radix and Counting sort)
could be modified to work with other types)
 */


public interface SortingAlgo {

    public int[] sort(int[] A);


}
