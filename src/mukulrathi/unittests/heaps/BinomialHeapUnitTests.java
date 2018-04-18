package mukulrathi.unittests.heaps;
import mukulrathi.customexceptions.UnderflowException;
import mukulrathi.customexceptions.ValueNotPresentException;
import mukulrathi.datastructures.implementations.heaps.BinomialHeap;

import java.util.Collection;
import java.util.Comparator;

public class BinomialHeapUnitTests<T> extends BinomialHeap<T> {

    public BinomialHeapUnitTests(Comparator<? super T> comp) {
        super(comp);
    }

    //check that a binomial tree of order k has 2^k nodes (if satisfied return size, else return -1)
    private int treeSize(BinomialHeapNode<T> node){
        int size = 1;
        BinomialHeapNode<T> currentChild = node.child;
        int childSize;
        while(currentChild!=null){
            childSize = treeSize(currentChild);
            if(childSize==-1) return -1; //child does not satisfy binomial tree size property,so tree doesn't either
            size+=childSize;
            currentChild= currentChild.rightSibling;
        }
        if(size !=((int) Math.pow(2,node.order)) ){ //doesn't satisfy size property
            return -1;
        }
        return size;
    }
    //check all binomial trees in heap satisfy size property (tree of order k has 2^k nodes)
    private boolean heapSize(){
        BinomialHeapNode<T> currentRoot = mFirstRoot;
        while (currentRoot!=null){
            if(treeSize(currentRoot)==-1){ //doesn't satisfy property
                return false;
            }
            currentRoot = currentRoot.rightSibling;
        }
        return true;
    }

    //check that a binomial tree of order k has height k (if satisfied return height, else return -1)
    private int treeHeight(BinomialHeapNode<T> node){
        BinomialHeapNode<T> currentChild = node.child;
        int maxChildHeight=-1;
        while(currentChild!=null){
            if(treeSize(currentChild)==-1) {
                return -1; //child does not satisfy binomial tree height property,so tree doesn't either
            }
            maxChildHeight = Math.max(maxChildHeight,treeSize(currentChild));
            currentChild= currentChild.rightSibling;
        }
        int height = maxChildHeight+1; //so if node has no children height=0

        if(height!=node.order){ //doesn't satisfy size property
            return -1;
        }
        return height;
    }

    //check all binomial trees in heap satisfy height property (tree of order k has height l=k)
    private boolean heapHeight(){
        BinomialHeapNode<T> currentRoot = mFirstRoot;
        while (currentRoot!=null){
            if(treeHeight(currentRoot)==-1){ //doesn't satisfy property
                return false;
            }
            currentRoot = currentRoot.rightSibling;
        }
        return true;
    }

    //check that a tree of order k has k children, of orders k-1,k-2,...0
    private boolean treeOrder(BinomialHeapNode<T> node) {
        if(node.order==0){
            return (node.child==null); // no children so satisfies order property
        }
        int childCount =0;
        int childOrder = node.order-1;
        BinomialHeapNode<T> currentChild = node.child;
        while(currentChild!=null){
            if (currentChild.order!=childOrder){ //NB recall left->right the children have descending orders
                return false;
            }
            childCount++;
            childOrder--;
            currentChild = currentChild.rightSibling;
        }
        if(node.order!=childCount){ //tree doesn't have correct order
            return false;
        }
        //now check if the children also satisfy order property
        currentChild = node.child;
        boolean satisfied = true;
        while (currentChild!=null){
            satisfied= satisfied&&treeOrder(currentChild);
            currentChild = currentChild.rightSibling;
        }
        return satisfied;
    }

    //check that all binomial tree satisfy tree order property
    // (tree of order k has k children, of orders k-1,k-2,...0)
    private boolean heapOrder(){
        //check each binomial tree satisfies property
        BinomialHeapNode<T> currentRoot = mFirstRoot;
        boolean satisfied = true;
        while (currentRoot!=null){
            satisfied= satisfied&&treeOrder(currentRoot);
            currentRoot = currentRoot.rightSibling;
        }
        return satisfied;
    }

