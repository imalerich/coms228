package edu.iastate.cs228.hw3;

import java.util.Comparator;

/**
 * Will compare two Node objects relative to their fruit name.
 * 
 * @author Ian Malerich
 *
 */
public class NameComparator implements Comparator<Node>
{
	public int compare(Node n1, Node n2)
	{
		return n1.fruit.compareTo(n2.fruit);  
	}
}
