package mukulrathi.unittests.heaps;

import mukulrathi.customexceptions.UnderflowException;
import mukulrathi.customexceptions.ValueNotPresentException;
import mukulrathi.datastructures.implementations.heaps.BinomialHeap;

import java.util.Scanner;

public class BinomialHeapTester {

    public static void main(String[] args) {
        BinomialHeap<Integer> testMinHeap = new BinomialHeapUnitTests<Integer>((x, y) -> (x - y)); //lambda function to sort integers
        Scanner input = new Scanner(System.in);
        System.out.println("Test the binomial heap with integers!");
        System.out.println();
        String[] operations = input.nextLine().split(" ");
        while (!operations[0].equals("q")) {
            switch (operations[0]) {
                case "insert":
                    int val = Integer.parseInt(operations[1]);
                    testMinHeap.insert(val);
                    break;
                case "delete":
                    val = Integer.parseInt(operations[1]);
                    try {
                        testMinHeap.delete(val);
                    } catch (ValueNotPresentException e) {
                        System.out.println("Error: The key was not in the heap.");
                    }
                    break;
                case "isEmpty":
                    System.out.println("Heap empty? : " + testMinHeap.isEmpty());
                    break;
                case "extract-min":
                    try {
                        System.out.println("The min element is: " + testMinHeap.extractMin());
                    } catch (UnderflowException e) {
                        System.out.println("Error: The heap is empty");
                    }
                    break;
                case "first":
                    try {
                        System.out.println("The first element is: " + testMinHeap.first());
                    } catch (UnderflowException e) {
                        System.out.println("Error: The heap is empty");
                    }
                    break;
                default:
                    System.out.println("Error: Invalid operation");


            }
            System.out.println(testMinHeap);
            operations = input.nextLine().split(" ");
        }
    }
}


