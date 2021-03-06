import java.io.PrintStream;
import java.util.*;

public class SortedLinkedListMultiset<T extends Comparable<T>> extends Multiset<T>
{
	/** A reference to the node at the head of the linked list */
	protected Node mHead;
	/** A reference to the node at the tail of the linked list */
	protected Node mTail;
	/** The length of the linked list */
	protected int mLength;

	public SortedLinkedListMultiset() {
		// Implement me!
		mHead = null;
		mTail = null;
		mLength = 0;
	} // end of SortedLinkedListMultiset()


	public void add(T item) {
		// Implement me!
		Node currNode = mHead;
		Node newNode = new Node(item, 1);
		if (mLength == 0) {
			mHead = newNode;
			mTail = newNode;
			mLength++;
		}
		else {
			if(mLength == 1) {
				T val = currNode.getValue();
				if(val.equals(item)) {
					currNode.setInstances(currNode.getInstances()+1);
					return;
				}
				else if(val.compareTo(item) < 0) {
					currNode.setNext(newNode);
					newNode.setPrev(currNode);
					mTail = newNode;
					mLength++;
					return;
				}
				else {
					currNode.setPrev(newNode);
					newNode.setNext(currNode);
					mHead = newNode;
					mLength++;
					return;
				}
			}
			else {
				currNode = mHead;
				for (int i =0; i < mLength; ++i) {
					if(currNode.getValue().equals(item)) {
						currNode.setInstances(currNode.getInstances()+1);
						return;
					}
					else if (currNode.getNext() == null) {
						currNode.setNext(newNode);
						newNode.setPrev(currNode);
						mTail = newNode;
						mLength++;
						return;
					}
					else if(currNode.getValue().compareTo(item) < 0 && currNode.getNext().getValue().compareTo(item) > 0) {
						newNode.setNext(currNode.getNext());
						newNode.setPrev(currNode);
						currNode.setNext(newNode);
						mLength++;
						return;
					}
					currNode = currNode.getNext();
				}
			}
		}
	} // end of add()

	private Node searchNode(T item) {

		Node currNode = mHead;
		if (mHead == null)
			return null;
		int middle = (int)((double)mLength/2.0);
		if (item instanceof Number) {
			double avg = (((Number)mTail.getValue()).doubleValue() + ((Number)mHead.getValue()).doubleValue())/2;
			if (((Number)item).doubleValue() > avg) {
				// Search to the middle from the head
				for (int i =0; i < middle; ++i) {
					if(currNode.getValue().equals(item)) {
						return currNode;	
					}
					else if (currNode.getValue().compareTo(item) < 0) {
						// Item does not exist in multiset
						return null;
					}
					currNode = currNode.getNext();
				}
			}
			else {
				// Search to the middle from the tail
				currNode = mTail;
				for (int i = mLength-1; i >= middle; --i) {
					if(currNode.getValue().equals(item)) {
						return currNode;
					}
					else if (currNode.getValue().compareTo(item) > 0) {
						// Item does not exist in multiset
						return null;
					}
					currNode = currNode.getPrev();
				}
			}		
		}
		else {
			// Regular Search	
			currNode = mHead;
			while(currNode.getNext()!=null && !(currNode.getValue().compareTo(item)==0)){
				if (currNode.getValue().compareTo(item) > 0) {
					return null;
				}
				currNode=currNode.getNext();
			}
			if(currNode.getValue().compareTo(item)==0)
				return currNode;
		}
		return null;
	}


	public int search(T item) {
		Node result = searchNode(item);
		if (result == null) {
			return 0;
		}
		else {
			return result.getInstances();
		}
	} // end of add()


	public void removeOne(T item) {
		Node currNode = searchNode(item);
		Node next = null;

		if(currNode != null && currNode.getValue().compareTo(item)==0){
			if(currNode.getInstances()!=1)
				currNode.setInstances(currNode.getInstances()-1); 
			else{
				if(mHead.getValue().compareTo(item)==0){
					if(mHead.getNext()!=null){
						mHead=mHead.getNext();
						if(mHead.getPrev()!=null)
							mHead.setPrev(null);
					} else 
						mHead = null;
				}else{
					next = currNode.getNext();
					currNode = currNode.getPrev();
					currNode.setNext(next);
					if(next!=null && currNode.getPrev()!=null)
						currNode.getNext().setPrev(currNode);
				}
			}
		}

	} // end of removeOne()


	public void removeAll(T item) {
		Node currNode = mHead;
		Node next=null;
		if (mHead != null) {
			if(mHead.getValue().compareTo(item)==0){
				if(mHead.getNext()!=null){
					mHead=mHead.getNext();
					if(mHead.getPrev()!=null)
						mHead.setPrev(null);
				} else 
					mHead = null;
			}
			else{
				while(currNode != null && !(currNode.getValue().compareTo(item)==0)){
					currNode=currNode.getNext();
				}
				if(currNode != null && currNode.getValue().compareTo(item)==0){
					next = currNode.getNext();
					currNode = currNode.getPrev();
					currNode.setNext(next);
					if(next!=null && currNode.getPrev()!=null)
						currNode.getNext().setPrev(currNode);
				}
			}
		}


	} // end of removeAll()


	/**
	 * @name print
	 * @type Method 
	 * @descr Print a string representation of the multiset
	 * @return String representation of the multiset
	 */ 	
	public void print(PrintStream out) {
		// Implement me!
		Node currNode = mHead;
		while(currNode != null) {
			out.println(currNode.getValue() + printDelim + currNode.getInstances());
			currNode = currNode.getNext();
		}
	} // end of print()

	/**
	 * @name Node
	 * @type Class
	 * @descr Class which represents the individual elements which constitute a multiset
	 * @author s3586372 Rahul Raghavan, Farid Farzin
	 * @created 10-08-2017
	 */                                                                                                 
	private class Node
	{
		/** The value of the Node */
		private T mValue;
		/** The reference to the next node */
		private Node mNext;
		/** The reference to the previous node */
		private Node mPrev;
		/** The number of instances of the value in the multiset */
		private int mInstances;

		/** constructor */
		public Node(T item, int instances) {
			mValue = item;
			mNext = null;
			mPrev = null;
			mInstances = instances;
		}
		public T getValue() {
			return mValue;
		}
		public Node getNext() {
			return mNext;
		}
		public Node getPrev() {
			return mPrev;
		}
		public int getInstances() {
			return mInstances;
		}
		public void setValue(T val) {
			mValue = val;
		}
		public void setNext(Node next) {
			mNext = next;
		}
		public void setPrev(Node prev) {
			mPrev = prev;
		}
		public void setInstances(int inst) {
			mInstances = inst;
		}
	}


} // end of class SortedLinkedListMultiset
