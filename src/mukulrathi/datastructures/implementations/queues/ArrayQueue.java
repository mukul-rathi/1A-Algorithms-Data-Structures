package mukulrathi.datastructures.implementations.queues;

import mukulrathi.customexceptions.UnderflowException;
import mukulrathi.datastructures.abstractdatatypes.Queue;

import java.util.ArrayList;

public class ArrayQueue<T> implements Queue<T>{
    private ArrayList<T> mQueue;//NB using ArrayList as substitute for array, since arrays
                                //don't work well with generics due to type erasure
    public  ArrayQueue(){
        mQueue = new ArrayList<T>();
    }
    public ArrayQueue(T x){
        mQueue = new ArrayList<T>();
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