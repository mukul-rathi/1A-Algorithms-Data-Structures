package mukulrathi.datastructures.implementations.queues;

import mukulrathi.customexceptions.UnderflowException;
import mukulrathi.datastructures.abstractdatatypes.Deque;

/*
This class is an example implementation of the Deque ADT using a doubly linked list.
 */

public class LinkedListDeque<T> implements Deque<T> {
    private int mSize;
    private QueueNode<T> mFront;
    private QueueNode<T> mRear; //these refer to nodes at front and rear ends respectively

    //class defining each node in the list
    //since doubly linked the nodes contain a value and pointers to the previous and next nodes
    private class QueueNode<T> {
        public T value;
        public QueueNode<T> nextElem;
        public QueueNode<T> prevElem;

        public QueueNode(T x) {
            value = x;
        }
    }

    public LinkedListDeque(){
        mSize=0;
    }
    public LinkedListDeque(T x){
        mSize=1;
        mFront = new QueueNode<T>(x);
        mRear = mFront;
    }

    @Override
    public boolean isEmpty() {
        return (mSize==0);
    }

    @Override
    public void putFront(T x) {
        QueueNode<T> newFront = new QueueNode<T>(x);
        newFront.nextElem = mFront;
        mFront.prevElem = newFront;
        mFront = newFront;
        mSize++;
    }

    @Override
    public void putRear(T x) {
        QueueNode<T> newRear = new QueueNode<T>(x);
        newRear.prevElem = mRear;
        mRear.nextElem = newRear;
        mRear= newRear;
        mSize++;

    }

    @Override
    public T getFront() throws UnderflowException {
        if(isEmpty()) throw new UnderflowException();
        T returnVal = mFront.value;
        ;
        if(mSize==1){
            mFront = null;
            mRear = null;
        }
        else{
            mFront.nextElem.prevElem=null;
            mFront = mFront.nextElem;
        }
        mSize--;
        return returnVal;
    }

    @Override
    public T getRear() throws UnderflowException {
        if(isEmpty()) throw new UnderflowException();
        T returnVal = mRear.value;
        if(mSize==1){
            mFront = null;
            mRear = null;
        }
        else{
            mRear.prevElem.nextElem = null;
            mRear = mRear.prevElem;
        }
        mSize--;
        return returnVal;
    }
}
