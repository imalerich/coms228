package edu.iastate.edu.cs228.hw1;

/** 
 * Empty squares are competed by various forms of life.
 * 
 * @author Ian Malerich
 */
public class Empty extends Living 
{
	/**
	 * Create an empty space in the specified world.
	 * @param w
	 *  The world in which to create this object.
	 * @param r
	 *  The row in which this object lies.
	 * @param c
	 *  The column in which this object lies.
	 */
	public Empty(World w, int r, int c) 
	{
		super(w, r, c);
	}
	
	@Override
	public String toString()
	{
		return "E";
	}
	
	@Override
	public State who()
	{
		return State.EMPTY; 
	}
	
	@Override
	public Living next(World wNew)
	{
		// See Living.java for an outline of the function. 
		// See the project description for corresponding survival rules. 
		Living l;
		
		// add age and get the population to be used for this step of the simulation
		int pop[] = new int[Living.NUM_LIFE_FORMS];
		census(pop);
		
		if (pop[RABBIT] > 1) {
			// more than one neighboring rabbit
			l = new Rabbit(wNew, row, column, 0);
			
		} else if (pop[FOX] > 1) {
			// more than one neighboring fox
			l = new Fox(wNew, row, column, 0);
			
		} else if (pop[BADGER] > 1) {
			// more than one neighboring badger
			l = new Badger(wNew, row, column, 0);
			
		} else if (pop[GRASS] > 0) {
			// at least one neighboring grass
			l = new Grass(wNew, row, column);
			
		} else {
			// still empty
			l = new Empty(wNew, row, column);
		}
		
		return l; 
	}
}
