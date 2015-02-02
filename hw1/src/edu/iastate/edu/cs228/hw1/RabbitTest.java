package edu.iastate.edu.cs228.hw1;

import java.io.FileNotFoundException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test the Rabbit class for accuracies.
 * 
 * @author Ian Malerich
 */
public class RabbitTest 
{
	private World w;
	private World n;
	
	@Test
	public void testAge() throws FileNotFoundException
	{
		w = new World("data/rabbit/age.txt");
		w.grid[1][1] = new Rabbit(w, 1, 1, 3);
		n = new World(w.getWidth());
		PredatorPrey.updateWorld(w, n);
		
		Assert.assertEquals("A Rabbit of age 3 should be Empty after one world udpate.", Empty.class, n.grid[1][1].getClass());
	}
	
	@Test
	public void testStarve() throws FileNotFoundException
	{
		w = new World("data/rabbit/starve.txt");
		n = new World(w.getWidth());
		PredatorPrey.updateWorld(w, n);
		
		Assert.assertEquals("Rabbit should be empty after starvation.", Empty.class, n.grid[1][1].getClass());
	}
	
	@Test
	public void testEaten() throws FileNotFoundException
	{
		w = new World("data/rabbit/eaten.txt");
		n = new World(w.getWidth());
		PredatorPrey.updateWorld(w, n);
		
		Assert.assertEquals("Rabbit should be empty after eaten byt a fox or badger.", Empty.class, n.grid[1][1].getClass());
	}
	
	@Test
	public void testLiveOn() throws FileNotFoundException
	{
		w = new World("data/rabbit/continue.txt");
		n = new World(w.getWidth());
		PredatorPrey.updateWorld(w, n);
		
		Assert.assertEquals("Rabbit should continue as a rabbit.", Rabbit.class, n.grid[1][1].getClass());
	}
	
	@Test
	public void testState() throws FileNotFoundException
	{
		w = new World("data/rabbit/age.txt");
		
		Assert.assertEquals("A rabbit should have a state of State.RABBIT", w.grid[1][1].who(), State.RABBIT);
	}
}
