package mukulrathi.algorithms.sorting;


import mukulrathi.customexceptions.UnderflowException;
import mukulrathi.datastructures.implementations.heaps.MinHeap;
import mukulrathi.unittests.heaps.MinHeapUnitTests;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
We implement heap-sort using an underlying min-heap data structure
 */
public class HeapSort implements SortingAlgo{
    MinHeap<Integer> mHeap;

    @Override
    public int[] sort(int[] A) {
        List<Integer> aList = Arrays.stream(A).boxed().collect(Collectors.toList()); //convert int[] to List<Integer>
                                                                                //by converting array to stream, boxing int -> Integer
                                                                                //and collecting stream -> list
        mHeap= new MinHeap<Integer>(aList,(x, y)->(x-y)); //sort by min value
        int[] result = new int[A.length];
        int i=0;
        while(!mHeap.isEmpty()){
            try {
                result[i++] = mHeap.extractMin();
            } catch (UnderflowException e) { //this should not happen since heap is not empty
                e.printStackTrace();
            }
        }
        return result;
    }

}
