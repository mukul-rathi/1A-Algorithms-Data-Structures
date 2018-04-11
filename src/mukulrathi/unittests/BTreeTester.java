package mukulrathi.unittests;

import mukulrathi.customexceptions.KeyNotFoundException;
import mukulrathi.datastructures.implementations.trees.BTree;

import java.util.Scanner;

public class BTreeTester {


    public static void main(String[] args){
        BTree<Integer,Integer> testBtree = new BTreeUnitTests<Integer, Integer>(2);
        Scanner input = new Scanner(System.in);
        System.out.println("Test the B tree with integers!");
        System.out.println();
        String[] operations = input.nextLine().split(" ");
        while(!operations[0].equals("q")) {
            switch(operations[0]) {
                case "set":
                    int key = Integer.parseInt(operations[1]);
                    int val = Integer.parseInt(operations[2]);
                    testBtree.set(key,val);
                    break;
                case "delete":
                    key = Integer.parseInt(operations[1]);
                    try {
                        testBtree.delete(key);
                    } catch (KeyNotFoundException e) {
                        System.out.println("Error: The key was not in the tree.");
                    }
                    break;
                case "get":
                    key = Integer.parseInt(operations[1]);
                    try {
                        testBtree.get(key);
                    } catch (KeyNotFoundException e) {
                        System.out.println("Error: The key was not in the tree.");
                    }
                    break;
                default:
                    System.out.println("Error: Invalid operation");


            }
            System.out.println(testBtree);
            operations = input.nextLine().split(" ");
        }

    }
}


