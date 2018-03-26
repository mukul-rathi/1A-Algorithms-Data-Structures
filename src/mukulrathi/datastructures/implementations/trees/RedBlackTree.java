package mukulrathi.datastructures.implementations.trees;

public class RedBlackTree<K extends Comparable<K>,V> extends BinarySearchTree<K> {
    private class RBTreeNode<K,V> extends TreeNode<K>{
        public V value;
        public NodeColour colour;

        public RBTreeNode(K k, V v) {
            super(k);
            value=v;

        }

        @Override
        public String toString(){ //used to print out node
            return "(K: "+key+ " " + value+")";
        }
    }
}
