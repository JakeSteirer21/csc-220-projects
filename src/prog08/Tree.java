package prog08;
import java.util.*;

public class Tree <K extends Comparable<K>, V>
  extends AbstractMap<K, V> {

  private class Node <K extends Comparable<K>, V>
    implements Map.Entry<K, V> {
    K key;
    V value;
    Node left, right;
    
    Node (K key, V value) {
      this.key = key;
      this.value = value;
    }

    public K getKey () { return key; }
    public V getValue () { return value; }
    public V setValue (V newValue) {
      V oldValue = value;
      value = newValue;
      return oldValue;
    }
  }
  
  private Node root;
  private int size;

  public int size () { return size; }

  /**
   * Find the node with the given key.
   * @param key The key to be found.
   * @return The node with that key.
   */
  private Node<K, V> find (K key, Node<K,V> root) {
    // IMPLEMENT
			if (root == null)
				return null;
		        if (key.compareTo(root.key) == 0)
				return root;
		        if (key.compareTo(root.key) < 0)
				return find(key, root.left);
			else
				return find(key, root.right);
  }    

  public boolean containsKey (Object key) {
    return find((K) key, root) != null;
  }
  
  public V get (Object key) {
    Node<K, V> node = find((K) key, root);
    if (node != null)
      return node.value;
    return null;
  }
  
  public boolean isEmpty () { return size == 0; }
  
  /**
   * Add key,value pair to tree rooted at root.
   * Return root of modified tree.
   */
  private Node<K, V> add (K key, V value, Node<K,V> root) {
    // IMPLEMENT
	  if (root == null)
			return new Node<K, V>(key, value);
		if (key.compareTo(root.key) < 0) {
			root.left = add(key, value, root.left);
			return root;
		}
	        else {
			root.right = add(key, value, root.right);
			return root;
		}
  }
  
  public V put (K key, V value) {
    // IMPLEMENT
	  Node<K, V> node = find(key, root);
	  if(node == null){
		  root = add(key, value, root);
		  size++;
		  return null;
	  } else {
		  V oldValue = node.getValue();
		  node.setValue(value);
		  return oldValue;
	  }
  }      
  
  public V remove (Object keyAsObject) {

    K key = (K) keyAsObject;

    Node<K,V> node = find(key, root);
    if (node == null)
      return null;
    root = remove(key, root);
    size--;
    return node.value;
  }

  private Node<K,V> remove (K key, Node<K,V> root) {
    // IMPLEMENT
	  if (key.compareTo(root.key) == 0)
		  return removeRoot(root);
	  	if (key.compareTo(root.key) < 0) {
	  		return remove(key, root.left);
	  	} else {
	  		return remove(key, root.right);
	  	}
  }

  /**
   * Remove root of tree rooted at root.
   * Return root of BST of remaining nodes.
   */
  private Node removeRoot (Node root) {
    // IMPLEMENT   
	  if (root.left == null)
		  return root.right;
	  if (root.right == null)
		  return root.left;
	  Node newRoot = getLeftmost(root.right);
	  root.right = removeLeftmost(root.right);
	  newRoot.left = root.left;
	  newRoot.right = root.right;
	  root = newRoot;
    return root;
  }

  /**
   * Return leftmost node in tree rooted at root.
   */
  private Node getLeftmost (Node root) {
    // IMPLEMENT
	  if (root.left == null)
		  return root;
	  return getLeftmost(root.left);
  }
  
  /**
   * Remove leftmost node from tree rooted at root.
   * Return root of modified tree.
   */
  private Node removeLeftmost (Node root) {
    // IMPLEMENT
	  if (root.left == null)
		  return root.right;
	  return removeLeftmost(root.left);
  }
  
  public Set<Map.Entry<K, V>> entrySet () { return null; }
  
  public String toString () {
    return toString(root, 0);
  }
  
  private String toString (Node root, int indent) {
    if (root == null)
      return "";
    String ret = toString(root.right, indent + 2);
    for (int i = 0; i < indent; i++)
      ret = ret + "  ";
    ret = ret + root.key + " " + root.value + "\n";
    ret = ret + toString(root.left, indent + 2);
    return ret;
  }

  public static void main (String[] args) {
    Tree<String, Integer> tree = new Tree<String, Integer>();
    
    tree.put("Victor", 6);
    tree.put("Lisa", 4);
    tree.put("Zoe", 3);
    tree.put("Hal", 3);
    System.out.println(tree);
    
    tree.remove("Victor");
    System.out.println(tree);
    
    tree.put("Victor", 10);
    System.out.println(tree);
  }
}
