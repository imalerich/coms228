package edu.iastate.cs228.hw2;

import java.util.Arrays;

/**
 * A class for generating and analyzing q-quantiles of datasets
 * 
 * @author Ian Malerich
 */
public class Quantiles 
{
    /* Variables to store the quantiles, size of the data, totals, etc */
	private int n; // the length of the original data array
	private int q; // the number of quantiles
	private int[] quantiles; // store the q-1 q-quantiles
	private int topTotal;
	private int botTotal;
	
	/**
	 * Help function to be used by constructors to initialize this class.
	 * Exception handling should be handled by each constructor.
	 * 
	 * @param data
	 * 	The integers to be split into q-quantiles.
	 * @param q
	 * 	The number of q-quantiles to create.
	 */
	private void init(int[] data, int q)
	{
    	// set the class data
    	this.q = q;
    	n = data.length;
    	
    	// create the new quantile array
    	quantiles = new int[q-1];
    	for (int i=0; i<quantiles.length; i++) {
    		int c = (int)Math.ceil((i+1) * (float)n/q);
    		quantiles[i] = OrderStatistics.findOrderStatistic(data, c);
    	}
	}
	
     /**
      * The expected time complexity of this method must be
      * O(n log q), where n = data.length, where n = data.length (for this, you
      * must implement the method such as the one outlined in Section 2.2.
      * 
      * @param data
      *  The integers to be split into q-quantiles.
      * @param q
      *	 The number of q-quantiles to create.
      */
	private void quickInit(int[] data, int q)
	{
		//set the class data
		this.q = q;
		n = data.length;
		
		// create the new quantile array
		quantiles = new int[q-1];
		quickSelect(data, 0, data.length-1, q, 0);
	}
	
	/**
	 * Finds the median of the data set, then recurses down to find the other
	 * quantiles by spliting the array in half.
	 * @param data
	 * 	Data input array to be parsed.
	 * @param first
	 * 	Starting index [inclusive] for the array subset to be parsed.
	 * @param last
	 * 	Ending index [inclusive] for the array subset to be parsed.
	 * @param q
	 * 	Local quantile count, relative to the array subset.
	 * @param kstart
	 * 	Used as an offset for the quickstart array when recursing.
	 * 	Root call will have this value at 0.
	 */
	private void quickSelect(int[] data, int first, int last, int q, int kstart)
	{
		int n = last-first+1;
		int k = (int)Math.ceil((q-1)/2f);
    	int c = (int)Math.ceil((k) * (float)n/q);
    	
    	quantiles[kstart + k-1] = OrderStatistics.select(data, first, last, c);
    	
    	// parse left subset
    	if (k > 1) {
    		int low = (int)Math.ceil(k * (float)n/q) - 1;
    		quickSelect(data, first, first+low, k, kstart);
    	}
    	
    	// parse right subset
    	if (k < q-1) {
    		int high = n - (int)Math.ceil(k * (float)n/q);
    		quickSelect(data, last-high+1, last, q-k, kstart + k);
    	}
	}
	
	/**
	 * Calculates topTotal and botTotal from the input data set.
	 * 
	 * @param data
	 * 	The data used to calculate topTotal and botTotal.
	 */
	private void getTotals(int[] data)
	{
		for (int i : data) {
			// all values that are smaller or equal to the 1st quantile
			if (i <= getQuantile(1))
				botTotal += i;
			
			// all values that are larger than the (q-1)th quantile
			if (i > getQuantile(q-1))
				topTotal += i;
		}
	}

    /**
     * A constructor that creates a new Quantiles object, whose quantiles array
     * has length q - 1, and contains the q-quantiles of the data array. The
     * expected time complexity of this method must be O(n � q), or better,
     * where n = data.length.
     * 
     * @param data
     * - The integers to split into q-quantiles
     * @param q
     * - The number of q-quantiles to create
     * 
     * @throws IllegalArgumentException
     * - If q <= 1 or if q > n
     */
    public Quantiles(int[] data, int q) 
    {
    	// test for an illegal argument
    	if (q <= 1 || q > data.length)
    		throw new IllegalArgumentException();
    	
    	init(data, q);
    	getTotals(data);
    }

    /**
     * A constructor that creates a new Quantiles object, whose quantiles array,
     * has length three, and is initialized to contain the three quantiles of
     * data. The expected time complexity of this method must be O(n), where n =
     * data.length.
     * 
     * @param data
     * - The integers to split into q-quantiles
     * @throws IllegalArgumentException
     * - If n < 4
     */
    public Quantiles(int[] data) 
    {
    	if (data.length < 4)
    		throw new IllegalArgumentException();
    	
    	init(data, 4);
    	getTotals(data);
    }

