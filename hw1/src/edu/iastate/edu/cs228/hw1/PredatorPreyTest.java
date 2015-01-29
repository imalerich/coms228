package edu.iastate.edu.cs228.hw1;

import java.io.FileNotFoundException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test the PredatorPrey class for accuracies.
 * 
 * @author Ian Malerich 
 */
public class PredatorPreyTest 
{
	private World w;
	private World n;
	private World ans;
	
	@Test
	public void testOneCycle() throws FileNotFoundException
	{
		ans = new World("data/predator/ans0.txt");
		w = new World("data/predator/test0.txt");
		n = new World(w.getWidth());
		runCycles(1);
		
		printExpected();
		Assert.assertTrue("After one cycles, test0 should be equal to ans0", w.equals(ans));
	}
	
	@Test
	public void testEightCycles() throws FileNotFoundException
	{
		ans = new World("data/predator/ans1.txt");
		w = new World("data/predator/test1.txt");
		n = new World(w.getWidth());
		runCycles(8);
		
		printExpected();
		Assert.assertTrue("After eight cycles, test1 should be equal to ans1", w.equals(ans));
	}
	
	@Test
	public void testTenCycles() throws FileNotFoundException
	{
		ans = new World("data/predator/ans2.txt");
		w = new World("data/predator/test2.txt");
		n = new World(w.getWidth());
		runCycles(10);
		
		printExpected();
		Assert.assertTrue("After ten cycles, test2 should be equal to ans2", w.equals(ans));
	}
	
	/**
	 * Print the final world state and the expected world state
	 * for debugging purposes.
	 */
	private void printExpected()
	{
		System.out.println("Final World: \n\n" + w.toString());
		System.out.println();
		System.out.println("Expected World: \n\n" + ans.toString());
		System.out.println();
	}
	
	/**
	 * Run the specified number of cycles to test.
	 * @param cycles
	 * 	Number of cycles to test.
	 */
	private void runCycles(int cycles)
	{
		for (int i=0; i<cycles; i++) {
			PredatorPrey.updateWorld(w, n);
			
			World tmp = w;
			w = n;
			n = tmp;
		}
	}
}
