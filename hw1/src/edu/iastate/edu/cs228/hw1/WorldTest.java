package edu.iastate.edu.cs228.hw1;

import java.io.FileNotFoundException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test the world for accurate behavior.
 *  
 * @author Ian Malerich
 */
public class WorldTest 
{
	private World w;
	
	@Test
	public void testSimpleFile() throws FileNotFoundException
	{
		w = new World("data/world/test0.txt");
		Assert.assertEquals("The input files width should be 2.", w.getWidth(), 2);
		
		for (int r=0; r<w.getWidth(); r++) {
			for (int c=0; c<w.getWidth(); c++) {
				Assert.assertEquals("All inputs should be badgers.", Badger.class, w.grid[r][c].getClass());
			}
		}
	}
	
	@Test
	public void testComplex() throws FileNotFoundException
	{
		w = new World("data/world/test1.txt");
		Assert.assertEquals("The input files width should be 2.", w.getWidth(), 2);
		
		Assert.assertEquals("[0, 0] should be Empty.", Empty.class, w.grid[0][0].getClass());
		Assert.assertEquals("[0, 1] should be a Rabbit.", Rabbit.class, w.grid[0][1].getClass());
		Assert.assertEquals("[1, 0] should be a Fox.", Fox.class, w.grid[1][0].getClass());
		Assert.assertEquals("[1, 1] should be a Badger.", Badger.class, w.grid[1][1].getClass());
	}
	
	@Test
	public void testLarge() throws FileNotFoundException
	{
		w = new World("data/world/test2.txt");
		Assert.assertEquals("The input files width should be 6.", w.getWidth(), 6);
		
		Assert.assertEquals("[0, 5] should be Empty.", Empty.class, w.grid[0][5].getClass());
		Assert.assertEquals("[1, 1] should be a Rabbit.", Rabbit.class, w.grid[1][1].getClass());
		Assert.assertEquals("[2, 1] should be a Badger.", Badger.class, w.grid[2][1].getClass());
		Assert.assertEquals("[4, 2] should be a Fox.", Fox.class, w.grid[4][2].getClass());
		Assert.assertEquals("[5, 3] should be Grass.", Grass.class, w.grid[5][3].getClass());
		Assert.assertEquals("[5, 4] should be Grass.", Grass.class, w.grid[5][4].getClass());
	}
	
	@Test
	public void testInit()
	{
		w = new World(4);
		Assert.assertEquals("The new world should have dimmensions of 4x4", 4, w.getWidth());
		
		w = new World(8);
		Assert.assertEquals("The new world should have dimmensions of 8x8", 8, w.getWidth());
		
		w = new World(12);
		Assert.assertEquals("The new world should have dimmensions of 12x12", 12, w.getWidth());
	}
	
	@Test
	public void testRandomInit()
	{
		w = new World(24);
		w.randomInit();
		Assert.assertEquals("The new world should have dimmensions of 24x24", 24, w.getWidth());
		
		for (int r=0; r<w.getWidth(); r++) {
			for (int c=0; c<w.getWidth(); c++) {
				Assert.assertNotNull("Each element should be initialized and not null", w.grid[r][c]);
			}
		}
	}
	
	@Test
	public void testOutput() throws FileNotFoundException
	{
		w = new World(6);
		w.randomInit();
		w.write("data/world/tmp.txt");
		
		World tmp = new World("data/world/tmp.txt");
		Assert.assertEquals("A world read from the output of write should have the same width.", 6, tmp.getWidth());
		
		for (int r=0; r<w.getWidth(); r++) {
			for (int c=0; c<w.getWidth(); c++) {
				Assert.assertEquals("Each element of the world and the output world should be equal.", 
						w.grid[r][c].getClass(), tmp.grid[r][c].getClass());
			}
		}
	}
}
