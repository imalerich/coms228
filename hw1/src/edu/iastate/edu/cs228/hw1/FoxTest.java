package edu.iastate.edu.cs228.hw1;

import java.io.FileNotFoundException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test the Fox class for accuracies.
 * 
 * @author Ian Malerich
 */
public class FoxTest 
{
	private World w;
	private World n;
	
	@Test
	public void testAge() throws FileNotFoundException
	{
		w = new World("data/fox/age.txt");
		w.grid[1][1] = new Fox(w, 1, 1, 4);
		n = new World(w.getWidth());
		PredatorPrey.updateWorld(w, n);
		
		Assert.assertEquals("A Fox of age 4 should be Empty after one world udpate.", Empty.class, n.grid[1][1].getClass());
	}
	
	@Test
	public void testBadger() throws FileNotFoundException
	{
		w = new World("data/fox/badger.txt");
		n = new World(w.getWidth());
		PredatorPrey.updateWorld(w, n);
		
		Assert.assertEquals("Should be badger when there are at least as many badgers as foxes.", Badger.class, n.grid[1][1].getClass());
	}
	
	@Test
	public void testStarve() throws FileNotFoundException
	{
		w = new World("data/fox/starve.txt");
		n = new World(w.getWidth());
		PredatorPrey.updateWorld(w, n);
		
		Assert.assertEquals("Should be empty if badgers and foxes outnumber the rabbits.", Empty.class, n.grid[1][1].getClass());
	}
	
	@Test
	public void testLiveOn() throws FileNotFoundException
	{
		w = new World("data/fox/continue.txt");
		n = new World(w.getWidth());
		PredatorPrey.updateWorld(w, n);
		
		Assert.assertEquals("If all other states, the Fox should live on.", Fox.class, n.grid[1][1].getClass());
	}
	
	@Test
	public void testWho() throws FileNotFoundException
	{
		w = new World("data/fox/age.txt");
		
		Assert.assertEquals("A Foxes state should be State.FOX", State.FOX, w.grid[1][1].who());
	}
}
