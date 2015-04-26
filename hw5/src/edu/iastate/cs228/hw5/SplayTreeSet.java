package edu.iastate.cs228.hw5;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

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
		if (root == null)
			root = new Node<E>(key); 
		
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
		} else if (c < 0) {
			// if there is no left child, insert it there
			if (current.getLeft() == null) {
				Node<E> tmp = new Node<E>(key);
				current.setLeft(tmp);
				tmp.setParent(current);
				splay(tmp);
			} else {
				// otherwise recurse down the tree
				add(current.getLeft(), key);
			}
		} else if (c > 0) {
			// if there is no right child, insert it there
			if (current.getRight() == null) {
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
		
		// unlink will take care of splaying
		Node<E> p = tmp.getParent();
		unlinkNode(tmp);
		
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
		} else if (c < 0 && current.getLeft() != null)
			return findEntry(current.getLeft(), key);
		else if (c > 0 && current.getRight() != null)
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
		Node<E> r = n.getRight();
		if (r == null)
			return null;
		else if (getNumChildren(r) == 0)
			return r;
		else
			return findSuccessor(r);
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
	private Node<E> findSuccessor(Node<E> current)
	{
		// if there are no further elements on the left, return current
		if (current.getLeft() == null)
			return current;
		
		// otherwise recurse down
		return findSuccessor(current.getLeft());
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
			Node<E> s = successor(n);
			n.setData(s.getData());
			
			// because we are using the left most element of the right tree, s has no left children
			s.getParent().setLeft(s.getRight()); // s.getRight() can be null
			s.getRight().setParent(s.getParent()); // s.getRight()'s parent
			
			// now s is the root of this subset (where n was) so we are done removing the node
			return;
		}
	}
	
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
		// TODO
		return null; 
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
			if (current.getParent().getParent() == null)
				zig(current);
				
			else 
				// current and parent are both left or right of their parent
				if ((current.isLeftChild() && current.getParent().isLeftChild()) ||
						current.isRightChild() && current.getParent().isRightChild())
					zigZig(current);
				
			else
				// current and parent are either left or right of their parent (but not the same)
				zigZag(current);
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
		p.setParent(current);
		root = current;
		
		if (current.isLeftChild()) {
			// current-right is now parent-left
			Node<E> r = current.getRight();
			r.setParent(p);
			p.setLeft(r);
			
			// p is now on the right of current
			current.setRight(p);
		} else {
			// current-left is now parent-right
			Node<E> l = current.getLeft();
			l.setParent(p);
			p.setRight(l);
			
			// p is now on the left of current
			current.setLeft(p);
		}
	}

	/**
	 * Performs the zig-zig operation on a node.
	 * @param current  node at which to perform the zig-zig operation.
	 */
	protected void zigZig(Node<E> current)
	{
		// p is not root and x and p are both left or right children
		Node<E> p = current.getParent();
		Node<E> g = p.getParent();
		Node<E> gg = g.getParent();
		
		if (p.isRightChild() && current.isRightChild()) {
			// move left data to the right
			Node<E> r = current.getRight();
			r.setParent(p);
			p.setLeft(r);
			
			r = p.getRight();
			r.setParent(g);
			g.setLeft(r);
			
			// now re-attach with current as root
			current.setRight(p);
			p.setParent(current);
			
			p.setRight(g);
			g.setParent(p);
			
			
		} else if (p.isLeftChild() && current.isLeftChild()) {
			// move right data to the left
			Node<E> l = current.getLeft();
			l.setParent(p);
			p.setRight(l);
			
			l = p.getLeft();
			l.setParent(g);
			g.setLeft(l);
			
			// now re-attach with current as root
			current.setLeft(p);
			p.setParent(current);
			
			p.setLeft(g);
			g.setParent(p);
		}
		
		validateRoot(current, gg);
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
		
		Node<E> r = current.getRight();
		Node<E> l = current.getLeft();
			
		p.setParent(current);
		g.setParent(current);
		
		if (current.isRightChild() && p.isLeftChild()) {
			// move currents left and right to under p and g
			p.setRight(l);
			l.setParent(p);
			
			g.setLeft(r);
			r.setParent(g);
			
			// now set current as the overall parent
			current.setLeft(p);
			current.setRight(g);
			
		} else if (current.isLeftChild() && p.isRightChild()) {
			// move currents left and right to under p and g
			p.setLeft(r);
			r.setParent(p);
			
			g.setRight(l);
			l.setParent(p);
			
			// now set current as the overall parent
			current.setLeft(g);
			current.setRight(p);
		}
		
		validateRoot(current, gg);
	}
	
	private void validateRoot(Node<E> current, Node<E> gg)
	{
		// if g was the root, current is now the root
		if (gg == null) {
			root = current;
			current.setParent(null);
		} else {
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
		Node<E> cursor;

		public SplayTreeIterator()
		{
			// TODO
		}

		@Override
		public boolean hasNext()
		{
			// TODO
			return true; 
		}

		@Override
		public E next()
		{
			// TODO
			return null; 
		}

		@Override
		public void remove()
		{
			// TODO
		}
	}
}