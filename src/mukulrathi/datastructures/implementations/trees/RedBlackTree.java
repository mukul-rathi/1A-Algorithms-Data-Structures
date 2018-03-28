package mukulrathi.datastructures.implementations.trees;

import mukulrathi.customexceptions.KeyNotFoundException;
import mukulrathi.datastructures.abstractdatatypes.Dictionary;

public class RedBlackTree<K extends Comparable<K>,V> extends BinarySearchTree<K,V> implements Dictionary<K,V>{
    private final static boolean RED = false;
    private final static boolean BLACK = true;


    private class RBTreeNode<K,V> extends TreeNode<K,V> {
        public boolean colour;
        // Reset
        private static final String printRESET = "\033[0m";  // Text Reset

        // Regular Colors
        private static final String printBLACK = "\033[0;30m";   // BLACK
        private static final String printRED = "\033[0;31m";     // RED


        public RBTreeNode(K k, V v) {
            super(k);
            value=v;
            colour=RED;

        }


        @Override
        public String toString(){ //used to print out node
            String colourOfNode = (colour==RED)? "RED": "BLACK";
            return "(K: " + key + " V: " + value +" "+colourOfNode+")";
        }
    }

    /*
    Constructors for RB Tree
     */

    public RedBlackTree(){

    }
    public RedBlackTree(K k, V v){

        mRoot = new RBTreeNode<K,V>(k,v);
    }



    /*
    Helper methods that do a NULL check within them, so prevent NullPointerExceptions
     */

    private boolean getColour(RBTreeNode<K,V> node){
        return (node==null)? BLACK : node.colour;
    }
    private RBTreeNode<K,V> getParent(RBTreeNode<K,V> node){
        return (node==null)? null: (RBTreeNode<K, V>) node.parent;
    }
    private RBTreeNode<K,V> getLeftChild(RBTreeNode<K,V> node){
        return (node==null)? null: (RBTreeNode<K, V>) node.leftChild;
    }
    private RBTreeNode<K,V> getRightChild(RBTreeNode<K,V> node){
        return (node==null)? null: (RBTreeNode<K, V>) node.rightChild;
    }
    private void setColour(RBTreeNode<K,V> node,boolean colour){
        if(node!=null){
            node.colour = colour;
        }
    }
    private void setParent(RBTreeNode<K,V> node,RBTreeNode<K,V> parent){
        if(node!=null){
            if(node==mRoot){ //corner case node was mRoot
                mRoot = parent;
            }
            node.parent = parent;
            //deal with corner case of node now being root
            if(parent==null){
                mRoot =node;
            }

        }
    }
    private void setLeftChild(RBTreeNode<K,V> node,RBTreeNode<K,V> leftChild){
        if(node!=null){
            node.leftChild = leftChild;
            setParent(leftChild,node);
        }
    }
    private void setRightChild(RBTreeNode<K,V> node,RBTreeNode<K,V> rightChild){
        if(node!=null){
            node.rightChild = rightChild;
            setParent(rightChild,node);
        }
    }

    private void shiftUpNode(RBTreeNode<K,V> oldNode,RBTreeNode<K,V> child){
        if(getParent(oldNode)==null){
            mRoot=child;
        }
        else if (getLeftChild(getParent(oldNode)) == oldNode) {
            setLeftChild(getParent(oldNode),child);
        } else {
            setRightChild(getParent(oldNode),child);

        }

    }

    @Override
    public void insert(K k){
        insert(k, null);
    }

