package mukulrathi.datastructures.implementations.stack;

import mukulrathi.customexceptions.UnderflowException;
import mukulrathi.datastructures.abstractdatatypes.Stack;

import java.util.ArrayList;
/*
This class is an example implementation of the Stack ADT using an array.
The index pointing to top of stack increases as more elements are added to stack
- lowest index = bottom of stack.
 */
public class ArrayStack<T> implements Stack<T> {
    private int mTop;
    private ArrayList<T> mStack; //NB using ArrayList as substitute for array, since arrays
                                //don't work well with generics due to type erasure

    public ArrayStack(){
        mTop=-1;
    }
    public ArrayStack(T x){
        mTop=0;
        mStack = new ArrayList<T>();
        mStack.add(mTop,x);
    }
    @Override
    public boolean isEmpty() {
        return (mTop<0);
    }

    @Override
    public void push(T x) { //if this was array, we would use a table doubling approach to increase
                            //size of array if full.This maintains the O(1) amortized cost of push.
        mStack.add(++mTop,x);
    }

    @Override
    public T pop() throws UnderflowException {
        return mStack.remove(mTop--);
    }

    @Override
    public T top() throws UnderflowException {
        return mStack.get(mTop);
        
    }
}
