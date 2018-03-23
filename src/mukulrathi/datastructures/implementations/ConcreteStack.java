package mukulrathi.datastructures.implementations;

import mukulrathi.customexceptions.UnderflowException;
import mukulrathi.datastructures.abstractdatatypes.Stack;

public class ConcreteStack<T> implements Stack<T> {
    private StackNode<T> mTop;

    private class StackNode<T>{
        public T value;
        public StackNode<T> nextElem;

        public StackNode(T x) {
            value = x;
        }
    }

    public ConcreteStack(){ //create empty stack

    }

    public ConcreteStack(T x){
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
