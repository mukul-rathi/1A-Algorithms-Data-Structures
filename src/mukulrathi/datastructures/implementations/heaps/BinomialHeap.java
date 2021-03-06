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
        BinomialHeap<T> newHeap = new BinomialHeap<T>(newNode, mComp);
        newHeap.valToNode.put(x,newNode);
        merge(newHeap);
    }

    @Override
    public T first() throws UnderflowException {
        if(mFirstRoot==null) throw new UnderflowException();
        //find the minimum value out of the roots
        T minVal = mFirstRoot.value;
        BinomialHeapNode<T> currentRoot = mFirstRoot.rightSibling;
        while(currentRoot!=null){
            minVal = (mComp.compare(currentRoot.value,minVal)<0)?currentRoot.value:minVal;
            currentRoot = currentRoot.rightSibling;
        }
        return minVal;
    }

    @Override
    public T extractMin() throws UnderflowException {
        T returnVal = first();
        try {
            delete(first());
        } catch (ValueNotPresentException e) {//this will not get thrown since first() is minValue of heap
            e.printStackTrace();
        }
        return returnVal;
    }

    //this removes any node from the tree
    @Override
    public void delete(T x) throws ValueNotPresentException {
        if(!valToNode.keySet().contains(x)) throw new ValueNotPresentException();
        BinomialHeapNode<T> deleteNode = valToNode.get(x);
        //first we bubble up the node to the root of its tree
        while(deleteNode.parent!=null){
            //we swap the two - we do this by swapping values, and updating valToNode
           valToNode.put(deleteNode.value,deleteNode.parent);
           valToNode.put(deleteNode.parent.value,deleteNode);

            T temp = deleteNode.value;
            deleteNode.value = deleteNode.parent.value;
            deleteNode.parent.value = temp;
            deleteNode = deleteNode.parent; //go up one level of tree
        }
        BinomialHeapNode<T> deleteRoot = deleteNode; //we call it deleteRoot to emphasise it is a root now

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

            //update valToNode hashmap
            valToNode.remove(deleteRoot.value);

    }

    @Override
    public void decreaseKey(T x) throws ValueNotPresentException {
        if(!valToNode.keySet().contains(x)) throw new ValueNotPresentException();
        BinomialHeapNode<T> currentNode = valToNode.get(x);
        while(currentNode.parent!=null && mComp.compare(currentNode.value,currentNode.parent.value)<0){ //min-heap property violation
            //we swap the two - we do this by swapping values, and updating valtoNode
            valToNode.put(currentNode.value,currentNode.parent);
            valToNode.put(currentNode.parent.value,currentNode);

            T temp = currentNode.value;
            currentNode.value = currentNode.parent.value;
            currentNode.parent.value = temp;
            currentNode = currentNode.parent; //go up one level of tree
        }

    }
    private BinomialHeapNode<T> mergeTree(BinomialHeapNode<T> root1,BinomialHeapNode<T> root2 ) {
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
            if(root2==mFirstRoot || root2.leftSibling ==null ){
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
        return root1;
    }

    public void merge(BinomialHeap<T> otherQ) {

        //first we update HashMap accordingly
        valToNode.putAll(otherQ.valToNode);

        //next we create an Arraylist where the value at index i is a tree with root order i - null otherwise
        int maxOrder = 0;
        BinomialHeapNode<T> currentNode = mFirstRoot;
        while(currentNode!=null){
            maxOrder = (currentNode.order>maxOrder) ? currentNode.order : maxOrder;
            currentNode = currentNode.rightSibling;
        }
        if(otherQ!=null) {
            currentNode = otherQ.mFirstRoot;
        }
        while(currentNode!=null){
            maxOrder = (currentNode.order>maxOrder) ? currentNode.order : maxOrder;
            currentNode = currentNode.rightSibling;

        }
        //initialise arraylist with null values
        ArrayList<BinomialHeapNode<T>> rootArray = new ArrayList<BinomialHeapNode<T>>(maxOrder+1);
        for(int i=0; i<=maxOrder+1; i++){
            rootArray.add(null);
        }
        //now iterate through roots and merge if necessary
        currentNode = mFirstRoot;
        while(currentNode!=null){
            if(rootArray.get(currentNode.order)==null){
                rootArray.set(currentNode.order,currentNode);
            }
            else{
                BinomialHeapNode<T> tempNode = currentNode;
                while(rootArray.get(tempNode.order)!=null){
                    tempNode = mergeTree(tempNode,rootArray.get(tempNode.order));
                    rootArray.set(tempNode.order-1, null);
                    //now there is no node with same order as tempNode's original order, since it has been merged into a tree of order+1
                }
                rootArray.set(tempNode.order,tempNode);
            }
            currentNode = currentNode.rightSibling;
        }
        if(otherQ!=null) {
            currentNode = otherQ.mFirstRoot;
        }
        while(currentNode!=null){
            if(rootArray.get(currentNode.order)==null){
                rootArray.set(currentNode.order,currentNode);
            }
            else{
                BinomialHeapNode<T> tempNode = currentNode;
                while(rootArray.get(tempNode.order)!=null){
                    tempNode = mergeTree(tempNode,rootArray.get(tempNode.order));
                    rootArray.set(tempNode.order-1, null);
                    //now there is no node with same order as tempNode's original order, since it has been merged into a tree of order+1
                }
                rootArray.set(tempNode.order,tempNode);
            }
            currentNode = currentNode.rightSibling;
        }

        //now go through the arraylist and make this new root list
        mFirstRoot = null;
        currentNode = null;
        for(BinomialHeapNode<T> root : rootArray){
            if(root==null){ //ignore null values since they correspond to orders which are not present in tree.
                continue;
            }
            //first root we are adding to root list
            if (mFirstRoot==null){
                mFirstRoot = root;
                mFirstRoot.leftSibling = null;
                currentNode = root;
            }
            else{
                //add root to end of list
                currentNode.rightSibling = root;
                root.leftSibling = currentNode;
                currentNode = root;

            }
        }
        currentNode.rightSibling = null;


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
        String prettyPrint = "Binomial Heap: \n";
        BinomialHeapNode<T> currentNode = mFirstRoot;
        while(currentNode!=null) {
            prettyPrint+= treePrinter(currentNode);
            currentNode=currentNode.rightSibling;
        }
        return prettyPrint;
    }
}
