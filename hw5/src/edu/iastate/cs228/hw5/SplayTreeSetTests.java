package edu.iastate.cs228.hw5;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import org.junit.Test;

/**
 * 
 * @author Sam Schulte
 *
 */

public class SplayTreeSetTests {

	// Tests toString()
	@Test
	public void testToString() {
		SplayTreeSet<Integer> s = new SplayTreeSet<Integer>();
		s.add(30);
		s.add(21);
		s.add(22);
		s.add(50);
		s.add(10);
		s.add(60);
		s.add(53);
		s.add(55);
		s.add(25);
		StringBuilder sb = new StringBuilder();
		sb.append("25");
		sb.append('\n');
		sb.append("    10");
		sb.append('\n');
		sb.append("        null");
		sb.append('\n');
		sb.append("        22");
		sb.append('\n');
		sb.append("            21");
		sb.append('\n');
		sb.append("            null");
		sb.append('\n');
		sb.append("    55");
		sb.append('\n');
		sb.append("        50");
		sb.append('\n');
		sb.append("            30");
		sb.append('\n');
		sb.append("            53");
		sb.append('\n');
		sb.append("        60");
		String ans = sb.toString();
		String res = s.toString();
		assertEquals(ans, res);
	}
	
	// Remove two children, parent is root
	@Test
	public void removeTwoChildrenParentRoot() {
		SplayTreeSet<Integer> s = new SplayTreeSet<Integer>();
		s.add(30);
		s.add(21);
		s.add(22);
		s.add(50);
		s.add(10);
		s.add(60);
		s.add(53);
		s.add(55);
		s.add(25);
		s.remove(55);
		
		Integer ans = 25;
		Integer res = s.getRoot().getData();
		assertEquals(ans, res);
	}
	
	// Remove two children, parent not root
	@Test
	public void removeTwoChildrenParentNotRoot() {
		SplayTreeSet<Integer> s = new SplayTreeSet<Integer>();
		s.add(30);
		s.add(21);
		s.add(22);
		s.add(50);
		s.add(10);
		s.add(60);
		s.add(53);
		s.add(55);
		s.add(25);
		s.remove(50);
		Integer ans = 55;
		Integer res = s.getRoot().getData();
		assertEquals(ans, res);
	}
	
	// Remove root with two children
	@Test
	public void removeTwoChildrenRoot() {
		SplayTreeSet<Integer> s = new SplayTreeSet<Integer>();
		s.add(30);
		s.add(21);
		s.add(22);
		s.add(50);
		s.add(10);
		s.add(60);
		s.add(53);
		s.add(55);
		s.add(25);
		
		s.remove(25);
		Integer ans = 22;
		Integer res = s.getRoot().getData();
		assertEquals(ans, res);
	}
	
	// Ensures remove updates size
	@Test
	public void removeCheckSize() {
		SplayTreeSet<Integer> s = new SplayTreeSet<Integer>();
		s.add(30);
		s.add(21);
		s.add(22);
		s.add(50);
		s.add(10);
		s.add(60);
		s.add(53);
		s.add(55);
		s.add(25);
		s.remove(25);
		Integer ans = 8;
		Integer res = s.size();
		assertEquals(ans, res);
	}
	
	// Remove with one child, parent is root
	@Test
	public void removeOneChildParentRoot() {
		SplayTreeSet<Integer> s = new SplayTreeSet<Integer>();
		s.add(30);
		s.add(21);
		s.add(22);
		s.add(50);
		s.add(10);
		s.add(60);
		s.add(53);
		s.add(55);
		s.add(25);
		s.remove(10);
		Integer ans = 25;
		Integer res = s.getRoot().getData();
		assertEquals(ans, res);
	}
	
	// Remove with one child, parent not root
	@Test
	public void removeOneChildParentNotRoot() {
		SplayTreeSet<Integer> s = new SplayTreeSet<Integer>();
		s.add(30);
		s.add(21);
		s.add(22);
		s.add(50);
		s.add(10);
		s.add(60);
		s.add(53);
		s.add(55);
		s.add(25);
		s.remove(22);
		Integer ans = 10;
		Integer res = s.getRoot().getData();
		assertEquals(ans, res);
	}
	
	// Remove root with one child
	@Test
	public void removeOneChildRoot() {
		SplayTreeSet<Integer> s = new SplayTreeSet<Integer>();
		s.add(30);
		s.add(21);
		s.add(22);
		s.add(50);
		s.remove(50);
		Integer ans = 30;
		Integer res = s.getRoot().getData();
		assertEquals(ans, res);
	}
	
	// Remove leaf
	@Test
	public void removeLeaf() {
		SplayTreeSet<Integer> s = new SplayTreeSet<Integer>();
		s.add(30);
		s.add(21);
		s.add(22);
		s.add(50);
		s.add(10);
		s.add(60);
		s.add(53);
		s.add(55);
		s.add(25);
		s.remove(53);
		Integer ans = 50;
		Integer res = s.getRoot().getData();
		assertEquals(ans, res);
	}
	
