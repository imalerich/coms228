package edu.iastate.cs228.hw2;

import java.util.Arrays;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test OrderStatistics.java.
 * 
 * @author Ian Malerich 
 */
public class OrderTest 
{
	/**
	 * Test the OrderStatistics Selection method.
	 */
	@Test
	public void testSelectMed()
	{
		int res = -1;
		int[] arr = { 15, 17, 9, 35, 23, 2, 11, 18, 5, 6 };
		
		res = OrderStatistics.findMinimum(arr);
		Assert.assertEquals("Minimum value should 2.", 2, res);
		
		res = OrderStatistics.findMedian(arr);
		Assert.assertEquals("Middle value should be 11.", 11, res);
		
		res = OrderStatistics.findMaximum(arr);
		Assert.assertEquals("Maximum value should be 35.", 35, res);
	}
	
	/**
	 * Test selection on the minimum array size.
	 */
	@Test
	public void testSelectMin()
	{
		int res = -1;
		int[] arr = { 6 };
		
		res = OrderStatistics.findMinimum(arr);
		Assert.assertEquals("Minimum value should be 6.", 6, res);
	}
	
	/**
	 * Test selection on a small array.
	 */
	@Test
	public void testSelectSmall()
	{
		int res = -1;
		int[] arr = { 2, 14, 1 };
		
		res = OrderStatistics.findMinimum(arr);
		Assert.assertEquals("Minimum value should 1.", 1, res);
		
		res = OrderStatistics.findMedian(arr);
		Assert.assertEquals("Middle value should be 2.", 2, res);
		
		res = OrderStatistics.findMaximum(arr);
		Assert.assertEquals("Maximum value should be 14.", 14, res);
	}
	
	/**
	 * Test selection methods on a large array.
	 */
	@Test
	public void testSelectLarge()
	{
		int res = -1;
		int[] arr = new int[1000];
		
		// initialize the array with no doubles
		for (int i=0; i<arr.length; i++)
			arr[i] = i+1;
		shuffle(arr);
		
		// clone and sort the array
		int[] sorted = arr.clone();
		Arrays.sort(sorted);
		
		// find the max, med, and min 
		res = OrderStatistics.findMinimum(arr);
		Assert.assertEquals("Minimum value should be " + sorted[0], sorted[0], res);
		
		res = OrderStatistics.findMaximum(arr);
		Assert.assertEquals("Maximum value should be " + sorted[arr.length-1], sorted[arr.length-1], res);
		
		int med = (int)Math.ceil(arr.length/2f);
		res = OrderStatistics.findMedian(arr);
		Assert.assertEquals("Median value should be " + sorted[med-1], sorted[med-1], res);
		
		// test some arbitrary values
		res = OrderStatistics.findOrderStatistic(arr, 300);
		Assert.assertEquals("300th smallest value should be " + sorted[299], sorted[299], res);
		
		res = OrderStatistics.findOrderStatistic(arr, 678);
		Assert.assertEquals("678th smallest value should be " + sorted[677], sorted[677], res);
	}
	
	/**
	 * Test selection methods on a very large array.
	 */
	@Test
	public void testSelectVeryLarge()
	{
		int index = -1;
		int res = -1;
		int[] arr = new int[99999];
		Random r = new Random(6);
		
		// initialize the array with no doubles
		for (int i=0; i<arr.length; i++)
			arr[i] = i+1;
		shuffle(arr);
		
		// clone and sort the array
		int[] sorted = arr.clone();
		Arrays.sort(sorted);
		
		// find the max, med, and min 
		res = OrderStatistics.findMinimum(arr);
		Assert.assertEquals("Minimum value should be " + sorted[0], sorted[0], res);
		
		res = OrderStatistics.findMaximum(arr);
		Assert.assertEquals("Maximum value should be " + sorted[arr.length-1], sorted[arr.length-1], res);
		
		int med = (int)Math.ceil(arr.length/2f);
		res = OrderStatistics.findMedian(arr);
		Assert.assertEquals("Median value should be " + sorted[med-1], sorted[med-1], res);
		
		// test some arbitrary values
		index = r.nextInt(arr.length-1) + 1;
		res = OrderStatistics.findOrderStatistic(arr, index);
		Assert.assertEquals("300th smallest value should be " + sorted[index-1], sorted[index-1], res);
		
		index = r.nextInt(arr.length-1) + 1;
		res = OrderStatistics.findOrderStatistic(arr, index);
		Assert.assertEquals("300th smallest value should be " + sorted[index-1], sorted[index-1], res);
		
		index = r.nextInt(arr.length-1) + 1;
		res = OrderStatistics.findOrderStatistic(arr, index);
		Assert.assertEquals("300th smallest value should be " + sorted[index-1], sorted[index-1], res);
	}
	
	/**
	 * Utility shuffle method for creating a very large random array with no repeating values.
	 * @param arr
	 * 	Array to shuffle.
	 */
	public static void shuffle(int[] arr)
	{
		Random r = new Random(1);
		for (int i=0; i<arr.length; i++)
		{
			int index = r.nextInt(arr.length);
			
			int tmp = arr[index];
			arr[index] = arr[i];
			arr[i] = tmp;
		}
	}
}
