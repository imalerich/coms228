package edu.iastate.cs228.hw4;

/**
 *  
 * @author Ian Malerich
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

/**
 * 
 * This class implements Graham's scan that constructs the convex hull of a finite set of points. 
 *
 */

public class ConvexHull 
{
	// ---------------
	// Data Structures 
	// ---------------
	
	/**
	 * The array points[] holds an input set of Points, which may be randomly generated or 
	 * input from a file.  Duplicates may appear. 
	 */
	private Point[] points;    
	private int numPoints;            // size of points[]

	/**
	 * Lowest point from points[]; and in case of a tie, the leftmost one of all such points. 
	 * To be set by the private method lowestPoint(). 
	 */
	private Point lowestPoint; 

	
	/**
	 * This array stores the same set of points from points[] with all duplicates removed. 
	 */
	private Point[] pointsNoDuplicate; 
	private int numDistinctPoints;    // size of pointsNoDuplicate[]

	
	/**
	 * Points on which Graham's scan is performed.  They are copied from pointsNoDuplicate[] 
	 * with some points removed.  More specifically, if multiple points from the array 
	 * pointsNoDuplicate[] have the same polar angle with respect to lowestPoint, only the one 
	 * furthest away from lowestPoint is included. 
	 */
	private Point[] pointsToScan; 
	private int numPointsToScan;     // size of pointsToScan[]
	
	
	/**
	 * Vertices of the convex hull in counterclockwise order are stored in the array 
	 * hullVertices[], with hullVertices[0] storing lowestPoint. 
	 */
	private Point[] hullVertices;
	private int numHullVertices;     // number of vertices on the convex hull
	
	
	/**
	 * Stack used by Grahma's scan to store the vertices of the convex hull of the points 
	 * scanned so far.  At the end of the scan, it stores the hull vertices in the 
	 * counterclockwise order. 
	 */
	private PureStack<Point> vertexStack;  

	// ------------
	// Constructors
	// ------------
	
	/**
	 * Generate n random points within the box range [-50, 50] x [-50, 50]. Duplicates are 
	 * allowed. Store the points in the private array points[]. 
	 * 
	 * @param n >= 1; otherwise, exception thrown.  
	 */
	public ConvexHull(int n) throws IllegalArgumentException 
	{
		if (n < 1)
			throw new IllegalArgumentException();
		
		points = new Point[n];
		numPoints = n;
		
		// randomly generate a list of points with a size of n
		Random r = new Random();
		for (int i=0; i<n; i++) {
			int x = r.nextInt(101) - 50;
			int y = r.nextInt(101) - 50;
			
			// create a new point using random values
			points[i] = new Point(x, y);
		}
	}
	
	/**
	 * Read integers from an input file.  Every pair of integers represent the x- and y-coordinates 
	 * of a point.  Generate the points and store them in the private array points[]. The total 
	 * number of integers in the file must be even.
	 * 
	 * You may declare a Scanner object and call its methods such as hasNext(), hasNextInt() 
	 * and nextInt(). An ArrayList may be used to store the input integers as they are read in 
	 * from the file.  
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException
	 * @throws InputMismatchException   when the input file contains an odd number of integers
	 */
	@SuppressWarnings("resource")
	public ConvexHull(String inputFileName) throws FileNotFoundException, InputMismatchException
	{
		ArrayList<Integer> data = new ArrayList<Integer>();
		Scanner s = new Scanner(new File(inputFileName));
		
		// read all integers from the input file into the temporary array
		while (s.hasNextInt()) {
			data.add(s.nextInt());
		}
		
		if (data.size() % 2 == 1)
			throw new InputMismatchException();
		
		numPoints = data.size();
		points = new Point[numPoints/2];
		
		for (int i = 0; i < data.size(); i+=2) {
			points[i/2] = new Point(data.get(i), data.get(i+1));
		}
		
		s.close();
	}

	// -------------
	// Graham's scan
	// -------------
	
