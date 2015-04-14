package edu.iastate.cs228.hw4;

/**
 *  
 * @author Ian Malerich
 *
 */

import java.util.Comparator;

/**
 * 
 * This class compares two points p1 and p2 by polar angle with respect to a reference point.  
 * It is known that the reference point is not above either p1 or p2, and in the case that
 * either or both of p1 and p2 have the same y-coordinate, not to their right. 
 *
 */
public class PointComparator implements Comparator<Point>
{
	private Point referencePoint; 
	
	/**
	 * 
	 * @param p reference point
	 */
	public PointComparator(Point p)
	{
		referencePoint = p; 
	}
	
	/**
	 * Use cross product and dot product to implement this method.  Do not take square roots 
	 * or use trigonometric functions. See the PowerPoint notes on how to carry out cross and 
	 * dot products. 
	 * 
	 * Call comparePolarAngle() and compareDistance(). 
	 * 
	 * @param p1
	 * @param p2
	 * @return -1 if one of the following three conditions holds: 
	 *                a) p1 and referencePoint are the same point but p2 is a different point; 
	 *                b) neither p1 nor p2 equals referencePoint, and the polar angle of 
	 *                   p1 with respect to referencePoint is less than that of p2; 
	 *                c) neither p1 nor p2 equals referencePoint, p1 and p2 have the same polar 
	 *                   angle w.r.t. referencePoint, and p1 is closer to referencePoint than p2. 
	 *         0  if p1 and p2 are the same point  
	 *         1  if one of the following three conditions holds:
	 *                a) p2 and referencePoint are the same point but p1 is a different point; 
	 *                b) neither p1 nor p2 equals referencePoint, and the polar angle of
	 *                   p1 with respect to referencePoint is greater than that of p2;
	 *                c) neither p1 nor p2 equals referencePoint, p1 and p2 have the same polar
	 *                   angle w.r.t. referencePoint, and p1 is further to referencePoint than p2. 
	 *                   
	 */
	public int compare(Point p1, Point p2)
	{
		int angle = comparePolarAngle(p1, p2);
		int dist = compareDistance(p1, p2);
		
		// if the angles are different, return the smaller angle
		if (angle != 0) return angle;
		// else return the smaller distance
		else return dist;
	}
	
	
	/**
	 * Compare the polar angles of two points p1 and p2 with respect to referencePoint.  Use 
	 * cross products.  Do not use trigonometric functions. 
	 * 
	 * Precondition:  p1 and p2 are distinct points. 
	 * 
	 * @param p1
	 * @param p2
	 * @return   -1  if p1 equals referencePoint or its polar angle with respect to referencePoint
	 *               is less than that of p2. 
	 *            0  if p1 and p2 have the same polar angle. 
	 *            1  if p2 equals referencePoint or its polar angle with respect to referencePoint
	 *               is less than that of p1. 
	 */
    public int comparePolarAngle(Point p1, Point p2) 
    {
    	if (p1.equals(referencePoint) && p2.equals(referencePoint)) return 0;
    	if (p1.equals(referencePoint)) return -1;
    	if (p2.equals(referencePoint)) return 1;
    	
    	Point v1 = new Point(p1.getX() - referencePoint.getX(), p1.getY() - referencePoint.getY());
    	Point v2 = new Point(p2.getX() - referencePoint.getX(), p2.getY() - referencePoint.getY());
    	int cross = v1.getX() * v2.getY() - v2.getX() * v1.getY();
    	
    	if (cross == 0) return 0;
    	return cross > 0 ? -1 : 1; 
    }
    
    
    /**
     * Compare the distances of two points p1 and p2 to referencePoint.  Use dot products. 
     * Do not take square roots. 
     * 
     * @param p1
     * @param p2
     * @return   -1   if p1 is closer to referencePoint 
     *            0   if p1 and p2 are equidistant to referencePoint
     *            1   if p2 is closer to referencePoint
     */
    public int compareDistance(Point p1, Point p2)
    {
    	Point v1 = new Point(p1.getX() - referencePoint.getX(), p1.getY() - referencePoint.getY());
    	Point v2 = new Point(p2.getX() - referencePoint.getX(), p2.getY() - referencePoint.getY());
    	
    	int dot1 = v1.getX() * v1.getX() + v1.getY() * v1.getY();
    	int dot2 = v2.getX() * v2.getX() + v2.getY() * v2.getY();
    	
    	if (dot1 == dot2) return 0;
    	return dot1 < dot2 ? -1 : 1;
    }
}
