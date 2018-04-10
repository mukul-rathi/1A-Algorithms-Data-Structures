package mukulrathi.unittests;

/*
    This class contains the methods to check if the Red-Black Tree invariants hold
 */

import mukulrathi.customexceptions.BlackHeightViolation;
import mukulrathi.datastructures.implementations.trees.RedBlackTree;

public class RedBlackTreeUnitTests<K extends Comparable<K>,V> extends RedBlackTree<K,V> {

    //Property 1: every node is either red or black - this is true since the colour of a node is a boolean value
    //false = red, true = black

    private boolean rootIsBlack() //Property 2: the root is always black - since no incoming (red) links
    {
        return (getColour((RBTreeNode<K,V>) mRoot)==BLACK);
    }

    //Property 3: All leaves are black and never contain key-value pairs - this is true since leaves are NULL nodes
    // and getColour returns black for NULL nodes

    private boolean redNodeChildrenAreBlack(RBTreeNode<K,V> node) //Property 4: if node is red, both its children are black
    {
        if(node==null){ //we've reached leaf - which is black so satisfies property
            return true;
        }
        else{
            if(getColour(node)==RED &&(getColour(getLeftChild(node))==RED || getColour(getRightChild(node))==RED)){ //property violated
                return false;
            }
            else{
                //either node is black or it is red and has two black children - so it satisfies property, so check if both children also satisfy property
                return (redNodeChildrenAreBlack(getRightChild(node))&&redNodeChildrenAreBlack(getLeftChild(node)));
            }
        }
    }

    private boolean blackHeightSame(RBTreeNode<K,V> node) // Property 5: for each node, all paths from that node to descendent leaves contain the same number of black nodes.
    {
        try {
            getBlackHeight((RBTreeNode<K, V>) mRoot);
            return true;
        } catch (BlackHeightViolation blackHeightViolation) {
            return false;
        }
    }

    private int getBlackHeight(RBTreeNode<K, V> node) throws BlackHeightViolation {
        if(node==null){
            return 0;
        }
        else{
            int leftChildBH = getBlackHeight(getLeftChild(node));
            int rightChildBH = getBlackHeight(getRightChild(node));
            if(getColour(getLeftChild(node))==BLACK){
                leftChildBH++;
            }
            if(getColour(getRightChild(node))==BLACK){
                rightChildBH++;
            }

            if(leftChildBH!=rightChildBH){
                throw new BlackHeightViolation();
            }
            else {
                return leftChildBH;
            }

        }
    }
    private boolean unitTestPass(){
        return rootIsBlack()&&redNodeChildrenAreBlack((RBTreeNode<K, V>) mRoot)&&blackHeightSame((RBTreeNode<K, V>) mRoot);
    }

    private String unitTestResult(){
        String results = "\n UNIT TESTS:  \n";
        results+="Root black? " + rootIsBlack() + "\n";
        results+="Red node children are black? " + redNodeChildrenAreBlack((RBTreeNode<K, V>) mRoot) + "\n";
        results+="Black height same? " + blackHeightSame((RBTreeNode<K, V>) mRoot) + "\n";
        return results;

    }

    @Override
    public String toString(){
        return super.toString()+ (unitTestPass()? "": unitTestResult());
    }

}
