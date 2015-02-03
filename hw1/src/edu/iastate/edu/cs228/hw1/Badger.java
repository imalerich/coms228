package edu.iastate.edu.cs228.hw1;

/**
 * A badger eats rabbits and competes against a fox. 
 * 
 * @author Ian Malerich
 */
public class Badger extends Living 
{
	/**
	 * The age of the badger.
	 */
	private int age; 
	
	/**
	 * Create a new badger object in the specified world.
	 * @param w
	 *  The world in which to create this object.
	 * @param r
	 *  The row in which this object lies.
	 * @param c
	 *  The column in which this object lies.
	 * @param a
	 *  The age of this object at init.
	 */
	public Badger(World w, int r, int c, int a) 
	{
		super(w, r, c);
		age = a;
	}
	
	@Override
	public State who()
	{
		return State.BADGER; 
	}
	
	@Override
	public String toString()
	{
		return "B";
	}
	
	@Override
	public Living next(World wNew)
	{
		// See Living.java for an outline of the function. 
		// See the project description for the survival rules for a fox. 
		Living l;
		
		// add age and get the population to be used for this step of the simulation
		int pop[] = new int[Living.NUM_LIFE_FORMS];
		census(pop);
		
		if (age == 4) {
			// if the age of the badger is equal to 4
			l = new Empty(wNew, row, column);
			
		} else if (pop[BADGER] == 1 && pop[FOX] > 1) {
			// if there is only one badger and more than one fox
			l = new Fox(wNew, row, column, 0);
			
		} else if (pop[BADGER] + pop[FOX] > pop[RABBIT]) {
			// badgers and foxes together out number rabbits (die of starvation)
			l = new Empty(wNew, row, column);
			
		} else {
			// the badger lives on
			l = new Badger(wNew, row, column, age+1);
			
		}
		
		return l; 
	}
}
