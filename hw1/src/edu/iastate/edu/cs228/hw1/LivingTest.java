package edu.iastate.edu.cs228.hw1;

import java.io.FileNotFoundException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test the Living class for accuracies.
 * 
 * @author Ian Malerich
 */
public class LivingTest 
{
	private World w;
	
	@Test
	public void testOne() throws FileNotFoundException
	{
		w = new World("data/living/1x1.txt");
		int pop[] = new int[Living.NUM_LIFE_FORMS];
		w.grid[0][0].census(pop);
		
		Assert.assertEquals("World of 1x1 should only have one neighbor.", 1, getNeighborCount(pop));
	}
	
	@Test
	public void testTwo() throws FileNotFoundException
	{
		w = new World("data/living/2x2.txt");
		int pop[] = new int[Living.NUM_LIFE_FORMS];
		
		/* ------------------------------------------------------------------------------------------------------
		 * 	Test each corner.
		 * ----------------------------------------------------------------------------------------------------*/
		w.grid[0][0].census(pop);
		Assert.assertEquals("World of 2x2 should have four neighbors at all corners.", 4, getNeighborCount(pop));
		
		w.grid[0][1].census(pop);
		Assert.assertEquals("World of 2x2 should have four neighbors at all corners.", 4, getNeighborCount(pop));
		
		w.grid[1][0].census(pop);
		Assert.assertEquals("World of 2x2 should have four neighbors at all corners.", 4, getNeighborCount(pop));
		
		w.grid[1][1].census(pop);
		Assert.assertEquals("World of 2x2 should have four neighbors at all corners.", 4, getNeighborCount(pop));
	}
	
	@Test
	public void testTwoNeighbors() throws FileNotFoundException
	{
		w = new World("data/living/2x2.txt");
		int pop[] = new int[Living.NUM_LIFE_FORMS];
		
		w.grid[0][0].census(pop);
		Assert.assertEquals("Top right has two Empty neighbors.", 2, pop[State.EMPTY.ordinal()]);
		Assert.assertEquals("Top right has one Fox neighbor.", 1, pop[State.FOX.ordinal()]);
		Assert.assertEquals("Top right has one Badger neighbor.", 1, pop[State.BADGER.ordinal()]);
		Assert.assertEquals("Top right has no Grass neighbors.", 0, pop[State.GRASS.ordinal()]);
		Assert.assertEquals("Top right has no Rabbit neighbors.", 0, pop[State.RABBIT.ordinal()]);
	}
	
	@Test
	public void testThree() throws FileNotFoundException
	{
		w = new World("data/living/3x3.txt");
		int pop[] = new int[Living.NUM_LIFE_FORMS];
		
		/* ------------------------------------------------------------------------------------------------------
		 * 	Test each corner.
		 * ----------------------------------------------------------------------------------------------------*/
		w.grid[0][0].census(pop);
		Assert.assertEquals("World of 3x3 should have four neighbors at the corners.", 4, getNeighborCount(pop));
		
		w.grid[2][0].census(pop);
		Assert.assertEquals("World of 3x3 should have four neighbors at the corners.", 4, getNeighborCount(pop));
		
		w.grid[0][2].census(pop);
		Assert.assertEquals("World of 3x3 should have four neighbors at the corners.", 4, getNeighborCount(pop));
		
		w.grid[2][2].census(pop);
		Assert.assertEquals("World of 3x3 should have four neighbors at the corners.", 4, getNeighborCount(pop));
		
		/* ------------------------------------------------------------------------------------------------------
		 * 	Test the middle of each side.
		 * ----------------------------------------------------------------------------------------------------*/
		w.grid[1][0].census(pop);
		Assert.assertEquals("World of 3x3 should have six neighbors along the sides.", 6, getNeighborCount(pop));
		
		w.grid[0][1].census(pop);
		Assert.assertEquals("World of 3x3 should have six neighbors along the sides.", 6, getNeighborCount(pop));
		
		w.grid[1][2].census(pop);
		Assert.assertEquals("World of 3x3 should have six neighbors along the sides.", 6, getNeighborCount(pop));
		
		w.grid[2][1].census(pop);
		Assert.assertEquals("World of 3x3 should have six neighbors along the sides.", 6, getNeighborCount(pop));
		
		/* ------------------------------------------------------------------------------------------------------
		 * 	Test the center.
		 * ----------------------------------------------------------------------------------------------------*/
		w.grid[1][1].census(pop);
		Assert.assertEquals("World of 3x3 should have nine neighbors at the center.", 9, getNeighborCount(pop));
	}
	
