package prog05;

import java.util.EmptyStackException;

import prog02.DirectoryEntry;

/** Implementation of the interface StackInt<E> using an array.
*   @author vjm
*/

public class ArrayStack<E> implements StackInt<E> {
  // Data Fields
  /** Storage for stack. */
  E[] theData;
  /** Index to top of stack. */
  int size = 0; // Initially empty stack.
  private static final int INITIAL_CAPACITY = 2;

  /** Construct an empty stack with the default initial capacity. */
  public ArrayStack() {
    theData = (E[])new Object[INITIAL_CAPACITY];
  }

  /** Pushes an item onto the top of the stack and returns the item
      pushed.
      @param obj The object to be inserted.
      @return The object inserted.
   */
  public E push(E obj) {
    if (size == theData.length) {
      reallocate();
    }
    theData[size++] = obj;
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
    return theData[--size];
  }

/**** EXERCISE ****/
  
  public E peek() {
	  if(empty())
		  throw new EmptyStackException();
	  return theData[size-1];
  }
  
  public boolean empty() {
	if(size == 0)
		return true;
	return false;
  }
	protected void reallocate() {
		E[] newData =
				(E[]) new Object[2 * theData.length];
		System.arraycopy(theData, 0, newData, 0,
				theData.length);
		theData = newData;
	}
}
