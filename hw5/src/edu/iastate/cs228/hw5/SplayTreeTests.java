package edu.iastate.cs228.hw5;

import java.util.Iterator;

import org.junit.Test;

public class SplayTreeTests 
{
	@Test
	public void util()
	{
		SplayTreeSet<Integer> s = new SplayTreeSet<Integer>();
		s.add(10000);
		s.add(3);
		s.add(20);
		s.add(8);
		s.add(22);
		s.add(1);
		s.add(0);
		s.add(100);
		s.add(86);
		s.remove(22);
		s.remove(100);
		
		Iterator<Integer> i = s.iterator();
		while (i.hasNext())
			System.out.print(i.next() + ", ");
		System.out.println();
		
	}
}