	// Contains root
	@Test
	public void containsRoot() {
		SplayTreeSet<Integer> s = new SplayTreeSet<Integer>();
		s.add(30);
		s.add(21);
		s.add(22);
		s.add(50);
		s.add(10);
		s.add(60);
		s.add(53);
		s.add(55);
		s.add(25);
		boolean ans = true;
		boolean res = s.contains(25);
		assertEquals(ans, res);
	}
	
	// Contains min
	@Test
	public void containsMin() {
		SplayTreeSet<Integer> s = new SplayTreeSet<Integer>();
		s.add(30);
		s.add(21);
		s.add(22);
		s.add(50);
		s.add(10);
		s.add(60);
		s.add(53);
		s.add(55);
		s.add(25);
		boolean ans = true;
		boolean res = s.contains(10);
		assertEquals(ans, res);
	}
	
	// Contains max
	@Test
	public void containsMax() {
		SplayTreeSet<Integer> s = new SplayTreeSet<Integer>();
		s.add(30);
		s.add(21);
		s.add(22);
		s.add(50);
		s.add(10);
		s.add(60);
		s.add(53);
		s.add(55);
		s.add(25);
		boolean ans = true;
		boolean res = s.contains(60);
		assertEquals(ans, res);
	}
	
	// Contains too high
	@Test
	public void containsTooHigh() {
		SplayTreeSet<Integer> s = new SplayTreeSet<Integer>();
		s.add(30);
		s.add(21);
		s.add(22);
		s.add(50);
		s.add(10);
		s.add(60);
		s.add(53);
		s.add(55);
		s.add(25);
		boolean ans = false;
		boolean res = s.contains(500);
		assertEquals(ans, res);
	}
	
	// Contains too low
	@Test
	public void containsTooLow() {
		SplayTreeSet<Integer> s = new SplayTreeSet<Integer>();
		s.add(30);
		s.add(21);
		s.add(22);
		s.add(50);
		s.add(10);
		s.add(60);
		s.add(53);
		s.add(55);
		s.add(25);
		boolean ans = false;
		boolean res = s.contains(5);
		assertEquals(ans, res);
	}
	
	// Contains in the middle, but not there
	@Test
	public void containsMiddleFalse() {
		SplayTreeSet<Integer> s = new SplayTreeSet<Integer>();
		s.add(30);
		s.add(21);
		s.add(22);
		s.add(50);
		s.add(10);
		s.add(60);
		s.add(53);
		s.add(55);
		s.add(25);
		boolean ans = false;
		boolean res = s.contains(32);
		assertEquals(ans, res);
	}
	
	// Successor min
	@Test
	public void successorMin() {
		SplayTreeSet<Integer> s = new SplayTreeSet<Integer>();
		s.add(30);
		s.add(21);
		s.add(22);
		s.add(50);
		s.add(10);
		s.add(60);
		s.add(53);
		s.add(55);
		s.add(25);
		
		System.out.println(s.toString() + '\n');
		Integer ans = 21;
		Node<Integer> n = s.getRoot().getLeft(); // Gives 10, the smallest node
		Integer res = s.successor(n).getData();
		assertEquals(ans, res);
	}
	
	// Successor of current node (which has a right subtree)
	@Test
	public void successorHasRightSubtree() {
		SplayTreeSet<Integer> s = new SplayTreeSet<Integer>();
		s.add(30);
		s.add(21);
		s.add(22);
		s.add(50);
		s.add(10);
		s.add(60);
		s.add(53);
		s.add(55);
		s.add(25);
		
		Integer ans = 30;
		Node<Integer> n = s.getRoot();
		Integer res = s.successor(n).getData();
		assertEquals(ans, res);
	}
	
	// Successor of current node (which has a left subtree)
	@Test
	public void successorOnlyLeftSubtree() {
		SplayTreeSet<Integer> s = new SplayTreeSet<Integer>();
		s.add(30);
		s.add(21);
		s.add(22);
		s.add(50);
		s.add(10);
		s.add(60);
		s.add(53);
		s.add(55);
		s.add(25);
		
		Integer ans = 25;
		Node<Integer> n = s.getRoot().getLeft().getRight(); // Gives 22, which only has a left subtree
		Integer res = s.successor(n).getData();
		assertEquals(ans, res);
	}
	
	// Successor of current node (which is a right leaf)
	@Test
	public void successorRightLeaf() {
		SplayTreeSet<Integer> s = new SplayTreeSet<Integer>();
		s.add(30);
		s.add(21);
		s.add(22);
		s.add(50);
		s.add(10);
		s.add(60);
		s.add(53);
		s.add(55);
		s.add(25);
		Integer ans = 55;
		Node<Integer> n = s.getRoot().getRight().getLeft().getRight(); // Gives 53, which is a right leaf
		Integer res = s.successor(n).getData();
		assertEquals(ans, res);
	}
	
