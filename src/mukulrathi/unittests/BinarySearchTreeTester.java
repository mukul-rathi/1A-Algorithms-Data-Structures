package mukulrathi.unittests;

import mukulrathi.datastructures.implementations.trees.BinarySearchTree;

import java.util.Scanner;

public class BinarySearchTreeTester {

    public static void main(String[] args){
        BinarySearchTree<Integer> testBST = new BinarySearchTree<Integer>();
        Scanner input = new Scanner(System.in);
        System.out.println("Enter integers to insert in BST:");
        while(input.hasNextInt()){
            testBST.insert(input.nextInt());
            System.out.println(testBST);
        }
    }
}
