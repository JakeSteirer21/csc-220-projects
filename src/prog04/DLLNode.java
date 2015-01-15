package prog04;

/** Entry in doubly linked list
 */
public class DLLNode {
  /** The name of the individual or whatever is used to look up an entry. */
  private String key;

  /** The number of individual or whatever value is stored for that entry. */
  private String value;

  /** The next entry in the list. */
  private DLLNode next;

  /** The previous entry in the list. */
  private DLLNode previous;

  /** Creates a new instance of DLLNode
      @param key Name of person.
      @param value Number of person.
  */
  public DLLNode (String key, String value) {
    this.key = key;
    this.value = value;
  }

  /** Retrieves the key
      @return key The name of the individual.
  */
  public String getKey () {
    return key;
  }
    
  /** Retrieves the value
      @return value The number of the individual.
  */
  public String getValue () {
    return value;
  }
  
  /** Sets the number to a different value.
      @param valueh The new number to set it to.
  */
  public void setValue (String value) {
    this.value = value;
  }

  /** Gets the next entry in the list.
      @return The next entry.
  */
  public DLLNode getNext () {
    return next;
  }

  /** Sets the next entry in the list.
      @param next The new next entry.
  */
  public void setNext (DLLNode next) {
    this.next = next;
  }

  /** Gets the previous entry in the list.
      @return The previous entry.
  */
  public DLLNode getPrevious () {
    return previous;
  }

  /** Sets the previous entry in the list.
      @param previous The new previous entry.
  */
  public void setPrevious (DLLNode previous) {
    this.previous = previous;
  }
}