	// Successor of current node (which is a left leaf)
	@Test
	public void successorLeftLeaf() {
		SplayTreeSet<Integer> s = new SplayTreeSet<Integer>();
		s.add(30);
		s.add(21);
		s.add(22);
		s.add(50);
		s.add(10);
		s.add(60);
		s.add(53);
		s.add(55);
		s.add(25);
		
		Integer ans = 50;
		Node<Integer> n = s.getRoot().getRight().getLeft().getLeft(); // Gives 30, which is a left leaf
		Integer res = s.successor(n).getData();
		assertEquals(ans, res);
	}
	
	// Ensures iterator is set up correctly
	@Test
	public void iteratorFirstCursorPosition() {
		SplayTreeSet<Integer> s = new SplayTreeSet<Integer>();
		s.add(30);
		s.add(21);
		s.add(22);
		s.add(50);
		s.add(10);
		s.add(60);
		s.add(53);
		s.add(55);
		s.add(25);
		Iterator<Integer> i = s.iterator();
		Integer ans = 10;
		Integer res = i.next();
		assertEquals(ans, res);
	}
	
	// Ensures iterator proceeds correctly
	@Test
	public void iteratorSecondCursorPosition() {
		SplayTreeSet<Integer> s = new SplayTreeSet<Integer>();
		s.add(30);
		s.add(21);
		s.add(22);
		s.add(50);
		s.add(10);
		s.add(60);
		s.add(53);
		s.add(55);
		s.add(25);
		Iterator<Integer> i = s.iterator();
		Integer ans = 21;
		i.next();
		Integer res = i.next();
		assertEquals(ans, res);
	}
	
	// Ensures iterator proceeds correctly
	@Test
	public void iteratorSixthCursorPosition() {
		SplayTreeSet<Integer> s = new SplayTreeSet<Integer>();
		s.add(30);
		s.add(21);
		s.add(22);
		s.add(50);
		s.add(10);
		s.add(60);
		s.add(53);
		s.add(55);
		s.add(25);
		Iterator<Integer> i = s.iterator();
		Integer ans = 50;
		i.next();
		i.next();
		i.next();
		i.next();
		i.next();
		Integer res = i.next();
		assertEquals(ans, res);
	}
	
	// Iterator remove smallest element
	@Test
	public void iteratorRemoveSmallest() {
		SplayTreeSet<Integer> s = new SplayTreeSet<Integer>();
		s.add(30);
		s.add(21);
		s.add(22);
		s.add(50);
		s.add(10);
		s.add(60);
		s.add(53);
		s.add(55);
		s.add(25);
		Iterator<Integer> i = s.iterator();
		i.next();
		Integer ans = 25;
		Integer res = s.getRoot().getData();
		assertEquals(ans, res);
	}
	
	// Remove test second smallest element
	@Test
	public void iteratorRemoveSecondSmallest() {
		SplayTreeSet<Integer> s = new SplayTreeSet<Integer>();
		s.add(30);
		s.add(21);
		s.add(22);
		s.add(50);
		s.add(10);
		s.add(60);
		s.add(53);
		s.add(55);
		s.add(25);
		
		Iterator<Integer> i = s.iterator();
		Integer ans = 22;
		i.next();
		i.next();
		i.remove();
		Integer res = s.getRoot().getData();
		assertEquals(ans, res);
	}
	
	// Remove test sixth smallest element
	@Test
	public void iteratorRemoveSixthSmallest() {
		SplayTreeSet<Integer> s = new SplayTreeSet<Integer>();
		s.add(30);
		s.add(21);
		s.add(22);
		s.add(50);
		s.add(10);
		s.add(60);
		s.add(53);
		s.add(55);
		s.add(25);
		
		Iterator<Integer> i = s.iterator();
		Integer ans = 55;
		i.next();
		i.next();
		i.next();
		i.next();
		i.next();
		i.next();
		i.remove();
		Integer res = s.getRoot().getData();
		assertEquals(ans, res);
	}
	
	// Tests if hasNext() returns false before max element
	@Test
	public void iteratorHasNextBeforeMax() {
		SplayTreeSet<Integer> s = new SplayTreeSet<Integer>();
		s.add(30);
		s.add(21);
		s.add(22);
		s.add(50);
		s.add(10);
		s.add(60);
		s.add(53);
		s.add(55);
		s.add(25);
		Iterator<Integer> i = s.iterator();
		boolean ans = true;
		i.next();
		i.next();
		i.next();
		i.next();
		i.next();
		i.next();
		i.next();
		i.next();
		boolean res = i.hasNext();
		assertEquals(ans, res);
	}
	
	// Tests if hasNext() returns false at max element
	@Test
	public void iteratorHasNextMax() {
		SplayTreeSet<Integer> s = new SplayTreeSet<Integer>();
		s.add(30);
		s.add(21);
		s.add(22);
		s.add(50);
		s.add(10);
		s.add(60);
		s.add(53);
		s.add(55);
		s.add(25);
		Iterator<Integer> i = s.iterator();
		boolean ans = false;
		i.next();
		i.next();
		i.next();
		i.next();
		i.next();
		i.next();
		i.next();
		i.next();
		i.next();
		boolean res = i.hasNext();
		assertEquals(ans, res);
	}
}