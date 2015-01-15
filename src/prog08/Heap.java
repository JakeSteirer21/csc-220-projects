package prog08;
import java.util.*;

public class Heap <E> extends AbstractQueue<E> {
  private List<E> theData = new ArrayList<E>();

  public Heap () {}
  
  /** An optional reference to a Comparator object. */
  Comparator < E > comparator = null;

  /** Creates a heap-based priority queue with that orders its
       elements according to the specified comparator.
       @param comp The comparator used to order this priority queue
   */
  public Heap (Comparator < E > comp) {
    comparator = comp;
  }

  public int size () { return theData.size(); }

  /** Compare the items with index i and index j in theData using
      either a Comparator object’s compare method or their natural
      ordering using method compareTo.
      @param i index of first item in theData
      @param j index of second item in theData
      @return Negative int if first less than second,
              0 if first equals second,
              positive int if first > second
      @throws ClassCastException if items are not Comparable
   */
  private int compare (int i, int j) {
    if (comparator == null)
      return ((Comparable<E>) theData.get(i)).compareTo(theData.get(j));
    else
      return comparator.compare(theData.get(i), theData.get(j));
  }

  /** Swap the items with index i and index j in theData.
      @param i index of first item in theData
      @param j index of second item in theData
   */
  private void swap (int i, int j) {
    E temp = theData.get(i);
    theData.set(i, theData.get(j));
    theData.set(j, temp);
  }

  /** Insert an item into the priority queue.
      pre:  theData is in heap order.
      post: The item is in the priority queue and
            theData is in heap order.
      @param item The item to be inserted
      @throws NullPointerException if the item to be inserted is null.
   */
  public boolean offer (E item) {
    if (item == null)
      throw new NullPointerException();
    
    theData.add(item);

    int child = size() - 1;
    int parent = (child - 1) / 2;

    while (child > 0 && compare(child, parent) < 0) {
      swap(parent, child);
      child = parent;
      parent = (child - 1) / 2;
    }

    return true;
  }

  /** Remove an item from the priority queue
      pre: The ArrayList theData is in heap order.
      post: Removed smallest item, theData is in heap order.
      @return The item with the smallest priority value or null if empty.
   */
  public E poll() {
    // IMPLEMENT
	  
    // Return null if queue is empty.
	  if(size() == 0)
		  return null;
    // Save the top of the heap.
	  E topHeap = theData.get(0);
    // If only one item then remove it and you are done.
	  if(theData.size() == 1){
		  theData.remove(0);
		  return topHeap;
	  }
    /* Remove the last item from the ArrayList and place it into
       the first position. */
	  theData.remove(0);
	  theData.add(0, theData.remove(size()-1));
    /* Use swaps to move that item into the correct position. */
	  int parent = 0;
	  int leftChild = 2*parent+1;
	  int rightChild = 2*parent+2; 
	  while((leftChild < size() && compare(parent, leftChild) > 0) || (rightChild < size()  && compare(parent, rightChild) > 0 ) ){
		  if(rightChild < size() && leftChild < size()){
			  if(compare(leftChild, rightChild) > 0){
				  swap(parent, rightChild); } 
			  else { 
				  swap(parent, leftChild); }
		  } 
		  if(leftChild < size() && compare(parent, leftChild) > 0) 
			  swap(parent, leftChild);
		  if(rightChild < size() && compare(parent, rightChild) > 0) 
			  swap(parent, rightChild);
	  parent++;
	  leftChild = 2*parent+1;
	  rightChild = 2*parent+2; 
	  }  
  return topHeap;

    /* See the lab notes for a hint on this loop. */

}

  public E peek() {
		 if (theData.isEmpty())
			 return null;
		 return theData.get(0);
	 }
	 
	 public Iterator<E> iterator() {
		 return theData.iterator();
	 }
  
  
  /** Remove a specified item from the priority queue.
      If it appears more than once, only one is removed.
      @param o The item to be removed.
      @returns true if item remove, false if it was not there.
  */
  public boolean remove (Object o) {
    int index = theData.indexOf(o);
    if (index == -1)
      return false;

    // Remove the item at size-1 and place it at index.
    theData.set(index, theData.remove(size()-1));
    // If the item at index is better than its parent, swap it upward
    // as in offer.  Otherwise, swap it downward as in poll().
    int rightChild = 2*index+2;
	int leftChild = 2*index+1; 
    
    
    while(compare(index, (index-1)/2) < 0 && index > 0) {
    	swap((index-1)/2, index);
    }
    while(leftChild < theData.size() && compare(index, leftChild) > 0 || rightChild < theData.size()  && compare(index, rightChild) > 0 ) {
    if(rightChild < theData.size() && leftChild < theData.size()){
		  if(compare(leftChild, rightChild) > 0){
			  swap(index, rightChild); } 
		  else { 
			  swap(index, leftChild); }
	  } 
	 else{
	  if(leftChild < theData.size() && compare(index, leftChild) > 0) 
		  swap(index, leftChild);
	  if(rightChild < theData.size() && compare(index, rightChild) > 0) 
		  swap(index, rightChild);
	  }
	  index++;
	  rightChild = 2*index+2;
	  leftChild = 2*index+1;
    } 
    return true;
    
  }

  public String toString () {
    return toString(0, 0);
  }
  
  private String toString (int root, int indent) {
    if (root >= size())
      return "";
    String ret = toString(2 * root + 2, indent + 2);
    for (int i = 0; i < indent; i++)
      ret = ret + "  ";
    ret = ret + theData.get(root) + "\n";
    ret = ret + toString(2 * root + 1, indent + 2);
    return ret;
  }

  public static void main (String[] args) {
    int[] data = { 3, 1, 4, 1, 5, 9, 2, 6 };
    Heap<Integer> queue = new Heap<Integer>();
    
    for (int i = 0; i < data.length; i++) {
      queue.offer(data[i]);
      System.out.println(queue);
    }
    for (int i = 0; i < data.length; i++) {
      queue.poll();
      System.out.println(queue);
    }
  }
}

