package edu.iastate.cs228.hw5;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 *
 * Splay tree implementation of the Set interface.  The contains() and
 * remove() methods of AbstractSet are overridden to search the tree without
 * using the iterator.
 * 
 * @author Ian Malerich
 *
 */
public class SplayTreeSet<E extends Comparable<? super E>> extends AbstractSet<E>
{
	// The root of the tree containing this set's items	
	Node<E> root;
	Node<E> lastNodeFound = null;

	// The total number of elements stored in this set
	int size;

	/**
	 * Default constructor.  Creates a new, empty, SplayTreeSet
	 */
	public SplayTreeSet() {
		root = null;
		size = 0;
	}

	/**
	 * Shallow copy constructor.  This method is fully implemented and should not be
	 * modified.
	 */
	public SplayTreeSet(Node<E> root, int size) {
		this.root = root;
		this.size = size;
	}

	/**
	 * Gets the root of this tree.  Used only for testing. This method is fully implemented
	 * and should not be modified.
	 * @return the root of this tree.
	 */
	public Node<E> getRoot() 
	{
		return root; 
	}

	/**
	 * Determines whether the set contains an element.  Splays at the node that stores the 
	 * element.  If the element is not found, splays at the last node on the search path.
	 * @param  obj  element to be determined whether to exist in the tree
	 * @return true if the element is contained in the tree and false otherwise
	 */
	@Override
	public boolean contains(Object obj)
	{
		@SuppressWarnings("unchecked")
		Node<E> tmp = findEntry((E)obj);
		if (tmp != null)
			splay(tmp);
		else
			splay(lastNodeFound);
		
		return tmp != null;
	}

	/**
	 * Inserts an element into the splay tree. In case the element was not contained, this  
	 * creates a new node and splays the tree at the new node. If the element exists in the 
	 * tree already, it splays at the node containing the element. 
	 * @param  key  element to be inserted
	 * @return true if insertion is successful and false otherwise
	 */
	@Override
	public boolean add(E key)
	{
		// if root has not been set, create a new node and set the root
		if (root == null) {
			size++;
			root = new Node<E>(key); 
			return true;
		}
		
		return add(root, key);
	}
	
	/**
	 * add a node by recursively traveling through the tree
	 * 
	 * @param current
	 * 	the current node to recurse through (to start - use the root node)
	 * @param key
	 * 	the key we wish to add
	 */
	private boolean add(Node<E> current, E key)
	{
		if (current == null)
			return false;
		
		int c = current.getData().compareTo(key);
		if (c == 0) {
			// if it already exists, splay the tree at the element
			splay(current);
			return false;
		} else if (c > 0) {
			// if there is no left child, insert it there
			if (current.getLeft() == null) {
				size++;
				Node<E> tmp = new Node<E>(key);
				current.setLeft(tmp);
				tmp.setParent(current);
				splay(tmp);
			} else {
				// otherwise recurse down the tree
				add(current.getLeft(), key);
			}
		} else if (c < 0) {
			// if there is no right child, insert it there
			if (current.getRight() == null) {
				size++;
				Node<E> tmp = new Node<E>(key);
				current.setRight(tmp);
				tmp.setParent(current);
				splay(tmp);
			} else {
				// otherwise recurse down the tree
				add(current.getRight(), key);
			}
		}
		
		return true;
	}
	
	/**
	 * Removes the node that stores an element.  Splays at its parent node after removal
	 * (No splay if the removed node was the root.) If the node was not found, the last node 
	 * encountered on the search path is splayed to the root.
	 * @param obj  element to be removed from the tree
	 * @return true if the object is removed and false if it was not contained in the tree. 
	 */  
	@Override
	public boolean remove(Object obj)
	{
		// find the node to remove
		@SuppressWarnings("unchecked")
		Node<E> tmp = findEntry((E)obj);
		
		// if the node was not found, splay at the last node found
		if (tmp == null) {
			splay(lastNodeFound);
			return false;
		}
		
		Node<E> p = tmp.getParent();
		unlinkNode(tmp);
		size--;
		
		if (p != null)
			splay(p);
		return true; 
	}

