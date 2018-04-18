package mukulrathi.unittests.trees;

import mukulrathi.customexceptions.BTreeNodeFullException;
import mukulrathi.customexceptions.BTreeNodeUnderFlowException;
import mukulrathi.customexceptions.KeyNotFoundException;
import mukulrathi.customexceptions.LeafDepthException;
import mukulrathi.datastructures.implementations.trees.BTree;
/*
    This class contains the methods to check if the B-Tree invariants hold
 */

public class BTreeUnitTests<K extends Comparable<K>, V> extends BTree<K,V> {
    public BTreeUnitTests(int minDegree) {
        super(minDegree);
    }

    private boolean internalKeysInOrder(BTreeNode<K,V> node){
        //check if keys in non-decreasing order for every node in the subtree rooted at this node
        // i.e for all i,  key i <= key i+1
       for(int i=0;i<node.keys.size()-1;i++) {
           if (node.keys.get(i).compareTo(node.keys.get(i + 1)) > 0) { //key i > key i+1 - violation!
               return false;
           }
       }
       if(node.isLeaf){ //no more nodes in this subtree since leaf so property holds
           return true;
       }
       else{
           for(BTreeNode<K,V> child: node.children){
               if(!internalKeysInOrder(child)) return false; //subtree rooted at child violates the property so this subtree also violates property
           }
           return true;
       }
   }

   private boolean childKeysInOrder(BTreeNode<K,V> node){
        //this checks if keys in node separate the ranges of keys stored in each subtree
       // i.e. for all i key i-1 <= any key in child i subtree <=key i
        if(node.isLeaf){
            return true; // no children so clearly true
        }
        else if(node.keys.size()==0){ //0 keys, 1 child so clearly true
            return true;
        }
        else{
            //NB if internalKeysInOrder holds, then it suffices to
            //only check smallest and largest values' validity in child subtree

            BTreeNode<K,V> minChildNode;
            K minChildKey;
            BTreeNode<K,V> maxChildNode = node.children.get(0);
            K maxChildKey;


            //compare first child subtree with first key

            while(!maxChildNode.isLeaf){
                //keep getting right-most child since that has maximal key
                maxChildNode = maxChildNode.children.get(maxChildNode.children.size()-1);
            }
            maxChildKey = maxChildNode.keys.get(maxChildNode.keys.size()-1); //largest key
            if(maxChildKey.compareTo(node.keys.get(0))>0){
                return false;
            }
            for(int i=0; i<node.keys.size()-1;i++){
                //compare each child subtree with the key preceding and following it


                //calculate min key by traversing left-most subtree
                minChildNode = node.children.get(i+1);
                while(!minChildNode.isLeaf){
                    //keep get left-most child since that has minimal key
                    minChildNode = minChildNode.children.get(0);
                }
                minChildKey = minChildNode.keys.get(0); //get minimum key of node


                maxChildNode = node.children.get(i+1);
                while(!maxChildNode.isLeaf){
                    //keep get right-most child since that has maximal key
                    maxChildNode = maxChildNode.children.get(maxChildNode.children.size()-1);
                }
                maxChildKey = maxChildNode.keys.get(maxChildNode.keys.size()-1);//get maximum key of node

                if(maxChildKey.compareTo(node.keys.get(i+1))>0 || (minChildKey.compareTo(node.keys.get(i))<0)){
                    //i.e property violated since child keys not in range
                    return false;
                }
            }

            //compare last child subtree with last key
            minChildNode = node.children.get(node.children.size()-1);
            while(!minChildNode.isLeaf){
                //keep get left-most child since that has minimal key
                minChildNode = minChildNode.children.get(0);
            }
            minChildKey = minChildNode.keys.get(0); //get minimum key of node

            if(minChildKey.compareTo(node.keys.get(node.keys.size()-1))<0){
                //i.e property violated since child keys not in range
                return false;
            }



            //check property holds for child subtrees too
            for(BTreeNode<K,V> child: node.children){
                if(!childKeysInOrder(child)) return false; //subtree rooted at child violates the property so this subtree also violates property
            }
            return true;


        }
   }

