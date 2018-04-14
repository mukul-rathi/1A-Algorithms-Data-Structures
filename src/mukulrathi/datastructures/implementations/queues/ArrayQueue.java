package mukulrathi.datastructures.implementations.queues;

import mukulrathi.customexceptions.UnderflowException;
import mukulrathi.datastructures.abstractdatatypes.Queue;

import java.util.ArrayList;
/*
This class is an example implementation of the Queue ADT using an Array.
 */
public class ArrayQueue<T> implements Queue<T>{
    private ArrayList<T> mQueue;//NB using ArrayList as substitute for array, since arrays
                                //don't work well with generics due to type erasure

    //used an initializer block for code common to both constructors
    {
        mQueue = new ArrayList<T>();
    }


    public  ArrayQueue(){

    }
    public ArrayQueue(T x){
        mQueue.add(x);

    }
    @Override
    public boolean isEmpty() {
        return (mQueue==null || mQueue.size()==0);
    }

    @Override
    public void put(T x) {
        mQueue.add(x);
    }

    @Override
    public T get() throws UnderflowException {
        if(isEmpty()) throw new UnderflowException();
        return mQueue.remove(0);
    }

    @Override
    public T first() throws UnderflowException {
        if(isEmpty()) throw new UnderflowException();
        return mQueue.get(0);
    }

}
