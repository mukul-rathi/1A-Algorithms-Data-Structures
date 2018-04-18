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

        public BinomialHeapNode(T val) {
            value = val;
            order = 0;
        }
    }


    public BinomialHeap(Comparator<? super T> comp) {
        super(comp);
    }

    private BinomialHeap(BinomialHeapNode<T> firstRoot,Comparator<? super T> comp) {
        super(comp);
        mFirstRoot = firstRoot;
    }

    @Override
    public void insert(T x) {
        //we consider inserted value as a binomial heap of one node and merge it
        merge(new BinomialHeap<T>(new BinomialHeapNode<T>(x),mComp));
    }

    @Override
    public T first() throws UnderflowException {
        if(mFirstRoot==null) throw new UnderflowException();
        //find the minimum value out of the roots
        T minVal = mFirstRoot.value;
        BinomialHeapNode<T> currentRoot = mFirstRoot.rightSibling;
        while(currentRoot!=null){
            minVal = (mComp.compare(currentRoot.value,minVal)<0)?currentRoot.value:minVal;
        }
        return minVal;
    }

    @Override
    public T extractMin() throws UnderflowException {
        BinomialHeapNode<T> deleteRoot = valToNode.get(first());

            //splice deleteRoot out of root doubly linked list
            if(deleteRoot.leftSibling==null){ //i.e. deleteRoot was the first root in heap
                mFirstRoot = deleteRoot.rightSibling;
            }
            else{
                deleteRoot.leftSibling.rightSibling = deleteRoot.rightSibling;
            }
            if(deleteRoot.rightSibling!=null){
                deleteRoot.rightSibling.leftSibling = deleteRoot.leftSibling;
            }

        //merge the heap with the deleteRoot child heap
            merge(new BinomialHeap<T>(deleteRoot.child,mComp));

            return deleteRoot.value; //return minimum value

    }

    @Override
    public void delete(T x) throws ValueNotPresentException {

    }

    @Override
    public void decreaseKey(T x) throws ValueNotPresentException {

    }
    private void mergeTree(BinomialHeapNode<T> root1,BinomialHeapNode<T> root2 ) {
        //pre-condition: root1 and root2 are adjacent nodes
        if(mComp.compare(root1.value,root2.value)>0){
            //swap the two references so that root1 points to smaller root
            BinomialHeapNode<T> temp = root1;
            root1 = root2;
            root2 = temp;
        }
        //root1 points to smaller root

        //update root1 sibling pointers to splice out root2 from root list
        if(root1.leftSibling==root2){
            root1.leftSibling = root2.leftSibling;
            if(root2==mFirstRoot){
                mFirstRoot = root1;
            }
            else{ //root2 has a left Sibling since not first root
                root2.leftSibling.rightSibling = root1;
            }
        }
        else{ //root2 is root1's right sibling
            root1.rightSibling = root2.rightSibling;
            if(root2.rightSibling!=null){
                root2.rightSibling.leftSibling = root1;
            }
        }

        //finally, make root2 root1's first child
        root2.leftSibling = null;
        root2.rightSibling = root1.child;
        if(root1.child!=null){
            root1.child.leftSibling = root2;
        }
        root1.child = root2;
        root2.parent = root1;
        root1.order++; //increase root1's order since now has extra child
    }

    public void merge(BinomialHeap<T> otherQ) {
        //we merge in two passes - first we insert the other heap's roots into the heap root list, maintaining
        //increasing order of the heaps
        if(otherQ==null || otherQ.mFirstRoot==null){
            return; //merging with empty heap so no change
        }
        if(mFirstRoot==null){ //this heap is empty so again simple to merge
            mFirstRoot = otherQ.mFirstRoot;
            return;
        }
        //pointers to keep track of the two roots we are comparing
        BinomialHeapNode<T> thisRoot =mFirstRoot;
        BinomialHeapNode<T> otherRoot =otherQ.mFirstRoot;
        while((thisRoot.rightSibling!=null&&(otherRoot.order < thisRoot.order))
                ||otherRoot==null) {
            //until we've either reached the last root of this heap and the roots in the other heap have larger order,
            //so need to be appended to root list, or we've finished adding the roots from the other heap

            if (otherRoot.order < thisRoot.order) {
                //place other root before this root in the heap root list
                otherRoot.leftSibling = thisRoot.leftSibling;
                if (thisRoot == mFirstRoot) {
                    mFirstRoot = otherRoot;
                }
                else{ //thisRoot has a left-sibling
                    thisRoot.leftSibling.rightSibling = otherRoot;
                }

                otherRoot.rightSibling = thisRoot;
                thisRoot.leftSibling = otherRoot;

                otherRoot = otherRoot.rightSibling;
            }
            else{
                // move one root along
                thisRoot = thisRoot.rightSibling;
            }
        }
        if(thisRoot.rightSibling==null){
            //i.e. we've reached last root of this heap and need to append remaining roots from other queue
            thisRoot.rightSibling = otherRoot;
            otherRoot.leftSibling = thisRoot;
        }

        //now we go through and merge the heaps with same order
        BinomialHeapNode<T> currentRoot = mFirstRoot;
        while(currentRoot.rightSibling!=null){
            //iterate through heap
            if(currentRoot.order==currentRoot.rightSibling.order) { //two roots of same order - need to be merged
                mergeTree(currentRoot, currentRoot.rightSibling);
                //check which of the roots is still in root-list
                currentRoot = (currentRoot.parent==null)?currentRoot:currentRoot.rightSibling;
            }
            else {
                currentRoot = currentRoot.rightSibling; //check next pair along
            }
        }



    }

    @Override
    public void merge(PriorityQueue<T> otherQ) { //since we do not know nature of PriorityQueue, we merge value by value
        while(!otherQ.isEmpty()){
            try {
                insert(otherQ.extractMin());
            } catch (UnderflowException e) { //this won't occur as we know the heap is non-empty
                e.printStackTrace();
            }
        }

    }

    @Override
    public boolean isEmpty() {

        return (mFirstRoot==null);
    }
}
