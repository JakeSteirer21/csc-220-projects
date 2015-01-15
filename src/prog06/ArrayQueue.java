package prog06;

import java.util.*;

/**
 * Implements the Queue interface using a circular array.
 **/
public class ArrayQueue<E> extends AbstractQueue<E>
        implements Queue<E> {

    // Data Fields
    /** Index of the front of the queue. */
    private int front;
    /** Index of the rear of the queue. */
    private int rear;
    /** Current size of the queue. */
    private int size;
    /** Default capacity of the queue. */
    private static final int DEFAULT_CAPACITY = 10;
    /** Array to hold the items. */
    private E[] theItems;

    // Constructors
    /**
     * Construct a queue with the default initial capacity.
     */
    public ArrayQueue () {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Construct a queue with the specified initial capacity.
     * @param initCapacity The initial capacity
     */
    @SuppressWarnings("unchecked")
    public ArrayQueue (int initCapacity) {
        theItems = (E[]) new Object[initCapacity];
        front = 0;
        rear = theItems.length - 1;
        size = 0;
    }

    // Public Methods
    /**
     * Inserts an item at the rear of the queue.
     * @post item is added to the rear of the queue.
     * @param item The element to add
     * @return true (always successful)
     */
    @Override
    public boolean offer (E item) {
        if (size == theItems.length)
            reallocate();
        size++;
        rear = (rear + 1) % theItems.length;
        theItems[rear] = item;
        
        return true;
    }

    /**
     * Returns the item at the front of the queue without removing it.
     * @return The item at the front of the queue if successful;
     * return null if the queue is empty
     */
    @Override
    public E peek () {
        if (size == 0)
            return null;
        return theItems[front];
    }

    /**
     * Removes the entry at the front of the queue and returns it
     * if the queue is not empty.
     * @post front references item that was second in the queue.
     * @return The item removed if successful or null if not
     */
    @Override
    public E poll () {
      // EXERCISE 3
    	E item = theItems[front];
    	theItems[front] = null;
    	size--;
    	front = (front + 1) % theItems.length;
    	return item;
    }

/**
 * Return the size of the queue
 * @return The number of items in the queue
 */
    @Override
    public int size () {
      return size;
    }
    /**
     * Returns an iterator to the elements in the queue
     * @return an iterator to the elements in the queue
     */
    @Override
    public Iterator<E> iterator () {
      return new Iter();
    }
    
    // Private Methods
    /**
     * Double the capacity and reallocate the items.
     * @pre The array is filled to capacity.
     * @post The capacity is doubled and the first half of the
     *       expanded array is filled with items.
     */
    @SuppressWarnings("unchecked")
    private void reallocate () {
        int newCapacity = 2 * theItems.length;
        E[] newItems = (E[]) new Object[newCapacity];
        // int j = front;
        
        System.arraycopy(theItems, front, newItems, 0, size - front);
        System.arraycopy(theItems, 0, newItems, size - front, front);
        
      /* for (int i = 0; i < size; i++) {
            newItems[i] = theItems[j];
            j = (j + 1) % theItems.length;
        }
      */
        front = 0;
        rear = size - 1;
        theItems = newItems;
    }

    private boolean labSolution = false;

    /** Inner class to implement the Iterator<E> interface. */
    private class Iter implements Iterator<E> {
        // Data Fields

        // Index of next element */
        private int index;

        // Count of elements accessed so far */
        private int count = 0;

        // Methods
        // Constructor
        /**
         * Initializes the Iter object to reference the
         * first queue element.
         */
        public Iter () {
          if (labSolution) {
            index = front;
          }
          else {
            // EXERCISE
        	  if(size == 0)
        		  index = -1;
        	  else
        		  index = front;
          }
        }

        /**
         * Returns true if there are more elements in the queue to access.
         * @return true if there are more elements in the queue to access.
         */
        @Override
        public boolean hasNext () {
          if (labSolution)
            return count < size;
          else {
            // EXERCISE
        	  if(index == -1)
        		  return false; 
        	  return true;
          }
        }

        /**
         * Returns the next element in the queue.
         * @pre index references the next element to access.
         * @post index and count are incremented.
         * @return The element with subscript index
         */
        @Override
        public E next () {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E returnValue = theItems[index];
            if (labSolution) {
              index = (index + 1) % theItems.length;
              count++;
            }
            else {
              // EXERCISE
            	if(index == rear)
            		index = -1;
            	else
            		index = (index + 1) % theItems.length;
            }
            return returnValue;
        }

        /**
         * Remove the item accessed by the Iter object -- not implemented.
         * @throws UnsupportedOperationException when called
         */
        @Override
        public void remove () {
            throw new UnsupportedOperationException();
        }
    }
}
