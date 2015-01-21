package edu.iastate.edu.cs228.hw1;


/**
 * A fox eats rabbits and competes against a badger. 
 * 
 * @author Ian Malerich
 */
public class Fox extends Living 
{
	private int age; 
	
	/**
	 * Constructor 
	 * @param w: world
	 * @param r: row position 
	 * @param c: column position
	 * @param a: age 
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
	
	/**
	 * A fox occupies the square. 	 
	 */
	public State who()
	{
		return State.FOX; 
	}
	
	/**
	 * A fox dies of old age or hunger or attack by one or more badgers. 
	 * @param wNew  world of the next cycle
	 * @return Living  life form occupying the square in the next cycle. 
	 */
	public Living next(World wNew)
	{
		// See Living.java for an outline of the function. 
		// See the project description for the survival rules for a fox. 
		Living l;
		
		// add age and get the population to be used for this step of the simulation
		age++;
		int pop[] = new int[5];
		census(pop);
		
		if (age == 5) {
			// the fox dies of old age
			l = new Empty(wNew, row, column);
			
		} else if (pop[ State.BADGER.ordinal() ] >= pop[ State.FOX.ordinal() ]) {
			// there are at least as many badgers as foxes, therefore, dd another badger
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
