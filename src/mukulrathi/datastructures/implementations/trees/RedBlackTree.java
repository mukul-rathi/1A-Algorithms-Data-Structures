package mukulrathi.datastructures.implementations.trees;

import mukulrathi.datastructures.abstractdatatypes.Dictionary;

public class RedBlackTree<K extends Comparable<K>,V> extends BinarySearchTree<K,V> implements Dictionary<K,V>{



    private class RBTreeNode<K,V> extends TreeNode<K,V>{
        public NodeColour colour;
        // Reset
        public static final String RESET = "\033[0m";  // Text Reset

        // Regular Colors
        public static final String BLACK = "\033[0;30m";   // BLACK
        public static final String RED = "\033[0;31m";     // RED


        public RBTreeNode(K k, V v) {
            super(k);
            value=v;
            colour=NodeColour.RED;

        }

        //this allows us to convert a standard TreeNode to an RBTreeNode
        public RBTreeNode(TreeNode<K,V> tNode){
            super(tNode.key);
            parent = tNode.parent;
            leftChild = tNode.leftChild;
            rightChild = tNode.rightChild;
        }

        @Override
        public String toString(){ //used to print out node
            String colourOfNode = (colour.equals(NodeColour.RED))? RED : BLACK;
            return colourOfNode + super.toString() + RESET;
        }
    }

    public RedBlackTree(){

    }
    public RedBlackTree(K k, V v){
        mRoot = new RBTreeNode<K,V>(k,v);
    }

    public void insert(K k, V v) {
        RBTreeNode<K,V> currentNode = (RBTreeNode<K, V>) mRoot;
        RBTreeNode<K,V> parentNode = null; //parent of current node
        while(currentNode!=null){
            if(k.compareTo(currentNode.key)==0){
                return; // Case 1: key is already present, we don't need to insert it
            }
            else if(k.compareTo(currentNode.key)<0){ //k< current node key so go down left subtree
                parentNode = currentNode;
                currentNode = (RBTreeNode<K, V>) currentNode.leftChild;
            }
            else{ // k> current node key so go down right subtree
                parentNode = currentNode;
                currentNode = (RBTreeNode<K, V>) currentNode.rightChild;
            }

        }
        //we've reached bottom of tree so insert as leaf
        RBTreeNode<K,V> newNode = new RBTreeNode<K,V>(k,v);

        if(parentNode==null){ // Case 2: tree is empty
            mRoot = newNode;
        }
        //Case 3:we have a parent node so we update newNode as the parent node's left or right child, depending on key
        else {
            newNode.parent = parentNode;
            if(k.compareTo(parentNode.key)<0){
                parentNode.leftChild = newNode;

            }
            else{
                parentNode.rightChild = newNode;

            }
        }

    }


    @Override
    public void set(K k, V v) {
    }

    @Override
    public V get(K k) {
        return null;
    }

}
