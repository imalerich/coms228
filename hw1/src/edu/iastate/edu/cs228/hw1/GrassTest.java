package edu.iastate.edu.cs228.hw1;

import java.io.FileNotFoundException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test the Grass class for accuracies.
 * 
 * @author Ian Malerich
 */
public class GrassTest 
{
	private World w;
	private World n;
	
	@Test
	public void testEatenByRabbit() throws FileNotFoundException
	{
		w = new World("data/grass/eaten.txt");
		n = new World(w.getWidth());
		PredatorPrey.updateWorld(w, n);
		
		Assert.assertEquals("If there are at least twice as many rabbits, should be empty.", Empty.class, n.grid[1][1].getClass());
	}
	
	@Test
	public void testRabbit() throws FileNotFoundException
	{
		w = new World("data/grass/rabbit.txt");
		n = new World(w.getWidth());
		PredatorPrey.updateWorld(w, n);
		
		Assert.assertEquals("There are more rabbits, should now be rabbit.", Rabbit.class, n.grid[1][1].getClass());
	}
	
	@Test
	public void testGrass() throws FileNotFoundException
	{
		w = new World("data/grass/grass.txt");
		n = new World(w.getWidth());
		PredatorPrey.updateWorld(w, n);
		
		Assert.assertEquals("The grass should continue to be grass.", Grass.class, n.grid[1][1].getClass());
	}
	
	@Test
	public void testWho() throws FileNotFoundException
	{
		w = new World("data/grass/grass.txt");
		
		Assert.assertEquals("Grass should identify as State.GRASS", State.GRASS, w.grid[1][1].who());
	}
}
