package mukulrathi.datastructures.implementations.trees;

import mukulrathi.datastructures.abstractdatatypes.sets.OrderedSet;
/*
This class is an example implementation of the Ordered Set ADT using a binary search tree.
 */

public class BinarySearchTree<T> implements OrderedSet<T> {
    private TreeNode<T> root;

    //each node in the tree contains a key (by which it is ordered)
    //and pointers to its parent and left and right children.
    private class TreeNode<T>{
        public T key;
        public TreeNode<T> parent;
        public TreeNode<T> leftChild;
        public TreeNode<T> rightChild;

        public TreeNode(T x){
            key = x;
        }
    }

    public BinarySearchTree(){ //create empty tree

    }

    public BinarySearchTree(T x){ //create tree with a root node
        root = new TreeNode<T>(x);
    }






}