    public void insert(K k, V v){
        //insertion is same as BST except we colour the inserted node red and fix any
        // red-black property violations afterwards
        RBTreeNode<K,V> currentNode = (RBTreeNode<K, V>) mRoot;
        RBTreeNode<K,V> parentNode = null; //parent of current node
        while(currentNode!=null){
            if(k.compareTo(currentNode.key)==0){
                currentNode.value = v;
                return;
            }
            else if(k.compareTo(currentNode.key)<0){ //k< current node key so go down left subtree
                parentNode = currentNode;
                currentNode = getLeftChild(currentNode);
            }
            else{ // k> current node key so go down right subtree
                parentNode = currentNode;
                currentNode = getRightChild(currentNode);
            }

        }
        //we've reached bottom of tree so insert as leaf

        RBTreeNode<K,V> newNode = new RBTreeNode<K,V>(k,v);

        if(parentNode==null){ // Case 2: tree is empty
            mRoot = newNode;
        }
        //Case 3: we have a parent node so we update newNode as the parent node's left or right child, depending on key
        else {
            setParent(newNode,parentNode);
            if(k.compareTo(parentNode.key)<0){
                setLeftChild(parentNode,newNode);

            }
            else{
                setRightChild(parentNode,newNode);
            }
        }

        RBInsertFixUp(newNode);

    }
    /*
    Note that inserting a red node in the tree doesn't affect the black height of the tree, so the only properties that
    could be violated are that the root node colour != black and that a red node has red children.
     */
    private void RBInsertFixUp(RBTreeNode<K, V> newNode) {
        while(getColour(getParent(newNode))==RED) { //RB violation since two adjacent red nodes

            if (getParent(newNode)==(getLeftChild(getParent(getParent(newNode))))) {
                //parent is a left child
                RBTreeNode<K, V> uncleNode = getRightChild(getParent(getParent(newNode)));
                if (getColour(uncleNode) == RED) {

                    //CASE 1: newNode's uncle is red


                    setColour(getParent(newNode), BLACK);
                    setColour(uncleNode, BLACK);
                    setColour(getParent(getParent(newNode)), RED);
                    newNode = getParent(getParent(newNode));
                }
                else {
                    //newNode's uncle is black

                    if (newNode==(getRightChild(getParent(newNode)))) {
                        //CASE 2: newNode's uncle = black and newNode = right child
                        //we left rotate to convert to case 3
                        newNode = getParent(newNode);
                        leftRotate(newNode);
                    }

                    //CASE 3: newNode's uncle = black and newNode is left-child
                    setColour(getParent(newNode), BLACK);
                    setColour(getParent(getParent(newNode)), RED);
                    rightRotate(getParent(getParent(newNode)));
                }


            }

            else {
                //identical by symmetry - parent is a right child so everything mirrored
                RBTreeNode<K, V> uncleNode = getLeftChild(getParent(getParent(newNode)));
                if (getColour(uncleNode) == RED) {

                    //CASE 1: newNode's uncle is red


                    setColour(getParent(newNode), BLACK);
                    setColour(uncleNode, BLACK);
                    setColour(getParent(getParent(newNode)), RED);
                    newNode = getParent(getParent(newNode));
                } else {


                    if (newNode==(getLeftChild(getParent(newNode)))) {
                        //CASE 2: newNode's uncle = black and newNode = left child
                        //we right rotate to convert to case 3
                        newNode = getParent(newNode);
                        rightRotate(newNode);
                    }

                    //CASE 3: newNode's uncle = black and newNode is right-child
                    setColour(getParent(newNode), BLACK);
                    setColour(getParent(getParent(newNode)), RED);
                    leftRotate(getParent(getParent(newNode)));
                }


                }

            }
            setColour((RBTreeNode<K, V>) mRoot,BLACK);
        }
    //Pre-condition: current node has a right subtree
    private void leftRotate(RBTreeNode<K, V> currentNode) {
        RBTreeNode<K,V> currentRightChild = getRightChild(currentNode);

        //make currentRightChild's left child  the currentNode's new right child
        setRightChild(currentNode,getLeftChild(currentRightChild));

        //make currentNode's parent the currentRightChild's parent
        if(getParent(currentNode)==null){
            mRoot = currentRightChild;
            setParent(currentRightChild,null);

        }
        else if(currentNode == (getLeftChild(getParent(currentNode)))){
            setLeftChild(getParent(currentNode),currentRightChild);
        }
        else {
            setRightChild(getParent(currentNode),currentRightChild);
        }
        if(getParent(currentNode)==null){
            mRoot = currentRightChild;
            setParent(currentRightChild,null);

        }

        //make currentNode currentRightChild's new left child
        setLeftChild(currentRightChild,currentNode);


    }

    //Pre-condition: current node has a left subtree
    //this is identical to leftRotate, just mirrored
    private void rightRotate(RBTreeNode<K, V> currentNode) {
        RBTreeNode<K,V> currentLeftChild = getLeftChild(currentNode);

        //make currentLeftChild's right child  the currentNode's new left child
        setLeftChild(currentNode,getRightChild(currentLeftChild));

        //make currentNode's parent the currentLeftChild's parent

        if(getParent(currentNode)==null){
            mRoot = currentLeftChild;
            setParent(currentLeftChild,null);

        }
        else if(currentNode==(getLeftChild(getParent(currentNode)))){
            setLeftChild(getParent(currentNode),currentLeftChild);
        }
        else {
            setRightChild(getParent(currentNode),currentLeftChild);
        }


        //make currentNode currentLeftChild's new right child
        setRightChild(currentLeftChild,currentNode);

    }


