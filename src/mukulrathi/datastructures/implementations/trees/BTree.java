package mukulrathi.datastructures.implementations.trees;

import mukulrathi.customexceptions.BTreeNodeFullException;
import mukulrathi.customexceptions.KeyNotFoundException;
import mukulrathi.datastructures.abstractdatatypes.Dictionary;

import java.util.ArrayList;

public class BTree<K extends Comparable<K>,V> implements Dictionary<K,V> {
    protected int mMinDegree;
    protected BTreeNode<K,V> mRoot;


    protected class BTreeNode<K extends Comparable<K>,V>{
        //we use an associative array structure to store key, value pairs, i.e.
        //the key at index i in keys has a corresponding value at index i in values
        public ArrayList<K> keys;
        public ArrayList<V> values;
        public ArrayList<BTreeNode<K,V>> children;
        public boolean isLeaf; //true if node is a leaf, false otherwise

        public BTreeNode(){
            keys = new ArrayList<K>();
            values = new ArrayList<V>();
            isLeaf = true;


        }
        public BTreeNode(ArrayList<K> ks, ArrayList<V> vs){
            isLeaf = true;
            keys = ks;
            values = vs;
        }

        @Override
        public String toString(){ //for brevity we will not print out the values, only the keys for each node
            String output= "( ";
            for(K k: keys){
                output+=k + " ";
            }
            output+=")";
            return output;
        }

        public boolean isFull(){ //this method checks if node is full
            return (keys.size()==(2*mMinDegree-1));
        }

    }

    //helper class to package together node and index into a tuple,
    //since we need to know index of key/value within node, as well as which
    //node we are referring to
    private class NodeIndexPair<K extends Comparable<K>,V>{
        public BTreeNode<K,V> node;
        public int index;

        public NodeIndexPair(BTreeNode<K,V> n, int i){
            node =n;
            index = i;
        }

    }

    public BTree(int minDegree){
        mMinDegree = minDegree;
    }


    //Internal BTree Methods for search, insert and delete

    private NodeIndexPair<K,V> BTreeSearch(BTreeNode<K,V> node, K k){
        //search subtree rooted at node for key - returns node and index within node if key is present
        int i=0;
        //search along list of keys to find subtree range for key
        while(i<node.keys.size()&& k.compareTo(node.keys.get(i))>0){
            i++;
        }
        if(i<node.keys.size()&&k.compareTo(node.keys.get(i))==0){
            //key matches one of the keys in node
            return new NodeIndexPair<>(node,i);
        }
        //key not in node
        else if(node.isLeaf){
            //no children to search so key can't be found
            return null;
        }
        else{ //search children subtree i
            return BTreeSearch(node.children.get(i),k);
        }

    }



    private void BTreeSplitChild(BTreeNode<K, V> node, int i) throws BTreeNodeFullException {
        /*this splits the node's ith child (size 2t-1) into two children of size(t-1) and migrates the median key
        up to the current node
            [A][B]                  [A][d][B]
              |            ->         /   \
           [c...d...e]            [c...]  [...e]
        */

        //Pre-condition -node is not full, otherwise we wouldn't be able to add median key of child to it
        if(node.isFull()) throw new BTreeNodeFullException();

        //split ith child into two children, each with t-1 keys
        //first has 0..t-2 inclusive, second has t...2t-2 inclusive
        BTreeNode<K, V> oldChild = node.children.get(i);
        BTreeNode<K,V> newChild1 = new BTreeNode<K,V>((ArrayList<K>) node.keys.subList(0,mMinDegree-1),
                                                    (ArrayList<V>) node.values.subList(0,mMinDegree-1));
        BTreeNode<K,V> newChild2 = new BTreeNode<K,V>((ArrayList<K>) node.keys.subList(mMinDegree,2*mMinDegree-1),
                                                    (ArrayList<V>) node.values.subList(mMinDegree,2*mMinDegree-1));
        if(!oldChild.isLeaf){
            //split children of oldChild 0...2t-1 -> 0...t-1 and t...2t-1 inclusive
            newChild1.isLeaf = false;
            newChild2.isLeaf = false;

            newChild1.children = (ArrayList<BTreeNode<K, V>>) oldChild.children.subList(0,mMinDegree);
            newChild2.children = (ArrayList<BTreeNode<K, V>>) oldChild.children.subList(mMinDegree,2*mMinDegree-1);
        }

        //migrate median key

        node.keys.add(i,oldChild.keys.get(mMinDegree-1));
        node.values.add(i,oldChild.values.get(mMinDegree-1));

        //replace oldChild
        node.children.set(i,newChild1);
        node.children.add(i+1,newChild2);

    }


    private void BTreeInsertNonFull(BTreeNode<K, V> node, K k, V v) throws BTreeNodeFullException {
        if(node.isFull()) throw new BTreeNodeFullException();
        if(node.isLeaf){ //base case, we always insert key into a leaf node, since that
            // maintains non-leaf node #children=#keys+1 property
            int i=0;
            //linear search to find position of key
            while(i<node.keys.size() && node.keys.get(i).compareTo(k)<0){
                i++;
            }
            //we've found position
            node.keys.add(i,k);
            node.values.add(i,v);
        }
        else {
            //node is not a leaf so we find right child subtree to recurse down
            int i=0;
            //linear search to find position of key
            while(i<node.keys.size() && node.keys.get(i).compareTo(k)<0){
                i++;
            }
            if(node.children.get(i).isFull()){
                //if the child is full we split
                BTreeSplitChild(node,i);
                //now we have to check if the new ith key (old ith child's median key) is smaller than k
                if(k.compareTo(node.keys.get(i))>0){
                    //larger than ith key but smaller than i+1th key, so recurse down i+1th child
                    i++;
                }
            }
            //now child must be non-full, so recurse on that subtree
            BTreeInsertNonFull(node.children.get(i),k,v);

        }
    }



