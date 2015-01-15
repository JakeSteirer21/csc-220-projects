package prog07;
import java.util.*;

public class DummyList <K extends Comparable<K>, V>
  extends AbstractMap<K, V> {

  private static class Node <K extends Comparable<K>, V>
    implements Map.Entry<K, V> {

    K key;
    Object valueOrDown;
    Node<K, V> next;
    
    Node () {}

    Node (K key, V value, Node<K, V> next) {
      this.key = key;
      this.valueOrDown = value;
      this.next = next;
    }
    
    Node (K key, Node<K, V> down, Node<K, V> next) {
      this.key = key;
      this.valueOrDown = down;
      this.next = next;
    }
    
    boolean bottom () {
      return valueOrDown == null || !(valueOrDown instanceof Node);
    }

    public K getKey () { return key; }
    public V getValue () { return (V) valueOrDown; }
    public V setValue (V newValue) {
      V oldValue = getValue();
      valueOrDown = newValue;
      return oldValue;
    }

    Node<K, V> getDown () { return (Node<K,V>) valueOrDown; }
  }
  
  private Node<K, V> dummy = new Node<K,V>();
  private int size;
  
  boolean heads () { return Math.random() < 0.5; }

  /**
   * Find the node with the given key.
   * @param key The key to be found.
   * @param from The node from which to start the search.
   * @return The node with that key.
   */
  private Node<K, V> find (K key, Node<K, V> from) {
    while (from.next != null && key.compareTo(from.next.key) > 0)
      from = from.next;

    if (from.next != null && key.equals(from.next.key))
      return from.next;
    else
      return null;
  }
  
  /**
   * Add a new key value to the sorted list, using from as the starting point.
   * @param key The new key.
   * @param value The new value.
   * @param from The node from which to start the search.
   * @return The new node.
   */
  private Node<K, V> add (K key, V value, Node<K, V> from) {
    // EXERCISE
	  while (from.next != null && key.compareTo(from.next.key) > 0)
		  from = from.next;
	  
	  Node<K, V> entry = new Node<K, V>(key, value, from.next);
	  from.next = entry;
	  return entry;
  }
    
  /**
   * Remove the node with a given key, if it is there, starting from from.
   * @param key The key to be removed.
   * @param from The node from which to start the search.
   * @return The value being removed, if it is there, or null.
   */
  private V remove (K key, Node<K, V> from) {
    // EXERCISE
    // Don't forget to decrement size if you actually remove a node.
	  while (from.next != null && key.compareTo(from.next.key) > 0)
		  from = from.next;
	  
	  if(from.next == null || !key.equals(from.next.key))
		  return null;
	  
	  Node<K, V> entry = from.next;
	  from.next = entry.next;
	  entry.next = null;
	  size--;
	  return entry.getValue();
  }

  public V remove (Object keyAsObject) {
    K key = (K) keyAsObject;
    V value = remove(key, dummy);
    return value;
  }

  public boolean containsKey (Object key) {
    return find((K) key, dummy) != null;
  }
  
  public V get (Object key) {
    Node<K, V> node = find((K) key, dummy);
    if (node != null)
      return node.getValue();
    return null;
  }
  
  public boolean isEmpty () { return size == 0; }
  
  public V put (K key, V value) {
    // EXERCISE
	  Node<K, V> entry = find(key, dummy); 
	  if(entry == null) {
		  add(key, value, dummy);
		  size++;
		  return null;
	  }
	  else {
		  V oldValue = entry.getValue();
		  entry.setValue(value);
		  return oldValue;
	  }
  }      
  
  private static class Iter<K extends Comparable<K>, V> implements Iterator<Map.Entry<K, V>> {
    Node<K, V> node;
    
    Iter (Node<K, V> dummy) {
      node = dummy;
    }
    
    public boolean hasNext () {
      return node.next != null;
    }
    
    public Map.Entry<K, V> next () {
      node = node.next;
      return node;
    }
    
    public void remove () {
      throw new UnsupportedOperationException();
    }
  }
  
  private class Setter extends AbstractSet<Map.Entry<K, V>> {
    public Iterator<Map.Entry<K, V>> iterator () {
      return new Iter(dummy);
    }
    
    public int size () { return size; }
  }
  
  public Set<Map.Entry<K, V>> entrySet () { return new Setter(); }
  
  public static void main (String[] args) {
    Map<String, Integer> map = new DummyList<String, Integer>();
    
    map.put("Victor", 50);
    map.put("Irina", 45);
    map.put("Lisa", 47);
    
    for (Map.Entry<String, Integer> pair : map.entrySet())
      System.out.println(pair.getKey() + " " + pair.getValue());
    
    System.out.println(map.get("Irina"));
    map.remove("Irina");
    
    for (Map.Entry<String, Integer> pair : map.entrySet())
      System.out.println(pair.getKey() + " " + pair.getValue());
    
    System.out.println(map.get("Irina"));
  }
}