    @Override
    public void delete(K k) throws KeyNotFoundException {
        /*NB for the most part, this is identical to BST delete.
        however we cannot just call super.delete(k) because we have to keep track of the colours
        of the nodes throughout the deletion.
        */


        RBTreeNode<K,V> deleteNode = (RBTreeNode<K, V>) nodeWithKey(k);

        //during deletion one of the nodes will get removed, leaving a gap
        //in the tree that needs to be replaced. This is either the current node (in case 1) or the successor node
        // since it is swapped with the deletedNode ie. its position in the tree will be empty since it has moved to
        //fill the deletedNode's position
        // To ensure red-black properties still hold we need to keep track of its original colour.
        boolean origColour = getColour(deleteNode);
        RBTreeNode<K, V> replaceNode; //node that shifts into the position of the gap in tree.


        //this keeps track of the parent of the position we want the new node to move into
        RBTreeNode<K, V> parentReplaceNode = getParent(deleteNode);

        //Case 1: if node has at most one subtree, we just shift this up

        if (getLeftChild(deleteNode) == null || getRightChild(deleteNode) == null){

            if (getLeftChild(deleteNode) == null) {
                //we are shifting up right subtree
                replaceNode = getRightChild(deleteNode); //since it is being shifted up
            }
            else {
                //we are shifting up left subtree
                replaceNode = getLeftChild(deleteNode);

            }
            shiftUpNode(deleteNode,replaceNode);

        }
        //Case 2: node has both subtrees, so its successor lies in right subtree
        //we swap node with its successor, then delete it
        //For ease of implementation, we will do this by deleting successor from tree, then swapping it
        //into the original node's position.


        else{
            RBTreeNode<K,V> succNode = (RBTreeNode<K, V>) nodeWithKey(successor(k));
            //note successor has no left subtree so case 1 applies

            //successor node is the node that will leave gap so we need to know its colour
            origColour = getColour(succNode);
            replaceNode = getRightChild(succNode); //since case 1 the right subtree shifts up one to fill hole

            if(deleteNode==getParent(succNode)){
                //if deleteNode == parent(succnode) we have:
                /*
                    (deleteNode)
                    /        \
                  (X)       (succNode)
                                \
                                (replaceNode)
                 which goes to
                    (succNode)
                    /        \
                  (X)       (replaceNode)

                  i.e. no shifting needed,
                  */
                parentReplaceNode = succNode;
            } else{
                /* we only need subtree shifting if
                         (deleteNode)
                                \
                                (X)
                                /
                               /
                              ...
                             /
                         (succNode)   -this hole will need shifting
                                \
                                (replaceNode)
                 which would go to
                         (succNode)
                                \
                                (X)
                                /
                               /
                              ...
                             /
                         (replaceNode) i.e. replaceNode shift up

                 */
                shiftUpNode(succNode, replaceNode);
                //update right child to X
                setRightChild(succNode,getRightChild(deleteNode));


            }

            //next let us swap succNode into currentNode's position by updating pointers

            //parent pointer updated:
            shiftUpNode(deleteNode,succNode);

            //next update left child:
            setLeftChild(succNode,getLeftChild(deleteNode));
            setColour(succNode,getColour(deleteNode));



        }
        if(replaceNode==null && deleteNode==mRoot){ //only one element left (this is a bit of a special case)
            mRoot=null;
            return;
        }
        /*origColour = original colour of the node that left gap in tree
        if it was red then no violation after removed since black heights remain same and we clearly aren't going
        to have two adjacent red nodes since its parent can't have been red, and root remains black since the deleted
        node cannot have been the black root.
        If black, then removal affects black heights of subtree rooted at its ancestor, since one fewer black node
        also there's potential that its replacement is red, so we could have two adjacent red nodes, or the root replacement
        node may be black.
        */
        if(origColour==BLACK){
            RBDeleteFixUp(replaceNode,parentReplaceNode); //moveNode passed in as param to deal with special replaceNode = null
            //since we still want to keep track of replaceNode's Parent
        }

    }