    /**
     * An optional constructor that creates a new Quantiles object, whose
     * quantiles array has length q - 1, and is initialized to contain the
     * q-quantiles of the data array.
     * 
     * If fast is true, then the expected time complexity of this method must be
     * O(n log q), where n = data.length, where n = data.length (for this, you
     * must implement the method such as the one outlined in Section 2.2.
     * 
     * If fast is false, then the expected time complexity of this method is
     * only required to be O(n � q), but may be faster
     * 
     * @param data
     * - The integers to split into q-quantiles
     * @param q
     * - The number of q-quantiles to create
     * @param fast
     * - Flag to request a O(n log q) construction
     * 
     * @throws IllegalArgumentException
     * - If q <= 1 or if q > n
     */
    public Quantiles(int[] data, int q, boolean fast) 
    {
    	// test for an illegal argument
    	if (q <= 1 || q > data.length)
    		throw new IllegalArgumentException();
    	
    	// initialize the data using the normal algorithm
    	if (!fast)
    		init(data, q);
    	else
    		quickInit(data, q);
    	
    	// calculate the top and bottom totals
    	getTotals(data);
    }

    /**
     * Returns the k-th q-quantile of this object. This method must take O(1)
     * time in the worst case.
     * 
     * @param k
     * - Specifies which q-quantile to return
     * @return - The k-th q-quantile
     * 
     * @throws IllegalArgumentException
     * - If k < 1 or k is greater than the number of quantiles of this object
     */
    public int getQuantile(int k) 
    {
    	if (k < 1 || k >= q)
    		throw new IllegalArgumentException();
    	
        return quantiles[k-1];
    }

    /**
     * Returns the number of quantiles in this object. This method must take
     * O(1) time in the worst case.
     * 
     * @return 
     * - The number of quantiles in this object
     */
    public int getQ() 
    {
        return q-1;
    }

    /**
     * Returns the index of the quantile group that contains x:
     * 
     * - If x is less than or equal to the first quantile, then return 1.
     * 
     * - If x is strictly greater than the last quantile (quantile q - 1), then
     * return q.
     * 
     * - Otherwise, return the smallest index k such that x is less than or
     * equal to the k-th q- quantile.
     * 		
		index = r.nextInt(arr.length-1) + 1;
		res = OrderStatistics.select(arr, 0, arr.length-1, index);
		Assert.assertEquals("300th smallest value should be " + sorted[index-1], sorted[index-1], res);
     * This method must take O(log k) time in the worst case.
     * 
     * @param x
     * - The item to find the quantile of
     * @return 
     * - The quantile containing x as described above
     */
    public int quantileQuery(int x) 
    {
        return quantileQuery(x, 1, q);
    }

    /**
     * Returns the sum of all values that are strictly higher than the (q - 1)th
     * q-quantile in the original data array. This method must take O(1) time in
     * the worst case.
     * 
     * @return - sum of all values that are strictly higher than the (q - 1)th
     * q-quantile
     */
    public int getTopTotal() 
    {
        return topTotal;
    }

    /**
     * Returns the sum of all vales that are smaller than or equal to the first
     * quantile in the original data array. This method must take O(1) time in
     * the worst case.
     * 
     * @return - the sum of all vales that are smaller than or equal to the
     * first quantile
     */
    public int getBottomTotal() 
    {
        return botTotal;
    }

    /**
     * Returns the ratio of getTopTotal() to getBottomTotal() for this object.
     * This method must take O(1) time in the worst case.
     * 
     * @return - the ratio
     */
    public float ineqRatio() 
    {
        return (float)topTotal/botTotal;
    }

    /**
     * Returns the length of the original data array. This method must take O(1)
     * time in the worst case.
     * 
     * @return - the size of the original data array
     */
    public int size() 
    {
        return n;
    }

    /**
     * Overrides the toString() method, returning a String in the format given
     * on page 6 of the project specifications.
     * 
     * @return - the String representation of this object
     */
    @Override
    public String toString() 
    {
        return (n + ", " + q + ", " + Arrays.toString(quantiles) + ", " + topTotal + ", " + botTotal + '\n');
    }
    
    /**
     * Searches for which quantile x exists in, using a binary search pattern.
     * This should execute at a worst case of O(log(k)).
     * 
     * @param x
     * 	The input we want the quantile for.
     * @param lower
     * 	The lower bound quantile, x is known to be larger.
     * @param upper
     * 	The upper bound quantile, x is known to be smaller or equal.
     * @return
     * 	The quantile in which x exists.
     */
    private int quantileQuery(int x, int lower, int upper)
    {
    	if (lower == upper)
    		return lower;
    	
    	int mid = (lower+upper)/2;
    	if (getQuantile(mid) > x)
    		return quantileQuery(x, lower, mid);
    	else if (getQuantile(mid) < x)
    		return quantileQuery(x, mid+1, upper);
    	else
    		return mid;
    }
}
