import java.util.Iterator;
/**
 * A Binary Search Tree is a Binary Tree with the following
 * properties:
 * 
 * For any node x: (1) the left child of x compares "less than" x; 
 * and (2) the right node of x compares "greater than" x
 *
 *
 * @param <T> the type of data object stored by the BST's Nodes
 */
public class BST<T extends Comparable<T>> {

	private Node<T> root;

	public BST() {
		root = null;
	}


	public Node<T> getRoot() {
		return root;
	}
	
	/**
	 * _Part 1: Implement this method._
	 *
	 * Add a new piece of data into the tree. To do this, you must:
	 * 
	 * 1) If the tree has no root, create a root node 
	 *    with the supplied data
	 * 2) Otherwise, walk the tree from the root to the bottom
	 *    (i.e., a leaf) as though searching for the supplied data.
	 * Then:
	 * 3) Add a new Node to the leaf where the search ended.
	 *
	 * @param data - the data to be added to the tree
	 */
	public void add(T data) {
		Node<T> k = new Node< >(data);
		Node<T> r = root;
		Node<T> s = null;

		if(root == null){
			root = k;
		}

		else {
			while(r != null) {
				s = r;
				if (data.compareTo(r.data) < 0) {
					r = r.left;
				} else {
					r = r.right;
				}
			}
			if(data.compareTo(s.data) < 0){
				s.left = k;
				k.parent = s;
			} else {
				s.right = k;
				k.parent = s;
			}
		}
	}
	

	/**
	 * _Part 2: Implement this method._
	 * 
	 * Find a Node containing the specified key and
	 * return it.  If such a Node cannot be found,
	 * return null.  This method works by walking
	 * through the tree starting at the root and
	 * comparing each Node's data to the specified 
	 * key and then branching appropriately.
	 * 
	 * @param key - the data to find in the tree
	 * @return a Node containing the key data, or null.
	 */
	public Node<T> find(T key) {
		Node<T> x = root;
		Node<T> y = null;

		while(x!=null && key != x.data){
			if( key.compareTo(x.data) < 0 ){
				x = x.left;
			} else {
				x = x.right;
			}
		}
		return x;
	}

	/**
	 * _Part 3: Implement this method._
	 *  
	 * @return the Node with the largest value in the BST
	 */
	public Node<T> maximum() {
		Node<T> r = root;

		if(root == null){
			return null;
		}

		while(r.right != null){
			r = r.right;
		}

		return r;
	}
	
	/**
	 * _Part 4: Implement this method._
	 *  
	 * @return the Node with the smallest value in the BST
	 */
	public Node<T> minimum() {
		Node<T> l = root;

		if(root == null){
			return null;
		}

		while(l.left != null){
			l = l.left;
		}

		return l;
	}

	/**
	 * _Part 5: Implement this method._
	 *
	 *  Assuming no two nodes contain equal data in the tree,
	 *  this method takes a node n with data d, and should
	 *  return the node that stores the value which appears
	 *  immediately after d in the sorted order, or null
	 *  if no such node exists.
	 *
	 *  Thus, for a tree containing data: 1, 3, 5, 10 (regardless
	 *  of the order they were added) the successor of the node
	 *  containing 1 should be the node containing 3.
	 *
	 * @param n a node in the BST
	 * @return the Node in the BST whose value is next in the sorted ordering
	 *   of all keys, or null if no such node exists
	 */
	public Node<T> successor(Node<T> n){
		Node<T> x = find(n.data);
		Node<T> succ;

		if(x.right != null){
			return min(x.right);
		}
		succ = x.parent;

		while(succ != null && x == succ.right) {
			x = succ;
			succ = succ.parent;
		}
		return succ;
	}


	public Node<T> min(Node<T> n){
		while(n.left!= null){
			n = n.left;
		}
		return n;
	}
	