	/**
	 * Returns the node containing key, or null if the key is not
	 * found in the tree.  Called by contains().
	 * @param key
	 * @return the node containing key, or null if not found
	 */
	protected Node<E> findEntry(E key)
	{
		return findEntry(root, key); 
	}
	
	/**
	 * Recursively searches this tree for object.	
	 * @param current
	 * 	root node of the tree from where to search
	 * @param obj
	 * 	the object we are searching for (the Nodes data)
	 * @return
	 * 	whether or not the object exists
	 */
	private Node<E> findEntry(Node<E> current, E key)
	{
		if (current == null)
			return null;
		
		int c = current.getData().compareTo(key);
		if (c == 0) {
			return current;
		} else if (c > 0 && current.getLeft() != null)
			return findEntry(current.getLeft(), key);
		else if (c < 0 && current.getRight() != null)
			return findEntry(current.getRight(), key);
		
		lastNodeFound = current;
		return null;
	}

	/**
	 * Returns the successor of the given node.
	 * @param n
	 * @return the successor of the given node in this tree, 
	 *   or null if there is no successor
	 */
	protected Node<E> successor(Node<E> n)
	{
		Node<E> l = n.getRight();
		if (l == null)
			return null;
		else if (getNumChildren(l) == 0)
			return l;
		else
			return findLeftMost(l);
	}
	
	/**
	 * Finds the right-most node of given node 
	 * 
	 * @param current
	 * 	node to find the right most element of
	 * @return
	 * 	the right most element of current 
	 *  if no right nodes returns current
	 */
	private Node<E> findRightMost(Node<E> current)
	{
		// no element to search
		if (current == null)
			return null;
		
		// if there are no further elements on the right, return current
		if (current.getRight() == null)
			return current;
		
		// otherwise recurse down
		return findRightMost(current.getRight());
	}
	
	/**
	 * Finds the left-most node of given node 
	 * 
	 * @param current
	 * 	node to find the left most element of
	 * @return
	 * 	the left most element of current if no left nodes
	 * 	returns current
	 */
	private Node<E> findLeftMost(Node<E> current)
	{
		// no element to search
		if (current == null)
			return null;
		
		// if there are no further elements on the left, return current
		if (current.getLeft() == null)
			return current;
		
		// otherwise recurse down
		return findLeftMost(current.getLeft());
	}

	/**
	 * Removes the given node, preserving the binary search
	 * tree property of the tree.
	 * @param n node to be removed
	 */
	protected void unlinkNode(Node<E> n)
	{
		// if this node has no children, then removing it from its parent unlinks it from the tree
		if (getNumChildren(n) == 0) {
			// if we have no children, and no parent, the tree is now null
			if (n.getParent() == null) {
				root = null;
				return;
			}
			
			if (n.isLeftChild())
				n.getParent().setLeft(null);
			else
				n.getParent().setRight(null);
			
			// we are done removing the node
			return;
		} else if (getNumChildren(n) == 1) {
			// set this nodes parent to this nodes child
			Node<E> child = n.getLeft() != null ? n.getLeft() : n.getRight();
			child.setParent(n.getParent());
			
			if (n.getParent() == null) {
				root = child;
				return;
			} if (n.isLeftChild()) {
				n.getParent().setLeft(child);
			} else {
				n.getParent().setRight(child);
			}
			
			// we are done removing the node
			return;
		} else {
			Node<E> s = n.getLeft();
			if (s != null)
				s = findRightMost(s);
			
			n.setData(s.getData());
			
			// because we are using the right-most most element of the left tree, s has no right children
			if (s.getParent() != null)
				s.getParent().setRight(s.getLeft()); // s.getLeft() can be null
			
			if (s.getLeft() != null)
				s.getLeft().setParent(s.getParent()); // s.getLeft()'s parent
			
			// now s is the root of this subset (where n was) so we are done removing the node
			return;
		}
	}
	