    //check that the roots are ordered in strictly increasing order
    private boolean heapDistinctIncreasingOrder(){
        BinomialHeapNode<T> currentRoot = mFirstRoot;
        if(currentRoot==null){ //empty heap satisfies this property
            return true;
        }
        while(currentRoot.rightSibling!=null){
            if(currentRoot.order>=currentRoot.rightSibling.order){
                return false;
            }
            currentRoot = currentRoot.rightSibling;
        }
        return true;
    }

    //check each binomial tree satisfies min-heap property
    private boolean treeMinHeapProperty(BinomialHeapNode<T> node) {
        if(node.order==0){
            return true; // no children so satisfies min-heap property
        }
        BinomialHeapNode<T> currentChild = node.child;
        while(currentChild!=null){
            if(mComp.compare(currentChild.value,node.value)<0){
                return false; //min-heap property violation as child's value < node's value
            }
            currentChild = currentChild.rightSibling;
        }
        //now check if the children also satisfy min-heap property
        currentChild = node.child;
        boolean satisfied = true;
        while (currentChild!=null){
            satisfied= satisfied&&treeMinHeapProperty(currentChild);
            currentChild = currentChild.rightSibling;
        }
        return satisfied;
    }

    //check  binomial heap satisfies min-heap property
    private boolean heapMinHeapProperty() {
        //check each binomial tree satisfies property
        BinomialHeapNode<T> currentRoot = mFirstRoot;
        boolean satisfied = true;
        while (currentRoot!=null){
            satisfied= satisfied&&treeMinHeapProperty(currentRoot);
            currentRoot = currentRoot.rightSibling;
        }
        return satisfied;
    }

    //check the number of values in heap == number of nodes in heap
    private boolean numNodesInHeap() {
        //count number of nodes
        int numNodes = 0;
        BinomialHeapNode<T> currentRoot = mFirstRoot;
        while (currentRoot!=null){
            numNodes+=Math.pow(2,currentRoot.order);
            currentRoot = currentRoot.rightSibling;
        }
        if(numNodes!= valToNode.keySet().size()){
            return false;
        }

        //check each value in heap points to correct node
        for(T value : valToNode.keySet()){
            if(mComp.compare(value,valToNode.get(value).value)!=0){
                return false; //not correct node, as value in node =/= this value
            }
        }
        return true; //all  value->node pairs match up
    }

    private boolean unitTestPass(){
        return heapSize()&&heapHeight()&&heapOrder()&&heapDistinctIncreasingOrder()&&
                heapMinHeapProperty()&&numNodesInHeap();
    }
    private String unitTestResult() {

        String results = "\n UNIT TESTS:  \n";
        results += "Tree order k has 2^k Nodes? " + heapSize() + "\n";
        results += "Tree order k has height k? " + heapHeight() + "\n";
        results += "Tree order k has k children of orders k-1,k-2,..0? " + heapOrder() + "\n";
        results += "Orders of roots distinct (strictly increasing)? " + heapDistinctIncreasingOrder()+ "\n";
        results += "Min Heap Property satisfied? " + heapMinHeapProperty() + "\n";
        results += "Number of values match with number of nodes? " + numNodesInHeap() +  "\n";
        return results;
    }


    @Override
    public void insert(T x){
        System.out.print((unitTestPass())?"": ("Pre: Insert error:" + unitTestResult()));
        super.insert(x);
        System.out.print((unitTestPass())?"": ("Post: Insert error:" + unitTestResult()));
    }

    @Override
    public void delete(T x) throws ValueNotPresentException {
        System.out.print((unitTestPass())?"": ("Pre: Delete error:" + unitTestResult()));
        super.delete(x);
        System.out.print((unitTestPass())?"": ("Post: Delete error:" + unitTestResult()));
    }
    @Override
    public T extractMin() throws UnderflowException {
        System.out.print((unitTestPass())?"": ("Pre: Extract-min error:" + unitTestResult()));
        T val = super.extractMin();
        System.out.print((unitTestPass())?"": ("Post: Extract-min error:" + unitTestResult()));

        return val;
    }
    @Override
    public void decreaseKey(T x) throws ValueNotPresentException {
        System.out.print((unitTestPass())?"": ("Pre: Decrease-key error:" + unitTestResult()));
        super.decreaseKey(x);
        System.out.print((unitTestPass())?"": ("Post: Decrease-key error:" + unitTestResult()));
    }


}