    private void RBDeleteFixUp(RBTreeNode<K, V> replaceNode,RBTreeNode<K, V> parentReplaceNode) {
        /*
            If the replacement node was initially red, then we can skip the while loop and just compensate by making it
            black.
            If the replacement node is the root then so long as it is black we are fine, since it has no ancestors so
            all subtrees will still have equal black height.

            However, if the node was black to begin with then we can't add an extra-black -so we need to do a fix-up in
            the while loop.
         */
        while(replaceNode!=mRoot && getColour(replaceNode)==BLACK) {

            if (replaceNode == getLeftChild(parentReplaceNode)) { //like with insert fix-up we consider case
                //where the replaceNode is a left child, and then the other case is just a mirrored strategy.

                RBTreeNode<K, V> siblingNode = getRightChild(parentReplaceNode);

                if (getColour(siblingNode) == RED) {
                    //CASE 1: x's sibling is red
                    setColour(siblingNode, BLACK);
                    leftRotate(parentReplaceNode);
                    siblingNode = getRightChild(parentReplaceNode); //this must be black since it was initially
                    //a child of a red node.
                    //so we are converting case 1 -> case 2,3 or 4

                }
                //so siblingNode == BLACK now, so we split into 3 cases (cases 2,3,4) based on colour of its children


                if (getColour(getLeftChild(siblingNode)) == BLACK && getColour(getRightChild(siblingNode)) == BLACK) {
                    //CASE 2: both of the children of the sibling node are black
                    setColour(siblingNode, RED); //it is safe to do this since no red children

                    //Now, both the subtree rooted at replaceNode and the subtree rooted at its sibling have one
                    //fewer black node. This is equiv. to replaceNode's parent subtree as a whole having one fewer
                    //black node - the same problem but moved up a level.
                    replaceNode = parentReplaceNode;
                    parentReplaceNode = getParent(parentReplaceNode);

                } else { //at least one-of the children is red
                    if (getColour(getRightChild(siblingNode)) == BLACK) {
                        //CASE 3: the left child is red and right child is black

                        //we swap the sibling and its left child's colours, then right rotate so
                        //the sibling is now the right child of its original leftChild
                        setColour(getLeftChild(siblingNode), BLACK);
                        setColour(siblingNode, RED);
                        rightRotate(siblingNode);
                        //now, the old leftChild of sibling is replaceNode's new sibling since it moved up a level
                        // through the rightRotate
                        siblingNode = getRightChild(parentReplaceNode);
                        //NB: the right child of siblingNode is red now, so we shift from
                        // case 3 -> case 4
                    }

                    //CASE 4: right child is red (colour of left child could be black or red - doesn't matter)

                    //we swap the sibling and parent's colours and left-rotate, and also colour the rightChild of
                    //sibling black to maintain black heights after rotation.
                    setColour(siblingNode, getColour(parentReplaceNode));
                    leftRotate(parentReplaceNode);

                    //NB before this replaceNode's subtree had black height (sibling) - 1 since one fewer black node.
                    //since sibling was initially black, its left child's subtree had black height(sibling) -1.
                    //this left child becomes replaceNode's new sibling due to right-rotates and since it has
                    //same black height we no longer have a violation, so we break out of loop.
                    break;
                }


            } else { //like with insert fix-up this is just a mirrored strategy.

                RBTreeNode<K, V> siblingNode = getLeftChild(parentReplaceNode);

                if (getColour(siblingNode) == RED) {
                    //CASE 1: x's sibling is red
                    setColour(siblingNode, BLACK);
                    rightRotate(parentReplaceNode);
                    siblingNode = getLeftChild(parentReplaceNode); //this must be black since it was initially
                    //a child of a red node.
                    //so we are converting case 1 -> case 2,3 or 4

                }
                //so siblingNode == BLACK now, so we split into 3 cases (cases 2,3,4) based on colour of its children


                if (getColour(getLeftChild(siblingNode)) == BLACK && getColour(getRightChild(siblingNode)) == BLACK) {
                    //CASE 2: both of the children of the sibling node are black
                    setColour(siblingNode, RED); //it is safe to do this since no red children

                    //Now, both the subtree rooted at replaceNode and the subtree rooted at its sibling have one
                    //fewer black node. This is equiv. to replaceNode's parent subtree as a whole having one fewer
                    //black node - the same problem but moved up a level.
                    replaceNode = parentReplaceNode;
                    parentReplaceNode = getParent(parentReplaceNode);

                } else { //at least one-of the children is red
                    if (getColour(getLeftChild(siblingNode)) == BLACK) {
                        //CASE 3: the right child is red and left child is black

                        //we swap the sibling and its right child's colours, then left rotate so
                        //the sibling is now the left child of its original rightChild
                        setColour(getRightChild(siblingNode), BLACK);
                        setColour(siblingNode, RED);
                        leftRotate(siblingNode);
                        //now, the old rightChild of sibling is replaceNode's new sibling since it moved up a level
                        // through the rightRotate
                        siblingNode = getLeftChild(parentReplaceNode);
                        //NB: the left child of siblingNode is red now, so we shift from
                        // case 3 -> case 4
                    }

                    //CASE 4: left child is red (colour of right child could be black or red - doesn't matter)

                    //we swap the sibling and parent's colours and right-rotate, and also colour the leftChild of
                    //sibling black to maintain black heights after rotation.
                    setColour(siblingNode, getColour(parentReplaceNode));
                    rightRotate(parentReplaceNode);
                    //NB before this replaceNode's subtree had black height (sibling) - 1 since one fewer black node.
                    //since sibling was initially black, its left child's subtree had black height(sibling) -1.
                    //this left child becomes replaceNode's new sibling due to right-rotates and since it has
                    //same black height we no longer have a violation, so we break out of loop.
                    break;
                }


            }
        }

        setColour(replaceNode,BLACK);
    }


    @Override
    public void set(K k, V v) {
        insert(k,v);
    }

    @Override
    public V get(K k) throws KeyNotFoundException {

        return nodeWithKey(k).value;
    }

}