	/**
	 * _Part 6: Implement this method._
	 *  
	 * Searches for a Node with the specified key.
	 * If found, this method removes that node
	 * while maintaining the properties of the BST.
	 *  
	 * @return the Node that was removed or null.
	 */
	public Node<T> remove(T data) {
		Node<T> z = find(data);
		Node<T> y;
		Node<T> x;

		if(z == null){
			return null;
		}
//		when data we want to remove has no children...
		if((z.left == null) || (z.right == null)){
			y = z;
		} else {
			y = successor(z);
		}

		if(y.left != null){
			x = y.left;
		} else { x = y.right; }

		if(x != null){
			x.parent = y.parent;
		}
		if(y.parent == null){
			root = x;
		}

//		finding the RMLC or LMRC, then swapping the values
		else if (y == ((y.parent).left)){
			(y.parent).left = x;
		} else { (y.parent).right = x; }


//		swapping values if our pointer data does not equal the data we want to delete,
		if(y != z){
			z.data = y.data;
		}
		return y;
	}

	/**
	 * 
	 * The Node class for our BST.  The BST
	 * as defined above is constructed from zero or more
	 * Node objects. Each object has between 0 and 2 children
	 * along with a data member that must implement the
	 * Comparable interface.
	 * 
	 * @param <T>
	 */
	public static class Node<T extends Comparable<T>> {
		private Node<T> parent;
		private Node<T> left;
		private Node<T> right;
		private T data;
		
		private Node(T d) {
			data = d;
			parent = null;
			left = null;
			right = null;
		}
		public Node<T> getParent() { return parent; }
		public Node<T> getLeft() { return left; }
		public Node<T> getRight() { return right; }
		public T getData() { return data; }
	}




	/**
	 * _Part 2: Implement this method._
	 *
	 * Find the rank (in an in-order traversal) of the specified
	 * data. The rank of the minimum value key in the BST is 1. If the key
	 * is not found in the tree, return -1.
	 *
	 * 1) Perform an in-order walk of the tree.
	 * 2) At each node "visited" check for the specified data
	 * 3) When found, return the current rank of that node (i.e.,
	 *    that node's location in the in-order walk).
	 *
	 * For example, if a BST contains the elements 1, 10, 20, 100
	 * regardless of the shape of the BST, the rank of 1 is 1,
	 * the rank of 10 is 2, the rank of 20 is 3 and the rank
	 * of 100 is 4.
	 *
	 *
	 * @param data - the data to be searched for
	 * @return - the rank of the found node, or -1
	 *
	 */
	public int rank(T data) {
		Node<T> x = minimum();
		Node<T> key = find(data);
		int count = 1;
		while(x != null){
			if(x.data.compareTo(key.data) == 0){
				return count;
			}
			count += 1;
			x = successor(x);
		}
		return count;
	}


	/**
	 * _Part 3: Implement this method._
	 *
	 * toString() is a method defined by Java's Object class
	 * that can be used to provide a String representation for your
	 * class.  It is called anytime you concatenate an Object to
	 * a String.
	 *
	 * Build a string representation of the BST that describes both
	 * the values stored inside and the shape of the structure; the
	 * approach is described below.
	 *
	 * 1) Build a String representation of the tree
	 *   performing a pre-order traversal of the tree.
	 * 2) As each node is "visited" call the .toString() method on the
	 *    data payload for that node, and add this value to the string.
	 * 3) The printed contents of each node should follow the following format
	 *       "(<data> <left subtree> <right subtree>)"
	 * 4) A null Node reference should also be delimited with ()
	 *
	 * For example an empty tree (no nodes) should return the following:
	 * "()"
	 * A tree with one root node, whose data is "A" should return:
	 * "(A ()())"
	 * A tree with a root whose data is "B" and whose left child contains
	 * "A" and whose right child contains "C" should return:
	 * "(B (A ()())(C ()()))"
	 *
	 * @return - A String representation of the tree
	 *
	 */
	public String toString() {
		// TODO: implement this
		return null;
	}

	/**
	 * _Part 4: Implement this method._
	 *
	 * Returns an iterator that performs an in-order walk
	 * over the tree.  Additionally, you should make the BST
	 * Iterable so that you can walk the tree's contents
	 * using Java's foreach style loop.
	 *
	 * You will need to define a class implementing the Iterator<T>
	 * interface to do the heavy lifting here.
	 */
	public Iterator<T> iterator() {
		// TODO: implement this
		return null;
	}

	// testing purposes
	public static void main(String [] args ){
		BST tree = new BST();
		tree.add(10);
		tree.add(12);
		tree.add(13);
		tree.add(14);
		tree.add(15);
		System.out.println(tree.rank(15));

	}
}