	/**
	 * Returns the number of children 0, 1, or 2 of a node.
	 * 	note: excludes grand-children etc
	 * @param n
	 * 	The node to count the children of
	 * @return
	 * 	the number of children, either 0, 1, or 2
	 */
	private int getNumChildren(Node<E> n)
	{
		int c = 0;
		if (n.getLeft() != null)
			c++;
		if (n.getRight() != null)
			c++;
		
		return c;
	}

	@Override
	public Iterator<E> iterator()
	{
		return new SplayTreeIterator();
	}

	@Override
	public int size()
	{
		return size; 
	}

	/**
	 * Returns a representation of this tree as a multi-line string as
	 * explained in the project description.
	 */
	@Override
	public String toString()
	{
		return toString(root, new String(), 0); 
	}
	
	/**
	 * Constructs a string out of a tree starting at current.
	 * 
	 * @param current
	 * 	The root node of the tree.
	 * @param appendTo
	 * 	The string already constructed by currents parents.
	 * @param depth
	 * 	The current depth of the tree.
	 * @return
	 * 	The completed string.
	 */
	private String toString(Node<E> current, String appendTo, int depth)
	{
		if (current == null) {
			return appendTo + (appendTo.length() != 0 ? "\n" : "") + getSpaces(depth*4) + "null";
		}
		
		String l = (appendTo.length() != 0 ? "\n" : "") + getSpaces(depth*4) + current.getData().toString();
		if (getNumChildren(current) == 0) {
			// no children, simply return the prev string plus the new string
			return appendTo + l;
		} else {
			String left = toString(current.getLeft(), appendTo + l, depth+1);
			String right = toString(current.getRight(), left, depth+1);
			
			return right;
		}
	}
	
	/**
	 * Get a string containg count spaces.
	 * 
	 * @param count
	 * 	the number of spaces in the return string
	 * @return	
	 * 	the return string
	 */
	private String getSpaces(int count)
	{
		return new String(new char[count]).replace('\0', ' ');
	}

	/**
	 * Splay at the current node.  This consists of a sequence of zig, zigZig, or zigZag 
	 * operations until the current node is moved to the root of the tree.
	 * @param current  node at which to splay.
	 */
	protected void splay(Node<E> current)
	{
		// perform zigs, zig-zigs, and zig-zags until current is the root
		while (current.getParent() != null) {
			
			// if current-parent is root, perform zig
			if (current.getParent().getParent() == null) {
				zig(current);
				
			} else 
				// current and parent are both left or right of their parent
				if ((current.isLeftChild() && current.getParent().isLeftChild()) ||
						current.isRightChild() && current.getParent().isRightChild()) {
					zigZig(current);
				
			} else 
				// current and parent are either left or right of their parent (but not the same)
				if ((current.isLeftChild() && current.getParent().isRightChild()) ||
						current.isRightChild() && current.getParent().isLeftChild()) {
					zigZag(current);
					
			} else {
				throw new IllegalStateException();
			}
		}
	}	

	/**
	 * Performs the zig operation on a node.
	 * @param current  node at which to perform the zig operation.
	 */
	protected void zig(Node<E> current)
	{
		// performed when current-parent is root
		Node<E> p = current.getParent();
		root = current;
		
		if (current.isLeftChild()) {
			// current-right is now parent-left
			Node<E> r = current.getRight();
			p.setLeft(r);
			if (r != null)
				r.setParent(p);
			
			// p is now on the right of current
			current.setRight(p);
		} else {
			// current-left is now parent-right
			Node<E> l = current.getLeft();
			p.setRight(l);
			if (l != null)
				l.setParent(p);
			
			// p is now on the left of current
			current.setLeft(p);
		}
		
		p.setParent(current);
		current.setParent(null);
	}

