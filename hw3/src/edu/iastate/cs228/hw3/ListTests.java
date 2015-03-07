package edu.iastate.cs228.hw3;

import java.io.FileNotFoundException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for cs228 homework 3.
 * 
 * @author imalerich
 *
 */
public class ListTests 
{
	@Test
	public void testConstructor() 
	{
		try {
			DoublySortedList myList = new DoublySortedList("dat/sample.txt");
		} catch (FileNotFoundException e){
			Assert.fail("Error - FileNotFoundException, doest the file dat/sample.txt exist?");
		}
	}

}
