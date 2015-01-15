package prog05;

import java.util.EmptyStackException;

/** Class to implement interface StackInt<E> as a linked list.
*   @author vjm
* */

public class LinkedStack<E> implements StackInt<E> {

  /** A Node is the building block for a single-linked list. */
  private static class Node < E > {
    // Data Fields
    /** The reference to the data. */
    private E data;

    /** The reference to the next node. */
    private Node next;

    // Constructors
    /** Creates a new node with a null next field.
        @param dataItem The data stored
     */
    private Node(E dataItem) {
      data = dataItem;
      next = null;
    }

    /** Creates a new node that references another node.
        @param dataItem The data stored
        @param nodeRef The node referenced by new node
     */
    private Node(E dataItem, Node < E > nodeRef) {
      data = dataItem;
      next = nodeRef;
    }
  } //end class Node

  // Data Fields
  /** The reference to the first stack node. */
  private Node<E> head = null;

  /** Pushes an item onto the top of the stack and returns the item
      pushed.
      @param obj The object to be inserted.
      @return The object inserted.
   */
  public E push(E obj) {
    head = new Node<E>(obj, head);
    return obj;
  }

  /** Returns the object at the top of the stack and removes it.
      post: The stack is one item smaller.
      @return The object at the top of the stack.
      @throws EmptyStackException if stack is empty.
   */
  public E pop() {
    if (empty()) {
      throw new EmptyStackException();
    }
    else {
	// IMPLEMENT THIS
    	E data = head.data;
    	head = head.next;
    return data;
    }
  }

  /** Returns the object at the top of the stack without removing it.
      post: The stack remains unchanged.
      @return The object at the top of the stack.
      @throws EmptyStackException if stack is empty.
   */
  public E peek() {
    if (empty()) {
      throw new EmptyStackException();
    }
    else {
	// IMPLEMENT THIS
	return head.data;
    }
  }

  /** Returns true if the stack is empty; otherwise, returns false.
      @return true if the stack is empty.
   */
  public boolean empty() {
    return head == null;
  }
}
