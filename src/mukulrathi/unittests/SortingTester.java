package mukulrathi.unittests;

import mukulrathi.algorithms.sorting.MergeSort;
import mukulrathi.algorithms.sorting.SortingAlgo;

import java.util.Scanner;


/*This is a test class with a main method that can be used to test each of the
  sorting algorithms - this uses the Strategy Java Design Pattern.
*/
public class SortingTester {
    private static int[] test;
    private static SortingAlgo sorter;


    public static void main (String[] args){

        sorter = new MergeSort();

        Scanner input = new Scanner(System.in);
        System.out.println("Enter number of integers: ");
        test = new int[input.nextInt()];
        System.out.println("Enter integers to sort: ");
        for(int i=0; i<test.length;i++){
            test[i] = input.nextInt();
        }

        test = sorter.sort(test);

        System.out.println("The sorted array is: ");
        for(int i: test){
            System.out.print(i + " ");
        }

    }
}