   private boolean isLeafCheck(BTreeNode<K,V> node){
        //this checks whether the isLeaf attribute of the nodes in subtree is indeed correct, i.e leaf nodes have no children
        if(node.isLeaf){
            return (node.children==null);
        }
        else{
            if(node.children==null){ //should be a leaf
                return false;
            }
            else{
                for(BTreeNode<K,V> child: node.children){
                    if(!isLeafCheck(child)) return false; //subtree rooted at child violates the property so this subtree also violates property
                }
                return true;
            }
        }
   }

   private boolean numChildrenCorrect(BTreeNode<K,V> node){
       //this checks that all non-leaf nodes in subtree have c children iff they have c-1 keys
       if(node.isLeaf){
           return true; //property clearly holds since unaffected by leaf node
       }
       if((node.keys.size()+1)!=node.children.size()){ //property violated
           return false;
       }
       else{
           for(BTreeNode<K,V> child: node.children){
               if(!numChildrenCorrect(child)) return false; //subtree rooted at child violates the property so this subtree also violates property
           }
           return true;
       }

   }

   private boolean leafDepthSame(){
       //this checks that all leaves have the same depth
       try {
           getNodeDepth(mRoot);
           return true;
       } catch (LeafDepthException e) {
            return false;
       }
   }

   private int getNodeDepth(BTreeNode<K,V> node) throws LeafDepthException {
       //this is a helper function that calculates the depth of a node
       if(node.isLeaf){
           return 0;
       }
       else{
           int childDepth = getNodeDepth(node.children.get(0));
           for(BTreeNode<K,V> child: node.children){
               if(childDepth!=getNodeDepth(child))throw new LeafDepthException(); //the tree is not balanced as children have different depths
           }
           return 1 +childDepth;
       }
   }

   private boolean nodeDegreeBound(BTreeNode<K,V> node){
       //this checks whether the number of keys is in correct bound for all nodes in subtree
       // t = minDegree
       // t-1<= num keys <= 2t-1, apart from root node - 1<= num keys <= 2t-1

        if((node.keys.size()> (2*mMinDegree-1))){
            return false;
       }
       if(node!=mRoot&&(node.keys.size()<(mMinDegree-1)) ){
            return false;
       }

       //again, if it is a leaf we are done, else we check through subtrees rooted at children
       if(node.isLeaf){
            return true;
       }
       else{
           for(BTreeNode<K,V> child: node.children){
               if(!numChildrenCorrect(child)) return false; //subtree rooted at child violates the property so this
               //subtree also violates property
           }
           return true;
       }
   }
    private boolean sameNumKeysVals(BTreeNode<K,V> node){
       //this checks whether we have same number of keys and values in each node - i.e no unpaired keys/values
       if(node.keys.size()!=node.values.size()){
           return false;
       }
        //again, if it is a leaf we are done, else we check through subtrees rooted at children
        if(node.isLeaf){
            return true;
        }
        else{
            for(BTreeNode<K,V> child: node.children){
                if(!sameNumKeysVals(child)) return false; //subtree rooted at child violates the property so this
                //subtree also violates property
            }
            return true;
        }

    }
    private boolean unitTestPass(){
        if (mRoot==null) return true;
        else {
            return internalKeysInOrder(mRoot) && childKeysInOrder(mRoot) && isLeafCheck(mRoot) && numChildrenCorrect(mRoot)
                    && leafDepthSame() && nodeDegreeBound(mRoot)&&sameNumKeysVals(mRoot);
        }
    }

