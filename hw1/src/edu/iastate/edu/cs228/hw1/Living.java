package edu.iastate.edu.cs228.hw1;

import java.util.Arrays;


/**
 * 
 * Living refers to the life form occupying a square in a world grid. It is a 
 * superclass of Badger, Empty, Fox, Grass, and Rabbit, and has two abstract 
 * methods awaiting implementation. 
 *
 * @author Ian Malerich
 */
public abstract class Living 
{
	protected World world; // the world in which the life form resides
	protected int row;     // location of the square on which 
	protected int column;  // the life form resides
	
	// constants to be used as indices. 
	protected static final int BADGER = 0; 
	protected static final int EMPTY = 1; 
	protected static final int FOX = 2; 
	protected static final int GRASS = 3; 
	protected static final int RABBIT = 4; 
	
	public static final int NUM_LIFE_FORMS = 5; 
	
	// life expectancies 
	public static final int BADGER_MAX_AGE = 4; 
	public static final int FOX_MAX_AGE = 5; 
	public static final int RABBIT_MAX_AGE = 3; 
	
	/**
	 * Template constructor for a Living entity;
	 * @param w
	 * 	The world this entity belongs to.
	 * @param r
	 *  The row position for this entity.
	 * @param c
	 *  The column position for this entity.
	 */
	protected Living(World w, int r, int c)
	{
		world = w;
		row = r;
		column = c;
		
		w.grid[r][c] = this;
	}
	
	/**
	 * Censuses all life forms in the 3 X 3 neighborhood in a world. 
	 * @param population  counts of all life forms
	 */
	protected void census(int population[])
	{		
		// Count the numbers of Badgers, Empties, Foxes, Grasses, and Rabbits  
		// in the 3 by 3 neighborhood centered at this Living object.  Store the 
		// counts in the array population[] at indices 0, 1, 2, 3, 4, respectively. 
		Arrays.fill(population, 0);
		
		for (int c=Math.max(column-1, 0); c<=Math.min(column+1, world.getWidth()-1); c++)
		{
			for (int r=Math.max(row-1, 0); r<=Math.min(row+1, world.getWidth()-1); r++)
			{
				population[ world.grid[r][c].who().ordinal() ]++;
			}
		}
	}
	

	/**
	 * Gets the identity of the life form on the square.
	 * @return State
	 */
	public abstract State who();
	// To be implemented in each class of Badger, Empty, Fox, Grass, and Rabbit. 
	// 
	// There are five states given in State.java. Include the prefix State in   
	// the return value, e.g., return State.Fox instead of Fox.  
	
	
	/**
	 * Determines the life form on the square in the next cycle.
	 * @param  wNew  world of the next cycle
	 * @return Living 
	 */
	public abstract Living next(World wNew); 
}

