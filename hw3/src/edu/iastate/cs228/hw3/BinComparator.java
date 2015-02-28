package edu.iastate.cs228.hw3;

import java.util.Comparator;

/**
 * Will compare two Node objects relative to their bin number.
 * 
 * @author Ian Malerich
 *
 */
public class BinComparator implements Comparator<Node>
{
	public int compare(Node n1, Node n2)
	{
		return n1.bin - n2.bin;  
	}
}
