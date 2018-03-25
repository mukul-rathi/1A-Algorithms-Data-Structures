package mukulrathi.datastructures.implementations.trees;

import mukulrathi.customexceptions.KeyNotFoundException;
import mukulrathi.customexceptions.UnderflowException;
import mukulrathi.datastructures.abstractdatatypes.Stack;
import mukulrathi.datastructures.abstractdatatypes.sets.DynamicSet;
import mukulrathi.datastructures.abstractdatatypes.sets.OrderedSet;
import mukulrathi.datastructures.implementations.stack.LinkedListStack;

import java.util.ArrayList;
import java.util.List;

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
        @Override
        public String toString(){ //used to print out node
            return "("+key+")";
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
        TreeNode<K> currentNode = mRoot;
        TreeNode<K> parentNode = null; //parent of current node
        while(currentNode!=null){
            if(k.compareTo(currentNode.key)==0){
                return; // Case 1: key is already present, we don't need to insert it
            }
            else if(k.compareTo(currentNode.key)<0){ //k< current node key so go down left subtree
                parentNode = currentNode;
                currentNode = currentNode.leftChild;
            }
            else{ // k> current node key so go down right subtree
                parentNode = currentNode;
                currentNode = currentNode.rightChild;
            }

        }
        //we've reached bottom of tree so insert as leaf
        TreeNode<K> newNode = new TreeNode<K>(k);

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
    public void delete(K k) throws KeyNotFoundException {
        TreeNode<K> currentNode = nodeWithKey(k); //this will throw KeyNotFoundException if k not in tree

        //Case 1: if node has at most one subtree, we just shift this up
        if (currentNode.leftChild == null || currentNode.rightChild == null){

            if (currentNode.leftChild == null) {
                //splice out node and update pointers
                currentNode.rightChild.parent = currentNode.parent;

                if (currentNode.parent.leftChild == currentNode) {
                    currentNode.parent.leftChild = currentNode.rightChild;
                } else {
                    currentNode.parent.rightChild = currentNode.rightChild;

                }
            }
            else {
                //splice out node and update pointers
                currentNode.leftChild.parent = currentNode.parent;

                if (currentNode.parent.leftChild == currentNode) {
                    currentNode.parent.leftChild = currentNode.leftChild;
                } else {
                    currentNode.parent.rightChild = currentNode.leftChild;

                }
            }
    }
        //Case 2: node has both subtrees, so its successor lies in right subtree
        //we swap node with its successor, then delete it
        //For ease of implementation, we will do this by deleting successor from tree, then swapping it
        //into the original node's position.


        else{
            TreeNode<K> succNode = nodeWithKey(successor(k));
            //note successor has no left subtree so case 1 applies
            if(succNode.rightChild!=null) { //check if right subtree present
                succNode.rightChild.parent = currentNode.parent;
            }

            if(succNode.parent.leftChild==succNode){
                succNode.parent.leftChild= succNode.rightChild;
            }
            else{
                succNode.parent.rightChild= succNode.rightChild;

            }

            //next let us swap succNode into currentNode's position by updating pointers

            //first the parent node
            succNode.parent  = currentNode.parent;

            if(currentNode==mRoot){
                mRoot= succNode;
            }
            else { //i.e. if currentNode has a parent (i.e. not root)
                if (currentNode.parent.leftChild == currentNode) {
                    currentNode.parent.leftChild = succNode;
                } else {
                    currentNode.parent.rightChild = succNode;

                }
            }

            //next, the children
            succNode.leftChild = currentNode.leftChild;
            currentNode.leftChild.parent = succNode;

            succNode.rightChild= currentNode.rightChild;
            currentNode.rightChild.parent = succNode;


        }


    }
    @Override
    public DynamicSet<K> union(DynamicSet<K> s) {
        //go through s and remove an element and insert it into the underlying data-structure
        while(!s.isEmpty()){
            K sKey = null;
            try {
                sKey = s.chooseAny();
                s.delete(sKey);

            } catch (KeyNotFoundException e) { //this exception is not going to be thrown since
                //s not empty so we must get an arbitrary key back, and we know that we can remove it
                e.printStackTrace();
            }
            insert(sKey);
        }
        return (DynamicSet<K>) this;
    }

    @Override
    public DynamicSet<K> intersection(DynamicSet<K> s) {
        DynamicSet<K> result= new BinarySearchTree<K>();
        while(!s.isEmpty()){
            //remove a key from S each time
            K sKey = null;
            try {
                sKey = s.chooseAny();
                s.delete(sKey);

            } catch (KeyNotFoundException e) { //this exception is not going to be thrown since
                //s not empty so we must get an arbitrary key back, and we know that we can remove it
                e.printStackTrace();
            }
            //if the key is also in this BST, it lies in the intersection
            if(hasKey(sKey)) {
                result.insert(sKey);
            }
        }

        return result;


    }

    @Override
    public DynamicSet<K> difference(DynamicSet<K> s) {
        while(!s.isEmpty()){
            //remove a key from S each time and check if in this BST - if so, remove it.
            K sKey = null;
            try {
                sKey = s.chooseAny();
                s.delete(sKey);

            } catch (KeyNotFoundException e) { //this exception is not going to be thrown since
                //s not empty so we must get an arbitrary key back, and we know that we can remove it
                e.printStackTrace();
            }
            //if the key is also in this BST, it lies in the intersection
            if(hasKey(sKey)) {
                try {
                    delete(sKey);
                } catch (KeyNotFoundException e) {//this exception is not going to be thrown since we've checked
                                                    //the key is present
                    e.printStackTrace();
                }
            }
        }
        return (DynamicSet<K>) this;



    }

    @Override
    public boolean subset(DynamicSet<K> s) {
        while(!s.isEmpty()){
            //remove a key from S each time
            K sKey = null;
            try {
                sKey = s.chooseAny();
                s.delete(sKey);

            } catch (KeyNotFoundException e) { //this exception is not going to be thrown since
                //s not empty so we must get an arbitrary key back, and we know that we can remove it
                e.printStackTrace();
            }
            //if the key from S is not in this BST, then S is not a subset of this BST
            if(!hasKey(sKey)) {
                return false;
            }
        }

        return true;
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
    public K chooseAny() throws KeyNotFoundException {
        if (isEmpty()) throw new KeyNotFoundException();
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

    public List<K> inOrderTraversal(){
        //visit left subtree first, then node, then right subtree
        //Postorder: visit L then R then node itself
        //Preorder: visit node then L then R
        ArrayList<K> answer = new ArrayList<K>();
        Stack<K> stack = new LinkedListStack<K>();
        TreeNode<K> currentNode = mRoot;
        while(currentNode!=null||!stack.isEmpty()) {
            while (currentNode != null) {
                stack.push(currentNode.key);
                currentNode = currentNode.leftChild;
            }
            if(!stack.isEmpty()){
                try {
                    K val = stack.pop();
                    answer.add(val);
                } catch (UnderflowException e) { //won't happen because we know stack is not empty
                    e.printStackTrace();
                }
                currentNode = currentNode.rightChild;
            }
        }
        return answer;

    }

    //this method returns the depth of the tree
    private int depth(TreeNode<K> currentNode){
        if(currentNode==null) return 0;
        else {
            return 1 + Math.max(depth(currentNode.leftChild),depth(currentNode.rightChild));
        }
    }


    /*
        This method modifies the list of nodes at each level - used for the toString method
        we pass in listOfNodes to prevent unnecessary copying, and maxDepth to prevent repeated computation
     */
    private void nodesByLevel(TreeNode<K> currentNode,int depth, int offset,ArrayList<ArrayList<TreeNode<K>>> listOfNodes, int maxDepth){
        if (depth >= maxDepth){ //greater than max depth of tree so cannot have any nodes here
            return;
        }
        listOfNodes.get(depth).set(offset,currentNode); //set the value of the node at the correct position in list
        if(currentNode==null){
            //left and right children also null
            nodesByLevel(null,depth+1,2*offset,listOfNodes,maxDepth);
            nodesByLevel(null,depth+1,2*offset+1,listOfNodes,maxDepth);

        }
        else{
            nodesByLevel(currentNode.leftChild,depth+1,2*offset,listOfNodes,maxDepth);
            nodesByLevel(currentNode.rightChild,depth+1,2*offset+1,listOfNodes,maxDepth);
        }


    }
    @Override
    public String toString(){ //this prints out the BST level by level - which is useful for debugging purposes
        String prettyPrint = "";
        int maxDepth = depth(mRoot);
        prettyPrint+= "Depth of tree: " + maxDepth + "\n";

        //initialise the list of nodes - initially all null values
        ArrayList<ArrayList<TreeNode<K>>> listOfNodes = new ArrayList<ArrayList<TreeNode<K>>>(maxDepth);
        for(int i=0; i<maxDepth;i++){
            ArrayList<TreeNode<K>> nodeLevel = new ArrayList<TreeNode<K>>();
            for(int j=0; j< (int) Math.pow(2,i);j++){
                nodeLevel.add(null);
            }
            listOfNodes.add(i,nodeLevel);
        }

        nodesByLevel(mRoot,0,0,listOfNodes,maxDepth);
        for (ArrayList<TreeNode<K>> nodeLevel : listOfNodes){
            for(TreeNode<K> node : nodeLevel){
                prettyPrint+=" ";
                prettyPrint+= (node!=null) ? node : "-";
            }
            prettyPrint+="\n";
        }
        return prettyPrint;
    }

}
