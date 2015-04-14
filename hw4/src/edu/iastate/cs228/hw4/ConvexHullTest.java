package edu.iastate.cs228.hw4;

public class ConvexHullTest 
{
	public static void main(String[] args) {
		ConvexHull hull = new ConvexHull(100);
		hull.GrahamScan();
		
		hull.pointsScannedToFile();
		hull.pointsToFile();
		hull.hullToFile();
		
		System.out.println(hull.toString());
	}
}
