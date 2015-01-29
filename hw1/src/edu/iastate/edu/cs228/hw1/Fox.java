package edu.iastate.edu.cs228.hw1;

/**
 * A fox eats rabbits and competes against a badger. 
 * 
 * @author Ian Malerich
 */
public class Fox extends Living 
{
	/**
	 * The age of the fox.
	 */
	private int age; 
	
	/**
	 * Create a new fox object in the specified world.
	 * @param w
	 *  The world in which to create the object.
	 * @param r
	 *  The row in which this object lies.
	 * @param c
	 *  The column in which this object lies.
	 * @param a
	 *  The age of this object at time of init.
	 */
	public Fox (World w, int r, int c, int a) 
	{
		super(w, r, c);
		age = a;
	}
	
	@Override
	public String toString()
	{
		return "F";
	}
	
	@Override
	public State who()
	{
		return State.FOX; 
	}
	
	@Override
	public Living next(World wNew)
	{
		// See Living.java for an outline of the function. 
		// See the project description for the survival rules for a fox. 
		Living l;
		
		// add age and get the population to be used for this step of the simulation
		age++;
		int pop[] = new int[Living.NUM_LIFE_FORMS];
		census(pop);
		
		if (age == 5) {
			// the fox dies of old age
			l = new Empty(wNew, row, column);
			
		} else if (pop[ State.BADGER.ordinal() ] >= pop[ State.FOX.ordinal() ]) {
			// there are at least as many badgers as foxes, therefore, add another badger
			l = new Badger(wNew, row, column, 0);
			
		} else if (pop[ State.BADGER.ordinal() ] + pop[ State.FOX.ordinal() ] > pop[ State.RABBIT.ordinal() ]) {
			// badgers and foxes together out number foxes (die of starvation)
			l = new Empty(wNew, row, column);
			
		} else {
			// live on
			l = new Fox(wNew, row, column, age);
			
		}
		
		return l; 
	}
}