	@Test
	public void testThreeNeighbors() throws FileNotFoundException
	{
		w = new World("data/living/3x3.txt");
		int pop[] = new int[Living.NUM_LIFE_FORMS];
		
		/* ------------------------------------------------------------------------------------------------------
		 * 	Test the center.
		 * ----------------------------------------------------------------------------------------------------*/
		w.grid[1][1].census(pop);
		Assert.assertEquals("Two Fox neighbors expected.", 2, pop[State.FOX.ordinal()]);
		Assert.assertEquals("Three Grass neighbors expected.", 3, pop[State.GRASS.ordinal()]);
		Assert.assertEquals("One Rabbit neighbor expected.", 1, pop[State.RABBIT.ordinal()]);
		
		/* ------------------------------------------------------------------------------------------------------
		 * 	Test an edge.
		 * ----------------------------------------------------------------------------------------------------*/
		w.grid[1][2].census(pop);
		Assert.assertEquals("One Empty neighbor expected.", 1, pop[State.EMPTY.ordinal()]);
		Assert.assertEquals("Two Grass neighbors expected.", 2, pop[State.GRASS.ordinal()]);
		Assert.assertEquals("Two Fox neighbors expected.", 2, pop[State.FOX.ordinal()]);
		Assert.assertEquals("One Badger neighbor expected.", 1, pop[State.BADGER.ordinal()]);
	}
	
	@Test
	public void testFour() throws FileNotFoundException
	{
		w = new World("data/living/4x4.txt");
		int pop[] = new int[Living.NUM_LIFE_FORMS];
		
		/* ------------------------------------------------------------------------------------------------------
		 * 	Test each corner.
		 * ----------------------------------------------------------------------------------------------------*/
		w.grid[0][0].census(pop);
		Assert.assertEquals("World of 4x4 should have four neighbors at the corners.", 4, getNeighborCount(pop));
		
		w.grid[3][0].census(pop);
		Assert.assertEquals("World of 4x4 should have four neighbors at the corners.", 4, getNeighborCount(pop));
		
		w.grid[0][3].census(pop);
		Assert.assertEquals("World of 4x4 should have four neighbors at the corners.", 4, getNeighborCount(pop));
		
		w.grid[3][3].census(pop);
		Assert.assertEquals("World of 4x4 should have four neighbors at the corners.", 4, getNeighborCount(pop));
		
		/* ------------------------------------------------------------------------------------------------------
		 * 	Test a few side cases.
		 * ----------------------------------------------------------------------------------------------------*/
		w.grid[1][0].census(pop);
		Assert.assertEquals("World of 4x4 should have six neighbors along the sides.", 6, getNeighborCount(pop));
		
		w.grid[0][1].census(pop);
		Assert.assertEquals("World of 4x4 should have six neighbors along the sides.", 6, getNeighborCount(pop));
		
		w.grid[3][2].census(pop);
		Assert.assertEquals("World of 4x4 should have six neighbors along the sides.", 6, getNeighborCount(pop));
		
		/* ------------------------------------------------------------------------------------------------------
		 * 	Test a few center cases.
		 * ----------------------------------------------------------------------------------------------------*/
		w.grid[1][1].census(pop);
		Assert.assertEquals("World of 4x4 should have nine neighbors in the center.", 9, getNeighborCount(pop));
		
		w.grid[2][1].census(pop);
		Assert.assertEquals("World of 4x4 should have nine neighbors in the center.", 9, getNeighborCount(pop));
		
		w.grid[2][2].census(pop);
		Assert.assertEquals("World of 4x4 should have nine neighbors in the center.", 9, getNeighborCount(pop));
	}
	
	/**
	 * Take a population array generated by census, and return the total number
	 *  of neighbors in that population.
	 * @param pop
	 *  The input population retrieved from the census method.
	 * @return
	 *  Total number of neighbors for that population.
	 */
	private int getNeighborCount(int pop[])
	{
		int size = 0;
		for (int i : pop)
			size += i;
		
		return size;
	}
}
