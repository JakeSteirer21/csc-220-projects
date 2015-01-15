package prog04;

/** This is an implementation of the prog02.PhoneDirectory interface that uses
 *   a doubly linked list to store the data.
 *   @author vjm
 */
public class SortedDLLPD extends DLLBasedPD {
  /** Add an entry to the directory.
      @param name The name of the new person.
      @param number The number of the new person.
  */
  protected void add (String name, String number) {
	// EXERCISE
    // Call find to find where to put the entry.
	  DLLNode next = find(name).entry;
	// For a TEST try using new FindOutput(false, head).	  
    // What will be the next entry after this entry?	  
    // What will be the previous entry after this entry?
	  DLLNode previous;
    // Two cases:  entry goes last (no next) or not.	  
	  if(next == null)
		  previous = tail;
	  else
		  previous = next.getPrevious();
    // Allocate new entry.
	  DLLNode entry = new DLLNode(name, number);
    // Set next for the entry.
	  entry.setNext(next);
	  entry.setPrevious(previous);
    // Set previous for next, two cases (next is null or not).
	  if(previous == null)
		  head = entry;
	  else
		  previous.setNext(entry);
	// Ditto for previous.
	  if(next == null)
		  tail = entry;
	  else 
		  next.setPrevious(entry);
    modified = true;
  }

  /** Find an entry in the directory.
      @param name The name to be found.
      @return A FindOutput object describing the result.
  */
  protected FindOutput find (String name) {
    // EXERCISE
    // Modify find so it also stops when it gets to an entry after the
    // one you want.
	  int compare;
		  for(DLLNode entry = head; entry != null; entry = entry.getNext()) {
			  compare = entry.getKey().compareTo(name);
			  if (compare == 0)
				  // Return the appropriate FindOutput object.
				  return new FindOutput(true, entry);
			  
			  if (compare > 0)
				  return new FindOutput(false, entry);
		  }

    return new FindOutput(false, null);
  }
}