	/**
	 * This method carries out Graham's scan in several steps below: 
	 * 
	 *     1) Call the private method lowestPoint() to find the lowest point from the input 
	 *        point set and store it in the variable lowestPoint. 
	 *        
	 *     2) Call the private method setUpScan() to sort all points by polar angle with respect
	 *        to lowestPoint.  After elimination of all duplicates in points[], the points are 
	 *        stored in pointsNoDuplicate[].  Next, for multiple points having the same polar angle 
	 *        with respect to lowestPoint, keep only the one furthest from lowestPoint.  The points
	 *        after the second round of elimination are stored in the array pointsToScan[].  
	 *        
	 *     3) Perform Graham's scan on the points in the array pointsToScan[]. To initialize the 
	 *        scan, push pointsToScan[0] and pointsToScan[1] onto vertexStack.  
	 * 
     *     4) As the scan terminates, vertexStack holds the vertices of the convex hull.  Pop the 
     *        vertices out of the stack and add them to the array hullVertices[], starting at index
     *        numHullVertices - 1, and decreasing the index toward 0.  Set numHullVertices.  
     *        
     * Two special cases below must be handled: 
     * 
     *     1) The array pointsToScan[] could contain just one point, in which case the convex
     *        hull is the point itself. 
     *     
     *     2) Or it could contain two points, in which case the hull is the line segment 
     *        connecting them.   
	 */
	public void GrahamScan()
	{
		lowestPoint();
		setUpScan();
		
		// create the vertex stack and add the first two points to it
		vertexStack = new ArrayBasedStack<Point>();
		vertexStack.push(pointsToScan[0]);
		if (pointsToScan.length > 1)
			vertexStack.push(pointsToScan[1]);
		
		// store the points we will use to determine orientation
		Point prev, current, next;
		if (pointsToScan.length > 1)
			prev = pointsToScan[1];
		else
			prev = null;
		
		for (int i=2; i<pointsToScan.length-1; i++) {
			next = pointsToScan[i+1];
			current = pointsToScan[i];
			
			// push the next index onto the scan
			vertexStack.push(pointsToScan[i]);
			
			// if this previous point, this point, and next points do not move counterclockwise, pop this point
			while (isCounterClockwise(prev, current, next) < 0) {
				vertexStack.pop();
				
				// pop to get the current element, peek at the previouse, then put the current back on the stack
				current = vertexStack.pop();
				prev = vertexStack.peek();
				vertexStack.push(current);
			}
			
			prev = vertexStack.peek();
		}
		
		// add the last point and the first point to close off the hull
		if (pointsToScan.length > 2)
			vertexStack.push(pointsToScan[pointsToScan.length-1]);
		vertexStack.push(pointsToScan[0]);
		
		// create the hullVertices array
		numHullVertices = vertexStack.size();
		hullVertices = new Point[numHullVertices];
		for (int i=numHullVertices-1; i>=0; i--) {
			hullVertices[i] = vertexStack.pop();
		}
	}
	
	/**
	 * Utility function to determine the bend within made by the input 3 points.
	 * 
	 * @param p0
	 * 	First point in the arch.
	 * @param p1
	 * 	Second point in the arch.
	 * @param p2
	 * 	Third point in the arch.
	 * @return
	 *  > 0 counterclockwise
	 *  0 collinear
	 *  < 0 clockwise
	 */
	private int isCounterClockwise(Point p0, Point p1, Point p2)
	{
		return (p1.getX() - p0.getX()) * (p2.getY() - p0.getY()) - 
				(p1.getY() - p0.getY()) * (p2.getX() - p0.getX());
	}

	// ------------------------------------------------------------
	// toString() and Files for Convex Hull Plotting in Mathematica
	// ------------------------------------------------------------
	
	/**
	 * The string displays the convex hull with vertices in counter clockwise order starting at  
	 * lowestPoint.  When printed out, it will list five points per line with three blanks in 
	 * between. Every point appears in the format "(x, y)".  
	 * 
	 * For illustration, the convex hull example in the project description will have its 
	 * toString() generate the output below: 
	 * 
	 * (-7, -10)   (0, -10)   (10, 5)   (0, 8)   (-10, 0)   
	 * 
	 * lowestPoint is listed only ONCE.  
	 */
	public String toString()
	{
		String out = "";
		for (int i=1; i<hullVertices.length+1; i++) {
			out += hullVertices[i-1].toString();
			out += i % 5 == 0 ? "\n" : "   ";
		}
		
		return out; 
	}
	
