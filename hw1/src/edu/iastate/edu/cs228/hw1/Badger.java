package edu.iastate.edu.cs228.hw1;

/**
 * A badger eats rabbits and competes against a fox. 
 * 
 * @author Ian Malerich
 */
public class Badger extends Living 
{
	private int age; 
	
	/**
	 * Constructor 
	 * @param w: world
	 * @param r: row position 
	 * @param c: column position
	 * @param a: age 
	 */
	public Badger(World w, int r, int c, int a) 
	{
		super(w, r, c);
		age = a;
	}
	
	/**
	 * A badger occupies the square. 	 
	 */
	public State who()
	{
		return State.BADGER; 
	}
	
	@Override
	public String toString()
	{
		return "B";
	}
	
	/**
	 * A badger dies of old age or hunger, or from an attack by a group of foxes when alone. 
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
		
		if (age == 4) {
			// if the age of the badger is equal to 4
			l = new Empty(wNew, row, column);
			
		} else if (pop[ State.BADGER.ordinal() ] == 1 && pop[ State.FOX.ordinal() ] > 1) {
			// if there is only one badger and more than one fox
			l = new Fox(wNew, row, column, 0);
			
		} else if (pop[ State.BADGER.ordinal() ] + pop[ State.FOX.ordinal() ] > pop[ State.RABBIT.ordinal() ]) {
			// badgers and foxes together out number foxes (die of starvation)
			l = new Empty(wNew, row, column);
			
		} else {
			// the badger lives on
			l = new Badger(wNew, row, column, age);
			
		}
		
		return l; 
	}
}
