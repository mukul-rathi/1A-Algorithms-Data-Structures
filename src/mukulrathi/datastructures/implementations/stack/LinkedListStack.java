package mukulrathi.datastructures.implementations.stack;

import mukulrathi.customexceptions.UnderflowException;
import mukulrathi.datastructures.abstractdatatypes.Stack;
/*
This class is an example implementation of the Stack ADT using a singly linked list.
Compared to ArrayStack implementation, this has the downside of
memory overhead of creating objects for each node
 */
public class LinkedListStack<T> implements Stack<T> {
    private StackNode<T> mTop;

    private class StackNode<T>{
        public T value;
        public StackNode<T> nextElem;

        public StackNode(T x) {
            value = x;
        }
    }

    public LinkedListStack(){ //create empty stack

    }

    public LinkedListStack(T x){
        mTop = new StackNode<T>(x);
    }

    @Override
    public boolean isEmpty() {
        return (mTop==null);
    }

    @Override
    public void push(T x) {
        StackNode<T> newTop = new StackNode<T>(x);
        newTop.nextElem = mTop;
        mTop = newTop;
    }

    @Override
    public T pop() throws UnderflowException {
        if(isEmpty()) throw new UnderflowException();
        T returnVal = mTop.value;
        mTop = mTop.nextElem;
        return  returnVal;
    }

    @Override
    public T top() throws UnderflowException {
        if(isEmpty()) throw new UnderflowException();
        return mTop.value;
    }
}
