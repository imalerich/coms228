package edu.iastate.cs228.hw5;

import org.junit.Test;

public class SplayTreeTests 
{
	@Test
	public void testAdd()
	{
		SplayTreeSet<Integer> s = new SplayTreeSet<Integer>();
		s.add(3);
		s.add(20);
		System.out.println(s.size);
		
		System.out.println(s.toString());
	}
}
