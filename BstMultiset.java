import java.io.PrintStream;

public class BstMultiset<T> extends Multiset<T> 
{
	protected Node root;

	public BstMultiset() {
		root=new Node(null, 0);
	} // end of BstMultiset()

	public void add(T item) {
		insert(root,item);
	} // end of add()

	private void insert(Node currNode, T item){
		if(currNode.getValue()==null){
			currNode.setValue(item);
			currNode.setInstances(1);
			currNode.setLeft(new Node(null,0));
			currNode.setRight(new Node(null,0));
			return;
		}	
		int compare = currNode.compareTo(item);
		if(compare==0){
			currNode.setInstances(currNode.getInstances()+1);
			return;
		}
		if(compare>0)
			insert(currNode.getRight(),item);
		if(compare<0)
			insert(currNode.getleft(),item);
	}

	/** Search value in the tree.
	 * If value is found return node depth*/
	public int search(T item) {
		return bstSearch(root,item);

	} // end of search()

	private int bstSearch(Node currNode, T item){
		int compare = currNode.compareTo(item);
		if(compare==0)
			return currNode.getInstances();
		if(compare>0)
			return bstSearch(currNode.getRight(),item);
		if(compare<0)
			return bstSearch(currNode.getleft(),item);
		return 0;
	}


	public void removeOne(T item) {
		del(root,item, false);
	} // end of removeOne()


	public void removeAll(T item) {
		del(root,item, true);
	} // end of removeAll()


	public void print(PrintStream out) {
		printNode(out , root);
	} // end of print()
	private void printNode(PrintStream out , Node currNode){
		if(currNode.getValue() != null) {
			out.println(currNode.getValue() + printDelim + currNode.getInstances());
			printNode(out , currNode.getleft());
			printNode(out , currNode.getRight());

		}
	}

	private void del(Node currNode, T item, Boolean ra){
		
			if(currNode!=null){	
				T right=null;
				T left=null;

			int compare = currNode.compareTo(item);
			Node rightNode = currNode.getRight();
			if(rightNode!=null)
			right = rightNode.getValue();
			Node leftNode = currNode.getleft();
			if(leftNode!=null)
			left = leftNode.getValue();
			if(compare==0){
				if(right==null && left==null) {
					delNoChild(currNode, ra);
					return;
				}
				if((right==null || left==null)){
					delOneChild(currNode, ra);
					return;
				}
				if(left!=null && right!=null){
					delTwoChild(currNode, ra);
					return;
				}
				return;
			}

      if(compare>0 && right != null){
        del(rightNode,item, ra);
        return;
      }
      if(compare<0 && left != null){
        del(leftNode,item, ra);
        return;
      }
			} else 
				return;
	}

	private void delNoChild(Node currNode, Boolean ra){
		if(ra || currNode.getInstances()==1)
			currNode.setValue(null);	
		else
			currNode.setInstances(currNode.getInstances()-1);
	}

	private void delOneChild(Node currNode, Boolean ra){
		Node tempNode;

		if(ra || currNode.getInstances()==1){
			if(currNode.getRight().getValue()!=null){
				tempNode = currNode.getRight();
				currNode.setValue(tempNode.getValue());
				currNode.setInstances(tempNode.getInstances());
				currNode.setRight(tempNode.getRight());	
				currNode.setLeft(tempNode.getleft());
			} else{
				tempNode = currNode.getleft();
				currNode.setValue(tempNode.getValue());
				currNode.setInstances(tempNode.getInstances());
				currNode.setRight(tempNode.getRight());	
				currNode.setLeft(tempNode.getleft());	
			}
		}
		else
			currNode.setInstances(currNode.getInstances()-1);
	}


	private void delTwoChild(Node currNode, Boolean ra){
		if(ra || currNode.getInstances()==1){
			Node smallest = searchSmallest(currNode.getRight());
			currNode.setValue(smallest.getValue());	
			currNode.setInstances(smallest.getInstances());
			smallest.setValue(null);
			smallest.setInstances(0);
		}
		else
			currNode.setInstances(currNode.getInstances()-1);
	}

	private Node searchSmallest(Node currNode){
		if(currNode.getleft().getValue()!=null){
			currNode = currNode.getleft();
			searchSmallest(currNode);
		}
		return currNode;
	}



	/**
	 * @name Node
	 * @type Class
	 * @descr Class which represents the individual elements which constitute a multiset
	 * @author s3586372 Rahul Raghavan, s3572164 Farid Farzin
	 * @created 10-08-2017
	 *
	 */ 
	private class Node 
	{
		/** The value of the Node */
		private T nValue;
		/** The reference to the right node */
		private Node nRight;
		/** The reference to the left node */
		private Node nLeft;
		/** The number of instances of the value in the multiset */
		private int nInstances;

		/** constructor */
		public Node(T item, int instances) {
			nValue = item;
			nRight = null;
			nLeft = null;
			nInstances = instances;
		}
		public T getValue() {
			return nValue;
		}
		public Node getRight() {
			return nRight;
		}
		public Node getleft() {
			return nLeft;
		}
		public int getInstances() {
			return nInstances;
		}
		public void setValue(T val) {
			nValue = val;
		}
		public void setRight(Node right) {
			nRight = right;
		}
		public void setLeft(Node left) {
			nLeft = left;
		}
		public void setInstances(int inst) {
			nInstances = inst;
		}

		public int compareTo(T o) {
			//if mValue > o return 1
			//if mValue < o return -1
			//if mValue = o return 0
			int output=0;
			if(nValue==null)
				return 0;
			int input = ((Comparable)nValue).compareTo(o);
			if(input==0)
				return 0;
			if(input>0)
				return -1;
			if(input<0)
				return 1;
			return 0;
		}
	}

} // end of class BstMultiset
