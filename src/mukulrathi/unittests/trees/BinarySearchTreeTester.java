package mukulrathi.unittests.trees;

import mukulrathi.customexceptions.KeyNotFoundException;
import mukulrathi.customexceptions.UnderflowException;
import mukulrathi.datastructures.implementations.trees.BinarySearchTree;

import java.util.Scanner;

public class BinarySearchTreeTester {


    public static void main(String[] args){
        BinarySearchTree<Integer,Integer> testBST = new BinarySearchTree<Integer,Integer>();
        Scanner input = new Scanner(System.in);
        System.out.println("Test the BST with integers!");
        System.out.println();
        String[] operations = input.nextLine().split(" ");
        while(!operations[0].equals("q")) {
            switch(operations[0]) {
                case "insert":
                    int val = Integer.parseInt(operations[1]);
                    testBST.insert(val);
                    break;
                case "delete":
                    val = Integer.parseInt(operations[1]);
                    try {
                        testBST.delete(val);
                    } catch (KeyNotFoundException e) {
                        System.out.println("Error: The key was not in the tree.");
                    }
                    break;
                case "isEmpty":
                        System.out.println("Tree empty? : " + testBST.isEmpty());
                        break;
                case "hasKey":
                        val = Integer.parseInt(operations[1]);
                        System.out.println("Key present? : " + testBST.hasKey(val));
                        break;
                case "chooseAny":
                    try {
                        System.out.println("A key from the tree is: " + testBST.chooseAny());
                    } catch (KeyNotFoundException e) {
                        System.out.println("Error: The tree is empty");
                    }
                    break;
                case "size":
                    System.out.println("The size of tree is: " + testBST.size());
                    break;
                case "min":
                    try {
                        System.out.println("The min element is: " + testBST.min());
                    } catch (UnderflowException e) {
                        System.out.println("Error: The tree is empty");
                    }
                    break;
                case "max":
                    try {
                        System.out.println("The max element is: " + testBST.max());
                    } catch (UnderflowException e) {
                        System.out.println("Error: The tree is empty");
                    }
                    break;
                case "predecessor":
                    val = Integer.parseInt(operations[1]);
                    try {
                        System.out.println("The predecessor of " + val + " is: " + testBST.predecessor(val));
                    } catch (KeyNotFoundException e) {
                        System.out.println("Error: the key does not have a predecessor");
                    }
                    break;
                case "successor":
                    val = Integer.parseInt(operations[1]);
                    try {
                        System.out.println("The successor of " + val + " is: " + testBST.successor(val));
                    } catch (KeyNotFoundException e) {
                        System.out.println("Error: the key does not have a successor");
                    }
                    break;
                default:
                    System.out.println("Error: Invalid operation");


            }
            System.out.println(testBST);
            operations = input.nextLine().split(" ");
        }

    }
}


