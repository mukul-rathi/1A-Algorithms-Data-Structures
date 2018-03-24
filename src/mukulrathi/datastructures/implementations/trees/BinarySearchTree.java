package mukulrathi.datastructures.implementations.trees;

import mukulrathi.customexceptions.KeyNotFoundException;
import mukulrathi.customexceptions.UnderflowException;
import mukulrathi.datastructures.abstractdatatypes.sets.DynamicSet;
import mukulrathi.datastructures.abstractdatatypes.sets.OrderedSet;
import mukulrathi.datastructures.abstractdatatypes.sets.StaticSet;
/*
This class is an example implementation of the Ordered Set ADT using a binary search tree.
 */

public class BinarySearchTree<K extends Comparable<K>> implements OrderedSet<K>{
    //we use bounded generic type (T extends__) since we want to compare ordering of key
    private TreeNode<K> mRoot;

    //each node in the tree contains a key (by which it is ordered)
    //and pointers to its parent and left and right children.
    //Note BST property : key(leftChild)<=key(node)<=key(rightChild)
    private class TreeNode<K>{
        public K key;
        public TreeNode<K> parent;
        public TreeNode<K> leftChild;
        public TreeNode<K> rightChild;

        public TreeNode(K x){
            key = x;
        }
    }

    public BinarySearchTree(){ //create empty tree

    }

    public BinarySearchTree(K x){ //create tree with a root node
        mRoot = new TreeNode<K>(x);
    }

    //create a shallow copy of the tree with given node as root
    public BinarySearchTree(TreeNode<K> root){
        mRoot = root;
    }
    //internal method to return the TreeNode with the particular key
    //useful since we can then traverse the tree from that point
    private TreeNode<K> nodeWithKey(K k) throws KeyNotFoundException {

        TreeNode<K> currentNode = mRoot;
        while(currentNode!=null) {
            //compare with the current node's key
            if(k.compareTo(currentNode.key)==0){
                return currentNode;
            }

            else if(k.compareTo(currentNode.key)<0){ //go down left subtree
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
    public K min() throws UnderflowException {
        if (isEmpty()) throw new UnderflowException(); //no min since tree is empty
        TreeNode<K> currentNode = mRoot;
        //keep following left child until we reach leaf
        while(currentNode.leftChild!=null){
            currentNode=currentNode.leftChild;
        }
        return currentNode.key;
    }

    @Override
    public K max() throws UnderflowException {
        if (isEmpty()) throw new UnderflowException(); //no max since tree is empty
        TreeNode<K> currentNode = mRoot;
        //keep following right child until we reach leaf
        while(currentNode.rightChild!=null){
            currentNode=currentNode.rightChild;
        }
        return currentNode.key;
    }

    @Override
    public K predecessor(K x) throws KeyNotFoundException {
        return null;

    }

    @Override
    public K successor(K x) {
        return null;
    }

    @Override
    public void insert(K x) {

    }

    @Override
    public void delete(K x) {

    }

    @Override
    public DynamicSet<K> union(StaticSet<K> s) {
        return null;
    }

    @Override
    public DynamicSet<K> intersection(StaticSet<K> s) {
        return null;
    }

    @Override
    public DynamicSet<K> difference(StaticSet<K> s) {
        return null;
    }

    @Override
    public boolean subset(StaticSet<K> s) {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return (mRoot==null);
    }

    @Override
    public boolean hasKey(K x) {
        return false;
    }

    @Override
    public K chooseAny() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }









}
