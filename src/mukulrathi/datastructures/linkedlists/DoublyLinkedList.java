package mukulrathi.datastructures.linkedlists;

import mukulrathi.customexceptions.OutOfBoundsException;
import mukulrathi.datastructures.abstractdatatypes.Vector;

/*
This class is an example implementation of the Vector ADT using a doubly linked list. Another potential implementation
is using an Array.
 */

public class DoublyLinkedList<T> implements Vector<T>{
    private int mLength;
    private ListNode<T> mHead;
    private ListNode<T> mTail; //NB this refers to the end node - not the list minus the head as in
                                //the singly linked list class.

    //class defining each node in the list
    //since doubly linked the nodes contain a value and pointers to the previous and next nodes
    private class ListNode<T> {
        public T value;
        public ListNode<T> nextElem;
        public ListNode<T> prevElem;

        public ListNode(T x) {
            value = x;
        }
    }

    public DoublyLinkedList(){
        mLength=0;
    }
    public DoublyLinkedList(T x){
        mLength=1;
        mHead = new ListNode<T>(x);
        mTail = mHead;
    }

    private ListNode<T> traverseToNode(int r) throws OutOfBoundsException {
        if (r>=mLength || r<0) { //not a valid inded
            throw new OutOfBoundsException();
        }
        ListNode<T> currentElem = mHead;

        if(r<mLength/2) { //if in first half of vector traverse forward
            while (r != 0) {//r=0 means we have found our rank
                currentElem = currentElem.nextElem;
                r--;
            }
        }
        else{ //slight optimisation - we start from back and traverse backwards
            currentElem = mTail;
            r = mLength-1-r; //this is rank relative to back
            while (r != 0) { //r=0 means we have found our rank
                currentElem = currentElem.prevElem;
                r--;
            }
        }
        return currentElem;
    }

    @Override
    public T elemAtRank(int r) throws OutOfBoundsException {
        ListNode<T> currentElem = traverseToNode(r);
        return currentElem.value;

    }

    @Override
    public void insertAtRank(int r, T val) throws OutOfBoundsException {
        ListNode<T> newElem = new ListNode<T>(val);

        if(r==0){  //add at  start of vector
            mHead.prevElem = newElem;
            newElem.nextElem = mHead;
            mHead= newElem;

        }
        else if(r==mLength){ //add at end of vector
            mTail.nextElem = newElem;
            newElem.prevElem = mTail;
            mTail = newElem;

        }
        else {
            //get nodes at current rank r and r-1
            ListNode<T> currentElem = traverseToNode(r);

            //splice pointers to fit in new Elem between the current and immediately
            //preceding nodes
            currentElem.prevElem.nextElem = newElem;
            newElem.prevElem =  currentElem.prevElem;
            newElem.nextElem = currentElem;
        }
        mLength++;

    }

    @Override
    public void replaceatRank(int r, T newVal) throws OutOfBoundsException {
        ListNode<T> newElem = new ListNode<T>(newVal);
        ListNode<T> currentElem = traverseToNode(r);

        //update pointers of newElem
        newElem.prevElem = currentElem.prevElem;
        newElem.nextElem = currentElem.nextElem;

        //ensure pointers for the prev and next elems (if they exist) point to
        // new elem rather than currentElem
        if(currentElem.prevElem!=null) {
            currentElem.prevElem.nextElem = newElem;
        }
        if(currentElem.nextElem!=null) {
            currentElem.nextElem.prevElem = newElem;
        }

    }

    @Override
    public T removeAtRank(int r) throws OutOfBoundsException {
        T removedVal = null;
        if(mLength==0) throw new OutOfBoundsException();
        if(r==0){ //removing head of vector
            removedVal = mHead.value;
            if(mLength==1) { //only one element
                mHead = null;
                mTail = null;
            }
            else{ //update so 2nd element is head
                mHead.nextElem.prevElem = null;
                mHead = mHead.nextElem;
            }
        }
        else if(r==mLength-1){ //NB the first if deals with 1 element case where no preceding elements
            removedVal = mTail.value;
            //set tail to be second-last element
            mTail.prevElem.nextElem = null;
            mTail = mTail.prevElem;
        }
        else{ //neither head nor tail
            ListNode<T> currentElem = traverseToNode(r);
            removedVal = currentElem.value;
            //splice out node - since not head or tail we know it has a prev and next elem.
            currentElem.prevElem.nextElem = currentElem.nextElem;
            currentElem.nextElem.prevElem = currentElem.prevElem;
        }

        mLength--;
        return removedVal;
    }

    public int getLength() {
        return mLength;
    }
}