	/** 
	 * For plotting in Mathematica. 
	 * 
	 * Writes to the file "hull.txt" the vertices of the constructed convex hull in counterclockwise 
	 * order for rendering in Mathematica.  These vertices are in the array hullVertices[], starting 
	 * at lowestPoint.  Every line in the file displays the x and y coordinates of only one point.  
	 * Write the coordinates of lowestPoint again to end the file.   
	 * 
	 * For instance, the file "hull.txt" generated for the convex hull example in the project 
	 * description will have the following content: 
	 * 
     *  -7 -10 
     *  0 -10
     *  10 5
     *  0  8
     *  -10 0
     *  -7 -10 
	 * 
	 * Note that lowestPoint (-7, -10) has its coordinates listed in the first and last lines.  This 
	 * is for Mathematica to plot the hull as a polygon rather than one missing the edge connecting
	 * (-10, 0) and (-7, -10).  
	 * 
	 * Called only after GrahamScan().  
	 * 
	 * 
	 * @throws IllegalStateException  if hullVertices[] has not been populated (i.e., the convex 
	 *                                   hull has not been constructed)
	 */
	public void hullToFile() throws IllegalStateException 
	{
		writePointsToFile(hullVertices, "hull.txt");
	}
	
	/**
	 * For plotting in Mathematica. 
	 * 
	 * Writes to the file "points.txt" the points stored in the array pointsNoDuplicate[]. The 
	 * format is the same as required for the method hullToFile(), except that the coordinates 
	 * of lowestPoint appear only once.
	 * 
	 * Called only after setUpScan() or GrahamScan(). 
	 * 
	 * @throws IllegalStateException  if pointsNoDuplicate[] has not been populated. 
	 */
	public void pointsToFile() throws IllegalStateException 
	{
		writePointsToFile(pointsNoDuplicate, "points.txt");
	}
	
	/**
	 * Also implement this method for testing purpose. 
	 * 
	 * Writes to the file "pointsScanned.txt" the points stored in the array pointsToScan[]. The 
	 * format is the same as required for the method pointsToFile(). 
	 * 
	 * Called only after setUpScan() or GrahamScan().  
	 * 
	 * @throws IllegalStateException  if pointsToScan[] has not been populated. 
	 */
	public void pointsScannedToFile() throws IllegalStateException 
	{
		writePointsToFile(pointsToScan, "pointsScanned.txt");
	}
	
	/**
	 * Utility method for all the output methods. Outputs the input arr to the input file
	 * with one point per line and one space between the x and y coordinates.
	 * 
	 * @param arr
	 * 	Input array to output to a file.
	 * @param file
	 * 	Input file name to write to.
	 * @throws IllegalStateException
	 * 	THrown if arr is null.
	 */
	private void writePointsToFile(Point[] arr, String file) throws IllegalStateException
	{
		if (arr == null) throw new IllegalStateException();
		
		try {
			PrintWriter w = new PrintWriter(file);
			for (Point p : arr) {
				w.println(p.getX() + " " + p.getY());
			}
				
			w.close();
			
		} catch (FileNotFoundException e) {
			System.err.println("Error creating file \"" + file + '\"');
			return;
		}
	}

	// ---------------
	// Private Methods
	// ---------------
	
	/**
	 * Find the point in the array points[] that has the smallest y-coordinate.  In case of a tie, 
	 * pick the point with the smallest x-coordinate. Set the variable lowestPoint to the found 
	 * point. 
	 * 
	 * Multiple elements from points[] could coincide at the lowestPoint (i.e., they are the same 
	 * point).  This situation could happen, though with a very small chance.  In this case, any of 
	 * them can be lowestPoint. 
	 * 
	 * Ought to be private, but is made public for testing convenience. 
	 */
	public void lowestPoint()
	{
		lowestPoint = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
		
		for (Point p : points) {
			if (p.getY() < lowestPoint.getY()) {
				lowestPoint = p;
			} else if (p.getY() == lowestPoint.getY() && p.getX() < lowestPoint.getX()) {
				lowestPoint = p;
			}
		}
	}
	
