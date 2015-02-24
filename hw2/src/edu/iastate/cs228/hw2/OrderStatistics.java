package edu.iastate.cs228.hw2;


/**
 * A class used to generate order statistics of data sets
 * 
 * @author Ian Malerich
 */
public class OrderStatistics 
{
	/**
	 * Returns the minimum element (first order statistic) in array arr. This
	 * method must run in worst-case O(n) time, where n = arr.length, using a
	 * linear scan of the input array.
	 * 
	 * @param arr
	 * - The data to search
	 * @return - the minimum element of arr
	 */
	public static int findMinimum(int[] arr) 
	{
		return findOrderStatistic(arr, 1);
	}

	/**
	 * Returns the maximum element (first order statistic) in array arr. This
	 * method must run in worst-case O(n) time, where n = arr.length, using a
	 * linear scan of the input array.
	 * 
	 * @param arr
	 * - The data to search
	 * @return - the maximum element of arr
	 */
	public static int findMaximum(int[] arr) 
	{
		return findOrderStatistic(arr, arr.length);
	}

	/**
	 * An implementation of the SELECT algorithm of Figure 1 of the project
	 * specification. Returns the ith order statistic in the subarray
	 * arr[first], ..., arr[last]. The method must run in O(n) expected time,
	 * where n = (last - first + 1).
	 * 
	 * @param arr
	 * - The data to search in
	 * @param first
	 * - The leftmost boundary of the subarray (inclusive)
	 * @param last
	 * - The rightmost boundary of the subarray (inclusive)
	 * @param i
	 * - The requested order statistic to find
	 * @return 
	 * - The ith order statistic in the subarray
	 * @throws IllegalArgumentException
	 * - If i < 1 or i > n
	 */
	public static int select(int[] arr, int first, int last, int i) 
	{
		// validate the index, if it fails, throw an exception 
		if (i < 1 || i > last - first + 1)
			throw new IllegalArgumentException("Index "  + i + " is out of bounds " + 1 + " -> " + (last-first+1) + ".");
		
		if (first == last)
			return arr[first];
		
		int p = partition(arr, first, last);
		int k = p - first + 1;
		if (i == k)
			return arr[p];
		else if (i < k) {
			return select(arr, first, p-1, i);
		} else {
			return select(arr, p+1, last, i-k);
		}
	}

	/**
	 * Returns the ith order statistic of array arr in O(n) expected time, where
	 * n = arr.length.
	 * 
	 * @param arr
	 * - The data to search through
	 * @param i
	 * - The requested order statistic to find in arr
	 * @return - The ith order statistic in arr
	 * 
	 * @throws IllegalArgumentException
	 * - If i < 1 or i > n
	 */
	public static int findOrderStatistic(int[] arr, int i) 
	{
		return select(arr, 0, arr.length-1, i);
	}

	/**
	 * Returns the median (n/2th order statistic rounding up) in array arr in
	 * O(n) expected time, where n = arr.length.
	 * 
	 * @param arr
	 * - The array to find the median of
	 * @return
	 * - The median value of arr
	 */
	public static int findMedian(int[] arr) 
	{
		return findOrderStatistic(arr, (int)Math.ceil(arr.length/2f));
	}
	
	/**
	 * Sorts arr from first to last so everything before the pivot is smaller than the pivot
	 * and everything after the pivot is larger, the separation point for the arrays is then returned.
	 * @param arr
	 * 	The array to draw the subset from.
	 * @param first
	 * 	Inclusive start of the array subset.
	 * @param last
	 * 	Inclusive end of the array subset.
	 * @return
	 * 	The separation point for the arrays.k
	 * @throws IllegalArgumentException
	 * 	Throws an exception if either first or last is negative, or first is > than last.
	 */
	private static int partition(int[] arr, int first, int last)
	{
		if (first > last || first < 0 || last < 0)
			throw new IllegalArgumentException("Invalid First: " + first + " or Last: " + last + " argumnent.");
		
		// pick a random pivot, and set it to the last position
		swap(arr, first + (int)(Math.random() * (last-first+1)), last);
		int p = arr[last];
		int i = first-1;
		
		// move all elements smaller than or equal to p to the left of p, and all elements greater, to the right
		for (int j=first; j<last; j++) {
			if (arr[j] <= p) {
				i++;
				swap(arr, i, j);
			}
		}
		
		// swap the last element with j, so that elements < j are smaller than p
		i++;
		swap(arr, last, i);
		return i;
	}
	
	/**
	 * Swap elements in an array int indecies i0 and i1.
	 * @param arr
	 * 	Array in which to swap indecies.
	 * @param i0
	 * 	First index to swap.
	 * @param i1
	 * 	Second index to swap.
	 */
	private static void swap(int[] arr, int i0, int i1)
	{
		int tmp = arr[i0];
		arr[i0] = arr[i1];
		arr[i1] = tmp;
	}
}
