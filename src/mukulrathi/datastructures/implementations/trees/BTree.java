package mukulrathi.datastructures.implementations.trees;

import mukulrathi.customexceptions.KeyNotFoundException;
import mukulrathi.datastructures.abstractdatatypes.Dictionary;

import java.util.ArrayList;

public class BTree<K extends Comparable<K>,V> implements Dictionary<K,V> {
    private int mMinDegree;
    private BTreeNode<K,V> mRoot;


    private class BTreeNode<K extends Comparable<K>,V>{
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
}
