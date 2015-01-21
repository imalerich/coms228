package edu.iastate.edu.cs228.hw1;


/***
 * A rabbit eats grass and lives no more than three years.
 * 
 * @author Ian Malerich
 */
public class Rabbit extends Living 
{
	private int age; 
	
	/**
	 * Creates a Rabbit object.
	 * @param w: world  
	 * @param r: row position 
	 * @param c: column position
	 * @param a: age 
	 */
	public Rabbit(World w, int r, int c, int a) 
	{
		super(w, r, c);
		age = a;
	}
	
	// Rabbit occupies the square.
	public State who()
	{
		return State.RABBIT; 
	}
	
	@Override
	public String toString()
	{
		return "R";
	}
	
	/**
	 * A rabbit dies of old age or hunger, or it is eaten by a badger or a fox. 
	 * @param wNew  world of the next cycle 
	 * @return Living  new life form occupying the same square
	 */
	public Living next(World wNew)
	{
		// See Living.java for an outline of the function. 
		// See the project description for the survival rules for a rabbit. 
		Living l;
		
		// add age and get the population to be used for this step of the simulation
		age++;
		int pop[] = new int[5];
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
