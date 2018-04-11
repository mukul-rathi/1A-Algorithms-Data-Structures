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
        for(int i=1;i<=20; i++){
            testBtree.set(i,i+10);
        }
        System.out.println(testBtree);

        String[] operations = input.nextLine().split(" ");
        while(!operations[0].equals("q")) {
            switch(operations[0]) {
                case "set":
                    int key = Integer.parseInt(operations[1]);
                    if(operations.length==2){ //this is so program doesn't terminate if I accidentally forget to type val
                        System.out.println("Error: Invalid operation");
                        break;
                    }
                    else {
                        int val = Integer.parseInt(operations[2]);
                        testBtree.set(key, val);
                        break;
                    }
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
                        System.out.println("Value is: "+ testBtree.get(key));
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