    private String unitTestResult(){

        String results = "\n UNIT TESTS:  \n";
        results+="Internal keys in order? " + internalKeysInOrder(mRoot) + "\n";
        results+="Child keys in order? " + childKeysInOrder(mRoot) + "\n";
        results+="Leaf checks correct? " + isLeafCheck(mRoot) + "\n";
        results+="Number of children correct? " + numChildrenCorrect(mRoot) + "\n";
        results+="Leaf depth same? " + leafDepthSame() + "\n";
        results+="Node degrees bounded? " + nodeDegreeBound(mRoot) + "\n";
        results+="Same number of keys and values? " + sameNumKeysVals(mRoot) + "\n";

        return results;

    }

    @Override
    public void set(K k, V v){
        System.out.print((unitTestPass())?"": ("Pre: Set error:" + unitTestResult()));
        super.set(k,v);
        System.out.print((unitTestPass())?"": ("Post: Set error:" + unitTestResult()));
    }
    @Override
    public void delete(K k) throws KeyNotFoundException {
        System.out.print((unitTestPass())?"": ("Pre: Delete error:" + unitTestResult()));
        super.delete(k);
        System.out.print((unitTestPass())?"": ("Post: Delete error:" + unitTestResult()));
    }
    @Override
    public V get(K k) throws KeyNotFoundException {
        System.out.print((unitTestPass())?"": ("Pre: Get error:" + unitTestResult()));
        V ans = super.get(k);
        System.out.print((unitTestPass())?"": ("Post: Get error:" + unitTestResult()));
        return ans;
    }

    @Override
    protected void BTreeSplitChild(BTreeNode<K,V> node,int i) throws BTreeNodeFullException, BTreeNodeUnderFlowException {
        System.out.print((unitTestPass())?"": ("Pre: SplitChild error:" + unitTestResult()));
        super.BTreeSplitChild(node,i);
        System.out.print((unitTestPass())?"": ("Post: SplitChild error:" + unitTestResult()));
    }
    @Override
    protected void BTreeInsertNonFull(BTreeNode<K,V> node,K k, V v) throws BTreeNodeFullException {
        System.out.print((unitTestPass())?"": ("Pre: InsertNonFull error:" + unitTestResult()));
        super.BTreeInsertNonFull(node,k, v);
        System.out.print((unitTestPass())?"": ("Post: InsertNonFull error:" + unitTestResult()));

    }
    @Override
    protected void BTreeNodeMerge(BTreeNode<K,V> node,int i) throws LeafDepthException, BTreeNodeFullException, BTreeNodeUnderFlowException {
        System.out.print((unitTestPass())?"": ("Pre: Merge error:" + unitTestResult()));
        super.BTreeNodeMerge(node,i);
        System.out.print((unitTestPass())?"": ("Post: Merge error:" + unitTestResult()));
    }
    @Override
    protected void BTreeLeftRotate(BTreeNode<K,V> node,int i) throws BTreeNodeUnderFlowException {
        System.out.print((unitTestPass())?"": ("Pre: LeftRotate error:" + unitTestResult()));
        super.BTreeLeftRotate(node,i);
        System.out.print((unitTestPass())?"": ("Post: LeftRotate error:" + unitTestResult()));
    }
    @Override
    protected void BTreeRightRotate(BTreeNode<K,V> node,int i) throws BTreeNodeUnderFlowException {
        System.out.print((unitTestPass())?"": ("Pre: RightRotate error:" + unitTestResult()));
        super.BTreeRightRotate(node,i);
        System.out.print((unitTestPass())?"": ("Post: RightRotate error:" + unitTestResult()));
    }

    @Override
    protected void BTreeDeleteNonEmpty(BTreeNode<K,V> node,K k) throws BTreeNodeUnderFlowException, KeyNotFoundException {
        System.out.print((unitTestPass())?"": ("Pre: DeleteNonEmpty error:" + unitTestResult()));
        super.BTreeDeleteNonEmpty(node,k);
        System.out.print((unitTestPass())?"": ("Post: DeleteNonEmpty error:" + unitTestResult()));
    }


}