	/**
	 * Call quickSort() on points[].  After the sorting, duplicates in points[] will appear next 
	 * to each other, with those equal to lowestPoint at the beginning of the array.  
	 * 
	 * Copy the points from points[] into the array pointsNoDuplicate[], eliminating all duplicates. 
	 * Update numDistinctPoints. 
	 * 
	 * Copy the points from pointsNoDuplicate[] into the array pointsToScan[] and eliminate some 
	 * as follows.  If multiple points have the same polar angle, eliminate all but the one that is 
	 * the furthest from lowestPoint.  Update numPointsToScan. 
	 * 
	 * Ought to be private, but is made public for testing convenience. 
	 *
	 */
	public void setUpScan()
	{
		quickSort();
		ArrayList<Point> arr = new ArrayList<Point>(Arrays.asList(points));
		
		// remove all duplicates from the array
		Point prev = null;
		Iterator<Point> i = arr.iterator();
		while (i.hasNext()) {
			Point current = i.next();
			if (prev != null && prev.equals(current)) {
				i.remove();
			}
			
			prev = new Point(current.getX(), current.getY());
		}
		
		// store the array with no duplicates
		pointsNoDuplicate = new Point[arr.size()];
		arr.toArray(pointsNoDuplicate);
		numDistinctPoints = pointsNoDuplicate.length;
		
		// remove points that have the same polar angle that will not be in the convex hull
		PointComparator comp = new PointComparator(lowestPoint);
		arr = new ArrayList<Point>(Arrays.asList(points));
		for (int j=1; j<arr.size(); j++) {
			// get the current and previous point to use
			Point current = arr.get(j);
			prev = arr.get(j-1);
			
			// if they have the same polar angle, we need to remove one of them
			if (comp.comparePolarAngle(current, prev) == 0) {
				// find which is closer to lowestPoint, and remove it
				if (comp.compareDistance(current, prev) < 0)
					arr.remove(j);
				else
					arr.remove(j-1);
				
				// move the index back to account for removal
				j--;
			}
		}
		
		// store the array that we wish to scan
		pointsToScan = new Point[arr.size()];
		arr.toArray(pointsToScan);
		numPointsToScan = pointsToScan.length;
	}

	/**
	 * Sort the array points[] in the increasing order of polar angle with respect to lowestPoint.  
	 * Use quickSort.  Construct an object of the pointComparator class with lowestPoint as the 
	 * argument for point comparison.  
	 * 
	 * Ought to be private, but is made public for testing convenience.   
	 */
	public void quickSort()
	{
		// recurse through the quick sort, starting with the entire array
		quickSortRec(0, numPoints-1);
	}
	
	/**
	 * Operates on the subarray of points[] with indices between first and last. 
	 * 
	 * @param first  starting index of the subarray
	 * @param last   ending index of the subarray
	 */
	private void quickSortRec(int first, int last)
	{
		if (last <= first)
			return;
		
		int p = partition(first, last);
		quickSortRec(first, p);
		quickSortRec(p+1, last);
	}
	
	/**
	 * Operates on the subarray of points[] with indices between first and last.
	 * 
	 * @param first
	 * @param last
	 * @return
	 */
	private int partition(int first, int last)
	{
		swap(points, first + (int)(Math.random() * (last-first+1)), last);
		Point p = points[last];
		int i = first-1;
		
		PointComparator comp = new PointComparator(lowestPoint);
		
		for (int j=first; j<last; j++) {
			if (comp.compare(points[j], p) <= 0) {
				i++;
				swap(points, i, j);
			}
		}
		
		i++;
		swap(points, last, i);
		return i; 
	}	
	
	private <T> void swap(T[] arr, int i0, int i1)
	{
		T tmp = arr[i0];
		arr[i0] = arr[i1];
		arr[i1] = tmp;
	}
}
