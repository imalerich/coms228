package edu.iastate.edu.cs228.hw1;

/**
 * Grass remains if more than rabbits in the neighborhood;
 * otherwise, it is eaten. 
 *
 * @author Ian Malerich
 */
public class Grass extends Living 
{
	/**
	 * Create a grass object.
	 * @param w
	 *  The world to create the grass object in.
	 * @param r
	 *  The row for this object.
	 * @param c
	 *  The column for this object.
	 */
	public Grass(World w, int r, int c) 
	{
		super(w, r, c);
	}
	
	@Override
	public State who()
	{
		return State.GRASS; 
	}
	
	@Override
	public String toString()
	{
		return "G";
	}
	
	@Override
	public Living next(World wNew)
	{
		// See Living.java for an outline of the function. 
		// See the project description for the survival rules for grass. 
		Living l;
		
		// add age and get the population to be used for this step of the simulation
		int pop[] = new int[Living.NUM_LIFE_FORMS];
		census(pop);
		
		if (pop[RABBIT] >= pop[GRASS]*2) {
			// there are at least twice as many rabbits as grass
			l = new Empty(wNew, row, column);
			
		} else if (pop[RABBIT] > pop[GRASS]) {
			// there are more rabbits than grass
			l = new Rabbit(wNew, row, column, 0);
			
		} else {
			// remain as grass
			l = new Grass(wNew, row, column);
			
		}
		
		return l; 
	}
}
