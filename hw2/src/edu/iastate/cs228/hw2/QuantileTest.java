package edu.iastate.cs228.hw2;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test Quantiles.java.
 * 
 * @author Ian Malerich 
 */
public class QuantileTest 
{
	private Quantiles q;

	/**
	 * Test the extra credit constructor of the Quantiles class.
	 */
	@Test
	public void testFast()
	{
		int [] arr = {15, 17, 9, 35, 23, 2, 11, 18, 5, 6};
		q = new Quantiles(arr,4,true);
		Assert.assertEquals("Expected 4", 3, q.getQ());
		Assert.assertEquals("Expected 10", 10, q.size());
		Assert.assertEquals("Expected 6", 6, q.getQuantile(1));
		Assert.assertEquals("Expected 11", 11, q.getQuantile(2));
		Assert.assertEquals("Expected 18", 18, q.getQuantile(3));
		Assert.assertEquals("Expected 13", 13, q.getBottomTotal());
		Assert.assertEquals("Expected 58", 58, q.getTopTotal());
		Assert.assertEquals("Expected 58", 3, q.getQ());
	}
	
	/**
	 * Test the quick constructor of the Quantiles class.
	 */
	@Test
	public void testQuick()
	{
		// initialize the array with no doubles
		int[] arr = new int[100];
		for (int i=0; i<arr.length; i++)
			arr[i] = i+1;
		OrderTest.shuffle(arr);
		int qsize = 6;
		
		q = new Quantiles(arr, qsize, true);
		String actual = q.toString();
		
		q = new Quantiles(arr, qsize, false);
		String expected = q.toString();
		
		System.out.println("Expected:\t" + expected);
		System.out.println("Actual:\t\t" + actual);
		Assert.assertTrue("Outputs should be equal", expected.equals(actual));
	}
	
	/**
	 * Test the get methods of the Quantiles class.
	 */
	@Test
	public void testGet()
	{
		int[] arr = { 15, 17, 9, 35, 23, 2, 11, 18, 5, 6 };
		
		// test with 4-quantiles
		q = new Quantiles(arr, 4, true);
		Assert.assertEquals("Expected 3 quantiles.", 3, q.getQ());
		Assert.assertEquals("Expected array size is 10.", 10, q.size());
		Assert.assertEquals("1st quartile should be 6", 6, q.getQuantile(1));
		Assert.assertEquals("2nd quartile should be 11", 11, q.getQuantile(2));
		Assert.assertEquals("3rd quartile should be 18", 18, q.getQuantile(3));
		Assert.assertEquals("Expected bottom total is 13", 13, q.getBottomTotal());
		Assert.assertEquals("Expected top total is 58.", 58, q.getTopTotal());
		Assert.assertTrue("Unexpected ineqRatio.", Math.abs(4.46153f-q.ineqRatio())<0.00001f);
		
		// test with 3-quantiles
		q = new Quantiles(arr, 3, true);
		Assert.assertEquals("Expected 2 quantiles.", 2, q.getQ());
		Assert.assertEquals("Expected array size is 10.", 10, q.size());
		Assert.assertEquals("1st 3-quantile should be 9", 9, q.getQuantile(1));
		Assert.assertEquals("2nd 3-quantile should be 17", 17, q.getQuantile(2));
		Assert.assertEquals("Expected bottom total is 22", 22, q.getBottomTotal());
		Assert.assertEquals("Expected top total is 76.", 76, q.getTopTotal());
		Assert.assertTrue("Unexpected ineqRatio.", Math.abs(3.45454545f - q.ineqRatio()) < 0.00001f);
	}
	
	/**
	 * Test the String output of the Quantiles class.
	 */
	@Test
	public void testOutput()
	{
		String expected;
		int[] arr = { 15, 17, 9, 35, 23, 2, 11, 18, 5, 6 };
		
		q = new Quantiles(arr, 4, true);
		expected = "10, 4, [6, 11, 18], 58, 13\n";
		Assert.assertTrue("Error - Output strings do not match.", expected.equals(q.toString()));
		
		q = new Quantiles(arr, 3, true);
		expected = "10, 3, [9, 17], 76, 22\n";
		Assert.assertTrue("Error - Ouput strings do not match.", expected.equals(q.toString()));
	}
	
	/**
	 * Test the quantileQuery method.
	 */
	@Test
	public void testQuery()
	{
		int[] arr = { 15, 17, 9, 35, 23, 2, 11, 18, 5, 6 };
		q = new Quantiles(arr, 4, true);
		
		Assert.assertEquals("Expected quantile is I", 1, q.quantileQuery(2));
		Assert.assertEquals("Expected quantile is I", 1, q.quantileQuery(6));
		Assert.assertEquals("Expected quantile is II", 2, q.quantileQuery(7));
		Assert.assertEquals("Expected quantile is II", 2, q.quantileQuery(11));
		Assert.assertEquals("Expected quantile is III", 3, q.quantileQuery(12));
		Assert.assertEquals("Expected quantile is III", 3, q.quantileQuery(13));
		Assert.assertEquals("Expected quantile is III", 3, q.quantileQuery(16));
		Assert.assertEquals("Expected quantile is III", 3, q.quantileQuery(18));
		Assert.assertEquals("Expected quantile is IV", 4, q.quantileQuery(20));
	}
}
