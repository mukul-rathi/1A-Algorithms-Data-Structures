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
    //we use bounded generic type (K extends__) since we want to compare ordering of key
    private TreeNode<K> mRoot;

    //each node in the tree contains a key (by which it is ordered)
    //and pointers to its parent and left and right children.
    //Note BST property : key(leftChild)<=key(node)<=key(rightChild)
    private class TreeNode<K>{
        public K key;
        public TreeNode<K> parent;
        public TreeNode<K> leftChild;
        public TreeNode<K> rightChild;

        public TreeNode(K k){
            key = k;
        }
    }

    public BinarySearchTree(){ //create empty tree

    }

    public BinarySearchTree(K k){ //create tree with a root node
        mRoot = new TreeNode<K>(k);
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
    public K predecessor(K k) throws KeyNotFoundException {

        TreeNode<K> currentNode = nodeWithKey(k); //this will throw KeyNotFoundException if k
                                                // not in tree.

        try {
            if(this.min().compareTo(k)==0){//check if k is min element of tree
                //if so, we have violated a pre-condition of predecessor()
                throw new KeyNotFoundException(); // so no predecessor
            }
        }
        catch (UnderflowException e) {//we will not execute this block because the
            //nodeWithKey method would have thrown a KeyNotFoundException
            //if the tree was empty, but we will still handle the exception
            e.printStackTrace();
        }


        //now we know there is a valid predecessor, let us find it.
        K predK= null;
        //case 1: node with key k has a left subtree, in which case get max
        // element of left subtree
        if(currentNode.leftChild!=null){
            try {
                predK= (new BinarySearchTree<K>(currentNode.leftChild)).max();
            } catch (UnderflowException e) { //this is not going to happen because
                //we know currentNode.leftChild!=null, but need to handle it anyway.
                e.printStackTrace();
            }
        }

        //case 2: an ancestor of the node is k's predecessor. so traverse with sketch below:
        // pred
        //   \
        //   /
        //  /
        // /
        //k
        // we need to go up the tree until key of ancestor(node)< k
        //this is the case since k = min element of pred's right subtree, i.e k is pred's successor
        else{
            while(currentNode.parent.leftChild==currentNode) {
                currentNode = currentNode.parent;
            }
            predK = currentNode.parent.key;
        }
        return predK;

    }

    @Override
    public K successor(K k) throws KeyNotFoundException {
        //this is the mirror image of predecessor


        TreeNode<K> currentNode = nodeWithKey(k); //this will throw KeyNotFoundException if k
        // not in tree.

        try {
            if(this.max().compareTo(k)==0){//check if k is max element of tree
                //if so, we have violated a pre-condition of successor()
                throw new KeyNotFoundException(); // so no successor
            }
        }
        catch (UnderflowException e) {//we will not execute this block because the
            //nodeWithKey method would have thrown a KeyNotFoundException
            //if the tree was empty, but we will still handle the exception
            e.printStackTrace();
        }


        //now we know there is a valid successor, let us find it.
        K succK= null;
        //case 1: node with key k has a right subtree, in which case get min
        // element of right subtree
        if(currentNode.rightChild!=null){
            try {
                succK= (new BinarySearchTree<K>(currentNode.rightChild)).min();
            } catch (UnderflowException e) { //this is not going to happen because
                //we know currentNode.leftChild!=null, but need to handle it anyway.
                e.printStackTrace();
            }
        }

        //case 2: an ancestor of the node is k's successor. so traverse with sketch below:
        // succ
        //   /
        //   \
        //    \
        //     \
        //      k
        // we need to go up the tree until key of ancestor(node)> k
        //this is the case since k = max element of succ's left subtree, i.e k is succ's predecessor
        else{
            while(currentNode.parent.rightChild==currentNode) {
                currentNode = currentNode.parent;
            }
            succK = currentNode.parent.key;
        }
        return succK;

    }

    @Override
    public void insert(K k) {

    }

    @Override
    public void delete(K k) {

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
    public boolean hasKey(K k) {
        //NB rather than copying nodeWithKey code, we could have reused it, however we would
        //have had to handle the exception and this is slow.
        //Also a key not being present when checking is not an exceptional circumstance, so
        //we should not use exceptions as control flow.

        TreeNode<K> currentNode = mRoot;
        while(currentNode!=null) {
            //compare with the current node's key
            if(k.compareTo(currentNode.key)==0){
                return true;
            }

            else if(k.compareTo(currentNode.key)<0){ //go down left subtree
                currentNode=currentNode.leftChild;
            }
            else{ //k must be in right subtree if it is in tree
                currentNode=currentNode.rightChild;

            }

        }
        //currentNode==null i.e. we have reached a leaf (NIL) node, so key is not there
        return false;
    }

    @Override
    public K chooseAny() {
        return mRoot.key; // return the root's key for simplicity's sake

    }

    @Override
    public int size() {
        int numOfNodes = 0;
        if(!isEmpty()){
            numOfNodes++; //count root node
            //recursively count the left and right subtrees if they exist
            if(mRoot.leftChild!=null){
                numOfNodes+= (new BinarySearchTree<K>(mRoot.leftChild)).size();
            }
            if(mRoot.rightChild!=null){
                numOfNodes+= (new BinarySearchTree<K>(mRoot.rightChild)).size();
            }
        }
        return numOfNodes;
    }









}
