package edu.iastate.edu.cs228.hw1;

/***
 * A rabbit eats grass and lives no more than three years.
 * 
 * @author Ian Malerich
 */
public class Rabbit extends Living 
{
	/**
	 * The age of the rabbit.
	 */
	private int age; 
	
	/**
	 * Create a new rabbit object in the specified student.
	 * @param w
	 *  The world to add this object into.
	 * @param r
	 *  The row where this object lies.
	 * @param c
	 *  The column where this object lies.
	 * @param a
	 *  The age of this object at time of init.
	 */
	public Rabbit(World w, int r, int c, int a) 
	{
		super(w, r, c);
		age = a;
	}
	
	@Override
	public State who()
	{
		return State.RABBIT; 
	}
	
	@Override
	public String toString()
	{
		return "R";
	}
	
	@Override
	public Living next(World wNew)
	{
		// See Living.java for an outline of the function. 
		// See the project description for the survival rules for a rabbit. 
		Living l;
		
		// add age and get the population to be used for this step of the simulation
		age++;
		int pop[] = new int[Living.NUM_LIFE_FORMS];
		census(pop);
		
		if (age == 3) {
			// the rabbit dies of old age
			l = new Empty(wNew, row, column);
			
		} else if (pop[ State.GRASS.ordinal() ] == 0) {
			// there is no grass, die of hunger
			l = new Empty(wNew, row, column);
			
		} else if (pop[ State.BADGER.ordinal() ] + pop[ State.FOX.ordinal() ] >= pop[ State.RABBIT.ordinal() ]) {
			// there are at least as many badgers and foxes as there are rabbits
			l = new Empty(wNew, row, column);
			
		} else {
			l = new Rabbit(wNew, row, column, age);
		}
		
		return l; 
	}
}
