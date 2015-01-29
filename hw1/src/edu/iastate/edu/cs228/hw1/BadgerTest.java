package edu.iastate.edu.cs228.hw1;

import java.io.FileNotFoundException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test the Badger class for accuracies.
 * 
 * @author Ian Malerich
 */
public class BadgerTest 
{
	private static World w;
	private static World n;
	
	@Test
	public void testBadgerAge() throws FileNotFoundException
	{
		w = new World("data/badger/age.txt");
		w.grid[1][1] = new Badger(w, 1, 1, 3);
		n = new World(w.getWidth());
		PredatorPrey.updateWorld(w, n);
		
		Assert.assertEquals("A badger of age 3 should be Empty after one world udpate.", Empty.class, n.grid[1][1].getClass());
	}
	
	@Test
	public void testEatenByFox() throws FileNotFoundException
	{
		w = new World("data/badger/fox.txt");
		n = new World(w.getWidth());
		PredatorPrey.updateWorld(w, n);
		
		Assert.assertEquals("A badger should turn to fox if only one badger, yet more than one fox.", Fox.class, n.grid[1][1].getClass());
	}
	
	@Test
	public void testStarve() throws FileNotFoundException
	{
		w = new World("data/badger/starve.txt");
		n = new World(w.getWidth());
		PredatorPrey.updateWorld(w, n);
		
		Assert.assertEquals("Badgers and foxes outnumber the rabbits, should be empty to starvation.", Empty.class, n.grid[1][1].getClass());
	}
	
	@Test
	public void testBadger() throws FileNotFoundException
	{
		w = new World("data/badger/badger.txt");
		n = new World(w.getWidth());
		PredatorPrey.updateWorld(w, n);
		
		Assert.assertEquals("None of the conditions are met, should still be a badger.", Badger.class, n.grid[1][1].getClass());
	}
	
	@Test
	public void testState() throws FileNotFoundException
	{
		w = new World("data/badger/age.txt");
		
		Assert.assertEquals("A badgers state should be State.BADGER", State.BADGER, w.grid[1][1].who());
	}
}
