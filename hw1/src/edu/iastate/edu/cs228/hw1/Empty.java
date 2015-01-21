package edu.iastate.edu.cs228.hw1;


/** 
 * Empty squares are competed by various forms of life.
 * 
 * @author Ian Malerich
 */
public class Empty extends Living 
{
	public Empty(World w, int r, int c) 
	{
		super(w, r, c);
	}
	
	@Override
	public String toString()
	{
		return "E";
	}
	
	public State who()
	{
		return State.EMPTY; 
	}
	
	/**
	 * An empty square will be occupied by Badger, Fox, Rabbit, or Grass, 
	 * or remain empty. 
	 * @param wNew  world of the next life cycle.
	 * @return Living  life form in the next cycle.   
	 */
	public Living next(World wNew)
	{
		// See Living.java for an outline of the function. 
		// See the project description for corresponding survival rules. 
		Living l;
		
		// add age and get the population to be used for this step of the simulation
		int pop[] = new int[5];
		census(pop);
		
		if (pop[ State.RABBIT.ordinal() ] > 1) {
			// more than one neighboring rabbit
			l = new Rabbit(wNew, row, column, 0);
			
		} else if (pop[ State.FOX.ordinal() ] > 1) {
			// more than one neighboring fox
			l = new Fox(wNew, row, column, 0);
			
		} else if (pop[ State.BADGER.ordinal() ] > 1) {
			// more than one neighboring badger
			l = new Badger(wNew, row, column, 0);
			
		} else if (pop[ State.GRASS.ordinal() ] >= 1) {
			// at least one neighboring grass
			l = new Grass(wNew, row, column);
			
		} else {
			// still empty
			l = new Empty(wNew, row, column);
		}
		
		return l; 
	}
}
