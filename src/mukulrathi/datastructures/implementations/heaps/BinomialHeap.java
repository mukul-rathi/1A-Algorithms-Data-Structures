package mukulrathi.datastructures.implementations.heaps;

import mukulrathi.customexceptions.UnderflowException;
import mukulrathi.customexceptions.ValueNotPresentException;
import mukulrathi.datastructures.abstractdatatypes.PriorityQueue;

import java.util.ArrayList;
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

        public BinomialHeapNode() {
            order = 0;
        }

        @Override
        public String toString(){
            return "(Value: " + value +" Order: " + order + ")";
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
        if(valToNode.keySet().contains(x)){
            return; //x is already in the heap so we do not insert duplicates
        }
        //we consider inserted value as a binomial heap of one node and merge it
        BinomialHeapNode<T> newNode = new BinomialHeapNode<T>(x);
        valToNode.put(x,newNode);
        merge(new BinomialHeap<T>(newNode,mComp));
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
        while(otherRoot!=null){
            //until we've finished adding the roots from the other heap

            if (otherRoot.order < thisRoot.order) {
                //place other root before this root in the heap root list
                otherRoot.leftSibling = thisRoot.leftSibling;
                if (thisRoot == mFirstRoot) {
                    mFirstRoot = otherRoot;
                }
                else{ //thisRoot has a left-sibling
                    thisRoot.leftSibling.rightSibling = otherRoot;
                }
                BinomialHeapNode<T> nextOtherRoot =otherRoot.rightSibling;

                otherRoot.rightSibling = thisRoot;
                thisRoot.leftSibling = otherRoot;
                otherRoot = nextOtherRoot;
            }
            else{
                if(thisRoot.rightSibling==null){
                    //i.e. we've reached last root of this heap and need to append remaining roots from other queue
                    thisRoot.rightSibling = otherRoot;
                    otherRoot.leftSibling = thisRoot;

                    break;//we've finished merging
                }
                else {
                    // move one root along
                    thisRoot = thisRoot.rightSibling;
                }
            }
        }


        //now we go through and merge the heaps with same order
        BinomialHeapNode<T> currentRoot = mFirstRoot;
        while(currentRoot.rightSibling!=null){
            //iterate through heap
            if(currentRoot.order==currentRoot.rightSibling.order) { //two roots of same order - need to be merged
                BinomialHeapNode<T> currentRightSibling = currentRoot.rightSibling;
                mergeTree(currentRoot, currentRightSibling);
                //check which of the roots is still in root-list
                currentRoot = (currentRoot.parent==null)?currentRoot:currentRightSibling;
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
    /*
            This method modifies the list of nodes at each level - used for the toString method
            we pass in listOfNodes to prevent unnecessary copying, and maxDepth to prevent repeated computation
         */
    private void nodesByLevel(BinomialHeapNode<T> currentNode, int depth, ArrayList<ArrayList<BinomialHeapNode<T>>> listOfNodes, int maxDepth){
        if (depth >= maxDepth){ //greater than max depth of tree so cannot have any nodes here
            return;
        }
        if(currentNode==null){
            currentNode= new BinomialHeapNode<T>(); //empty placeholder node
        }
        listOfNodes.get(depth).add(currentNode);
        if(currentNode.order==0){
            nodesByLevel(null,depth+1,listOfNodes,maxDepth); //put an empty placeholder node for children
        }
        else{
            BinomialHeapNode<T> child = currentNode.child;
            while(child!=null){
                nodesByLevel(child,depth+1,listOfNodes,maxDepth);
                child = child.rightSibling;
            }
            listOfNodes.get(depth+1).add(null);//this will print a "||" i.e. clearly indicate when the children of one node end and the
            //other node's children begin

        }





    }
    public String treePrinter(BinomialHeapNode<T> root) { //this prints out the B-Tree level by level - which is useful for debugging purposes
        String prettyPrint = "";
        int maxDepth = root.order+1;
        prettyPrint+= "Depth of tree: " + (maxDepth-1) + "\n";

        //initialise the list of nodes
        ArrayList<ArrayList<BinomialHeapNode<T>>> listOfNodes = new ArrayList<ArrayList<BinomialHeapNode<T>>>(maxDepth);
        for(int i=0; i<maxDepth;i++){
            ArrayList<BinomialHeapNode<T>> nodeLevel = new ArrayList<BinomialHeapNode<T>>();

            listOfNodes.add(i,nodeLevel);
        }

        nodesByLevel(root,0,listOfNodes,maxDepth);
        for (ArrayList<BinomialHeapNode<T>> nodeLevel : listOfNodes){
            for(BinomialHeapNode<T> node : nodeLevel){
                prettyPrint+=" ";
                prettyPrint+= (node!=null) ? ((node.value==null)? "-": node) : "||";
            }
            prettyPrint+="\n";
        }
        return prettyPrint;
    }

    @Override
    public String toString() { //this prints out the
        String prettyPrint = "Binomial Heap: ";
        BinomialHeapNode<T> currentNode = mFirstRoot;
        while(currentNode!=null) {
            prettyPrint+= treePrinter(currentNode);
            currentNode=currentNode.rightSibling;
        }
        return prettyPrint;
    }
}
