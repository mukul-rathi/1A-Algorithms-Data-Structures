package mukulrathi.datastructures.implementations.trees;

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

    }

    public BTree(int minDegree){
        mMinDegree = minDegree;
    }

    @Override
    public void set(K k, V v) {

    }

    @Override
    public V get(K k) throws KeyNotFoundException {
        return null;
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
