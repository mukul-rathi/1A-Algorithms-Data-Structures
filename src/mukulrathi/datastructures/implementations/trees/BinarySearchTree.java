package mukulrathi.datastructures.implementations.trees;

import mukulrathi.customexceptions.KeyNotFoundException;
import mukulrathi.customexceptions.UnderflowException;
import mukulrathi.datastructures.abstractdatatypes.sets.DynamicSet;
import mukulrathi.datastructures.abstractdatatypes.sets.OrderedSet;
import mukulrathi.datastructures.abstractdatatypes.sets.StaticSet;
/*
This class is an example implementation of the Ordered Set ADT using a binary search tree.
 */

public class BinarySearchTree<T> implements OrderedSet<T> {
    private TreeNode<T> mRoot;

    //each node in the tree contains a key (by which it is ordered)
    //and pointers to its parent and left and right children.
    //Note BST property : key(leftChild)<=key(node)<=key(rightChild)
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
        mRoot = new TreeNode<T>(x);
    }

    //create a shallow copy of the tree with given node as root
    public BinarySearchTree(TreeNode<T> root){
        mRoot = root;
    }
    //internal method to return the TreeNode with the particular key
    //useful since we can then traverse the tree from that point
    private TreeNode<T> nodeWithKey(T k) throws KeyNotFoundException {

        TreeNode<T> currentNode = mRoot;
        while(currentNode!=null) {
            //compare with the current node's key
            if(k==currentNode.key){
                return currentNode;
            }

            else if(k < currentNode.key){ //go down left subtree
                currentNode=currentNode.leftChild;
            }
            else{ //k must be in right subtree if it is in tree
                currentNode=currentNode.rightChild;

            }

        }
        //currentNode==null i.e. we have reached a leaf (NIL) node, so key is not there
        throw new KeyNotFoundException();
    }

    @Override
    public T min() throws UnderflowException {
        if (isEmpty()) throw new UnderflowException(); //no min since tree is empty
        TreeNode<T> currentNode = mRoot;
        //keep following left child until we reach leaf
        while(currentNode.leftChild!=null){
            currentNode=currentNode.leftChild;
        }
        return currentNode.key;
    }

    @Override
    public T max() throws UnderflowException {
        if (isEmpty()) throw new UnderflowException(); //no max since tree is empty
        TreeNode<T> currentNode = mRoot;
        //keep following right child until we reach leaf
        while(currentNode.rightChild!=null){
            currentNode=currentNode.rightChild;
        }
        return currentNode.key;
    }

    @Override
    public T predecessor(T x) throws KeyNotFoundException {



    }

    @Override
    public T successor(T x) {
        return null;
    }

    @Override
    public void insert(T x) {

    }

    @Override
    public void delete(T x) {

    }

    @Override
    public DynamicSet<T> union(StaticSet<T> s) {
        return null;
    }

    @Override
    public DynamicSet<T> intersection(StaticSet<T> s) {
        return null;
    }

    @Override
    public DynamicSet<T> difference(StaticSet<T> s) {
        return null;
    }

    @Override
    public boolean subset(StaticSet<T> s) {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return (mRoot==null);
    }

    @Override
    public boolean hasKey(T x) {
        return false;
    }

    @Override
    public T chooseAny() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }









}
