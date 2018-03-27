package mukulrathi.unittests;

import mukulrathi.customexceptions.KeyNotFoundException;
import mukulrathi.customexceptions.UnderflowException;
import mukulrathi.datastructures.implementations.trees.BinarySearchTree;
import mukulrathi.datastructures.implementations.trees.RedBlackTree;

import java.util.Scanner;

public class RedBlackTreeTester {


    public static void main(String[] args){
        RedBlackTree<Integer,Integer> testRBtree = new RedBlackTree<Integer, Integer>();
        Scanner input = new Scanner(System.in);
        System.out.println("Test the Red-black tree with integers!");
        System.out.println();
        String[] operations = input.nextLine().split(" ");
        while(!operations[0].equals("q")) {
            switch(operations[0]) {
                case "insert":
                    int key = Integer.parseInt(operations[1]);
                    int val = Integer.parseInt(operations[2]);
                    testRBtree.insert(key,val);
                    break;
                case "delete":
                    key = Integer.parseInt(operations[1]);
                    try {
                        testRBtree.delete(key);
                    } catch (KeyNotFoundException e) {
                        System.out.println("Error: The key was not in the tree.");
                    }
                    break;
                case "set":
                     key = Integer.parseInt(operations[1]);
                     val = Integer.parseInt(operations[2]);
                    testRBtree.set(key,val);
                    break;
                case "isEmpty":
                        System.out.println("Tree empty? : " + testRBtree.isEmpty());
                        break;
                case "hasKey":
                        key = Integer.parseInt(operations[1]);
                        System.out.println("Key present? : " + testRBtree.hasKey(key));
                        break;
                case "chooseAny":
                    try {
                        System.out.println("A key from the tree is: " + testRBtree.chooseAny());
                    } catch (KeyNotFoundException e) {
                        System.out.println("Error: The tree is empty");
                    }
                    break;
                case "size":
                    System.out.println("The size of tree is: " + testRBtree.size());
                    break;
                case "min":
                    try {
                        System.out.println("The min element is: " + testRBtree.min());
                    } catch (UnderflowException e) {
                        System.out.println("Error: The tree is empty");
                    }
                    break;
                case "max":
                    try {
                        System.out.println("The max element is: " + testRBtree.max());
                    } catch (UnderflowException e) {
                        System.out.println("Error: The tree is empty");
                    }
                    break;
                case "predecessor":
                    key = Integer.parseInt(operations[1]);
                    try {
                        System.out.println("The predecessor of " + key + " is: " + testRBtree.predecessor(key));
                    } catch (KeyNotFoundException e) {
                        System.out.println("Error: the key does not have a predecessor");
                    }
                    break;
                case "successor":
                    key = Integer.parseInt(operations[1]);
                    try {
                        System.out.println("The successor of " + key + " is: " + testRBtree.successor(key));
                    } catch (KeyNotFoundException e) {
                        System.out.println("Error: the key does not have a successor");
                    }
                    break;
                default:
                    System.out.println("Error: Invalid operation");


            }
            System.out.println(testRBtree);
            operations = input.nextLine().split(" ");
        }

    }
}


