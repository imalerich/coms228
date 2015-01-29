package edu.iastate.edu.cs228.hw1;

import java.io.FileNotFoundException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test the Empty class for accuracies.
 * 
 * @author Ian Malerich
 */
public class EmptyTest 
{
	private static World w;
	private static World n;
	
	@Test
	public void testRabbitReproduction() throws FileNotFoundException
	{
		w = new World("data/empty/rabbit.txt");
		n = new World(w.getWidth());
		PredatorPrey.updateWorld(w, n);
		PredatorPrey.updateWorld(w, n);
		
		Assert.assertEquals("If there is more than one rabbit nearby, should be rabbit.", Rabbit.class, n.grid[1][1].getClass());
	}
	
	@Test
	public void testFox() throws FileNotFoundException
	{
		w = new World("data/empty/fox.txt");
		n = new World(w.getWidth());
		PredatorPrey.updateWorld(w, n);
		
		Assert.assertEquals("After one tick should be, the empty slot should now be Fox, but was " + 
				n.grid[1][1].who(), Fox.class, n.grid[1][1].getClass());
	}
	
	@Test
	public void testBadger() throws FileNotFoundException
	{
		w = new World("data/empty/badger.txt");
		n = new World(w.getWidth());
		PredatorPrey.updateWorld(w, n);
		
		Assert.assertEquals("After one tick should be, the empty slot should now be badger, but was " + 
				n.grid[1][1].who(), Badger.class, n.grid[1][1].getClass());
	}
	
	@Test
	public void testGrass() throws FileNotFoundException
	{
		w = new World("data/empty/grass.txt");
		n = new World(w.getWidth());
		PredatorPrey.updateWorld(w, n);
		
		Assert.assertEquals("After one tick should be, the empty slot should now be grass, but was " + 
				n.grid[1][1].who(), Grass.class, n.grid[1][1].getClass());
	}
	
	@Test
	public void testEmpty() throws FileNotFoundException
	{
		w = new World("data/empty/empty.txt");
		n = new World(w.getWidth());
		PredatorPrey.updateWorld(w, n);
		
		Assert.assertEquals("When all neighbors are empty, this slot should now be empty, but was " + 
				n.grid[1][1].who(), Empty.class, n.grid[1][1].getClass());
	}
	
	@Test
	public void testWho() throws FileNotFoundException
	{
		w = new World("data/empty/empty.txt");
		
		Assert.assertEquals("Empty should identify as State.EMPTY", State.EMPTY, w.grid[1][1].who());
	}
}
