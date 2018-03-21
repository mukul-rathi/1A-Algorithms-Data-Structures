package mukulrathi.algorithms.sorting;

import java.util.Scanner;

public class Tester {
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