	/**
	 * Performs the zig-zig operation on a node.
	 * @param current  node at which to perform the zig-zig operation.
	 */
	protected void zigZig(Node<E> current)
	{
		Node<E> p = current.getParent();
		Node<E> g = p.getParent();
		Node<E> gg = g.getParent();
		boolean R = g.isRightChild();
		
		if (p.isRightChild() && current.isRightChild()) {
			// move left data to the right
			Node<E> b = current.getLeft();
			Node<E> c = p.getLeft(); // left is current
			
			p.setRight(b);
			if (b != null)
				b.setParent(p);
			
			g.setRight(c);
			if (c != null)
				c.setParent(g);
			
			g.setParent(p);
			p.setLeft(g);
			
			p.setParent(current);
			current.setLeft(p);
			
		} else if (p.isLeftChild() && current.isLeftChild()) {
			// move right data to the left
			Node<E> b = current.getRight();
			Node<E> c = p.getRight(); // left is current
			
			p.setLeft(b);
			if (b != null)
				b.setParent(p);
			
			g.setLeft(c);
			if (c != null)
				c.setParent(g);
			
			g.setParent(p);
			p.setRight(g);
			
			p.setParent(current);
			current.setRight(p);
		}
		
		if (gg == null) {
			root = current;
			current.setParent(null);
		} else {
			if (R)
				gg.setRight(current);
			else
				gg.setLeft(current);
			
			current.setParent(gg);
		}
	}

	/**
	 * Performs the zig-zag operation on a node.
	 * @param current  node to perform the zig-zag operation on
	 */
	protected void zigZag(Node<E> current)
	{
		// p is not root and x and p are either left or right children (but not the same)
		Node<E> p = current.getParent();
		Node<E> g = p.getParent();
		Node<E> gg = g.getParent();
		boolean R = g.isRightChild();
			
		if (current.isRightChild() && p.isLeftChild()) {
			// move currents left and right to under p and g
			Node<E> b = current.getLeft();
			Node<E> c = current.getRight();
			
			if (b != null)
				b.setParent(p);
			p.setRight(b);
			
			if (c != null)
				c.setParent(g);
			g.setLeft(c);
			
			current.setLeft(p);
			current.setRight(g);
			p.setParent(current);
			g.setParent(current);
			
			
		} else if (current.isLeftChild() && p.isRightChild()) {
			// move currents left and right to under p and g
			Node<E> b = current.getLeft();
			Node<E> c = current.getRight();
			
			if (b != null)
				b.setParent(g);
			g.setRight(b);
			
			if (c != null)
				c.setParent(p);
			p.setLeft(c);
			
			current.setLeft(g);
			current.setRight(p);
			p.setParent(current);
			g.setParent(current);
			
		}
		
		// move current to the child of gg (or root if gg does not exist)
		if (gg == null) {
			root = current;
			current.setParent(null);
		} else {
			if (R)
				gg.setRight(current);
			else
				gg.setLeft(current);
			
			current.setParent(gg);
		}
	}

	/**
	 *
	 * Iterator implementation for this splay tree.  The elements are
	 * returned in ascending order according to their natural ordering.
	 *
	 */
	private class SplayTreeIterator implements Iterator<E>
	{
		private Stack<Node<E>> s;
		private Node<E> cursor; // used only for traversal
		private Node<E> data; // stores the current data of the iterator

		public SplayTreeIterator()
		{
			// cursor will start at the root, and will recurse down to the left when next() is called
			cursor = root;
			s = new Stack<Node<E>>();
		}

		@Override
		public boolean hasNext()
		{
			// there is no more data when we are at the bottom right of the tree (the largest node)
			return !s.isEmpty() || cursor != null;
		}

		@Override
		public E next()
		{
			// if there is no next element, return an exception
			if (!hasNext())
				throw new NoSuchElementException();
			
			// find the left most element of the tree
			while (cursor != null) {
				s.push(cursor);
				cursor = cursor.getLeft();
			}
			
			// set the cursor to the right node of the current node on the stack
			data = s.pop();
			cursor = data.getRight();
			
			// the data we want is now that nodes parents
			return data.getData(); 
		}

		@Override
		public void remove()
		{
			// remove the cursor from the tree
			Node<E> p = data.getParent();
			SplayTreeSet.this.remove(data.getData());
			
			cursor = p.getRight(); // everything to the left has already been checked
			s.clear();
		}
	}
}