    @Override
    public void set(K k, V v) {
        //check if key already present
        NodeIndexPair<K,V> keyPresent = BTreeSearch(mRoot,k);
        if(keyPresent!=null){
            keyPresent.node.values.set(keyPresent.index,v); //overwrite value associated with key k
        }
        else {
            //we are inserting k into B-tree

            //deal with empty tree case
            if (mRoot == null) {
                mRoot = new BTreeNode<K, V>();
                mRoot.keys.add(k);
                mRoot.values.add(v);
            }
            else {
                if(mRoot.isFull()) {
                    //we pre-emptively explode full nodes on the way down, this slightly increases height of tree
                    //but prevents costly recursive explosion up the tree (since that would require far more disk accesses)

                    //create a new (empty) root and make the old root its child
                    BTreeNode<K, V> oldRoot = mRoot;
                    mRoot = new BTreeNode<K, V>();
                    mRoot.isLeaf = false;
                    mRoot.children = new ArrayList<BTreeNode<K, V>>();
                    mRoot.children.add(oldRoot);
                    //split the old root into two - so new root has old root's median key, and two children (each t-1)
                    //since 2(t-1) + 1 = 2t-1
                    try {
                        BTreeSplitChild(mRoot, 0);
                    } catch (BTreeNodeFullException e) { //this shouldn't happen, but the exception is there as a check
                        e.printStackTrace();
                    }
                }
                //now root is definitely not full
                try {
                    BTreeInsertNonFull(mRoot,k,v);
                } catch (BTreeNodeFullException e) { //again, this shouldn't happen, but the exception is there as a check
                    e.printStackTrace();
                }

            }
        }

    }



    @Override
    public V get(K k) throws KeyNotFoundException {
        NodeIndexPair<K,V> result = BTreeSearch(mRoot,k);
        if(result==null){
            throw new KeyNotFoundException(); //key not in B-Tree
        }
        else{
            return result.node.values.get(result.index);
            //return value at that position
        }
    }

    @Override
    public void delete(K k) throws KeyNotFoundException {

    }







    //this method returns the depth of the tree
    private int depth(BTreeNode<K,V> currentNode){
        if(currentNode.isLeaf) return 0; //since leaf so no children - 1 node subtree has depth 0
        else {
            int maxChildDepth = 0;
            for(BTreeNode<K,V> child: currentNode.children){
                maxChildDepth = (depth(child)>maxChildDepth)?depth(child):maxChildDepth;
            }
            return 1 + maxChildDepth;
        }
    }


    /*
        This method modifies the list of nodes at each level - used for the toString method
        we pass in listOfNodes to prevent unnecessary copying, and maxDepth to prevent repeated computation
     */
    private void nodesByLevel(BTreeNode<K,V> currentNode,int depth, ArrayList<ArrayList<BTreeNode<K,V>>> listOfNodes, int maxDepth){
        if (depth >= maxDepth){ //greater than max depth of tree so cannot have any nodes here
            return;
        }
        if(currentNode==null){
            currentNode= new BTreeNode<K, V>(); //empty placeholder node
        }
        listOfNodes.get(depth).add(currentNode);
        if(currentNode.isLeaf){
            nodesByLevel(null,depth+1,listOfNodes,maxDepth); //put an empty placeholder node for children
        }
        else{
            for(BTreeNode<K,V> child: currentNode.children){
                nodesByLevel(child,depth+1,listOfNodes,maxDepth);
            }
            listOfNodes.get(depth+1).add(null);//this will print a "||" i.e. clearly indicate when the children of one node end and the
                                                //other node's children begin

        }





    }
    @Override
    public String toString(){ //this prints out the B-Tree level by level - which is useful for debugging purposes
        String prettyPrint = "";
        int maxDepth = depth(mRoot);
        prettyPrint+= "Depth of tree: " + (maxDepth-1) + "\n";

        //initialise the list of nodes
        ArrayList<ArrayList<BTreeNode<K,V>>> listOfNodes = new ArrayList<ArrayList<BTreeNode<K,V>>>(maxDepth);
        for(int i=0; i<maxDepth;i++){
            ArrayList<BTreeNode<K,V>> nodeLevel = new ArrayList<BTreeNode<K,V>>();

            listOfNodes.add(i,nodeLevel);
        }

        nodesByLevel(mRoot,0,listOfNodes,maxDepth);
        for (ArrayList<BTreeNode<K,V>> nodeLevel : listOfNodes){
            for(BTreeNode<K,V> node : nodeLevel){
                prettyPrint+=" ";
                prettyPrint+= (node!=null) ? ((node.keys.size()==0)? "-": node) : "||";
            }
            prettyPrint+="\n";
        }
        return prettyPrint;
    }

}
