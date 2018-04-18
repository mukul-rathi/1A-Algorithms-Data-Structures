package mukulrathi.datastructures.implementations.heaps;

import mukulrathi.customexceptions.UnderflowException;
import mukulrathi.customexceptions.ValueNotPresentException;
import mukulrathi.datastructures.abstractdatatypes.PriorityQueue;

import java.util.Comparator;
import java.util.HashMap;

public class BinomialHeap<T> extends PriorityQueue<T> {
    protected BinomialHeapNode<T> mFirstRoot; //first root in the root list of binomial heap

    protected HashMap<T,BinomialHeapNode<T>> valToNode = new HashMap<T, BinomialHeapNode<T>>(); //this is used for O(1) access to the
    // location of the item in the heap

    protected class BinomialHeapNode<T>{
        public T value; //this is the payload of the node
        public int order; //how many children it has

        //pointers to parent, child and siblings
        public BinomialHeapNode<T> parent;
        public BinomialHeapNode<T> child; //this points to left-most child

        public BinomialHeapNode<T> leftSibling; //we connect siblings using a doubly-linked list
        public BinomialHeapNode<T> rightSibling;//we maintain invariant that children have decreasing order left->right


    }


    public BinomialHeap(Comparator<? super T> comp) {
        super(comp);
    }

    @Override
    public void insert(T x) {

    }

    @Override
    public T first() throws UnderflowException {
        return null;
    }

    @Override
    public T extractMin() throws UnderflowException {
        return null;
    }

    @Override
    public void delete(T x) throws ValueNotPresentException {

    }

    @Override
    public void decreaseKey(T x) throws ValueNotPresentException {

    }

    @Override
    public void merge(PriorityQueue<T> otherQ) {

    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
