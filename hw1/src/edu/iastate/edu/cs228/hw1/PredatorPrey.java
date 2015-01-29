package edu.iastate.edu.cs228.hw1;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 
 * The PredatorPrey class does the predator-prey simulation over a grid world 
 * with squares occupied by badgers, foxes, rabbits, grass, or none. 
 *
 * @author Ian Malerich
 */
public class PredatorPrey 
{
	/**
	 * True when the user wishes to exit, otherwise false.
	 */
	private static boolean gameover = false;
	
	/**
	 * Update the new world from the old world in one cycle.
	 * @param wOld
	 *  The old world to be updated.
	 * @param wNew
	 *  Where the new world is stored.
	 */
	public static void updateWorld(World wOld, World wNew)
	{
		// update each object in the grid
		for (int r=0; r<wOld.grid.length; r++) {
			for (int c=0; c<wOld.grid[0].length; c++) {
				wOld.grid[r][c].next(wNew);
			}
		}
	}
	
	/**
	 * Repeatedly generates worlds either randomly or from reading files. 
	 * Over each world, carries out an input number of cycles of evolution. 
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException
	{	
		System.out.println("The Predator-Prey Simulator");
		System.out.println("keys: 1 (random world) 2 (file input) 3 (exit)");
	
		int trial = 0;
		while (!gameover) { 
			System.out.println();
			runTrial(++trial);
		}
	}
	
	/**
	 * Run a single trial of a simulation.
	 * @param trial
	 *  The trial number.
	 * @throws FileNotFoundException
	 */
	private static void runTrial(int trial) throws FileNotFoundException
	{
		int cycles = 0;
		World current = generateWorld(trial);
		World next = new World(current.getWidth());
		cycles = promptNumCycles();

		System.out.println("\nInitial World:\n");
		System.out.println(current.toString());
		
		for (int i=0; i<cycles; i++) {
			updateWorld(current, next);
			
			World tmp = current;
			current = next;
			next = tmp;
		}
		
		System.out.println("\nFinal World:\n");
		System.out.println(current.toString());
	}
	
	/**
	 * Generate the world to be used for a trial from user input.
	 * @param trial
	 *  The trial number to be used.
	 * @return
	 *  The generated world.
	 */
	private static World generateWorld(int trial) throws FileNotFoundException
	{
		// load the world from the selected option
		switch ( prompt(trial) ) {
		case 1:
			System.out.println("Random World");
			int width = promptWidth();
			if (width <= 0) {
				System.err.println("Invalid dimmensions offered for grid width.");
				System.err.println("Error...");
				System.exit(0);
				return null;
			}

			World w = new World(width);
			w.randomInit();
			return w;

		case 2:
			System.out.println("World input from file");
			return promptFile();

		default:
			System.exit(0);
			return null;
		}
	}
	
	/**
	 * Prompt the user for a selection on how to generate the world.
	 * @param trial
	 *  The trial number to prompt for.
	 * @return
	 *  The users selection.
	 */
	private static int prompt(int trial)
	{
		System.out.print("Trial " + trial + ": ");
		Scanner s = new Scanner(System.in);
		int ret = 0;
		
		if (s.hasNextInt()) {
			ret = s.nextInt();
		} 
		
		// only values of 1-3 are allowed
		if (ret < 1 || ret > 3) {
			System.err.println("Error - Invalid option.");
			System.err.println("Exiting...");
			System.exit(-1);
		}
		
		return ret;
	}
	
	/**
	 * Prompt the user for the width of the world to use.
	 * @return
	 * 	The width input by the user.
	 */
	private static int promptWidth()
	{
		System.out.print("Enter grid width: ");
		
		Scanner s = new Scanner(System.in);
		if (s.hasNextInt()) {
			return s.nextInt();
		}
		
		System.err.println("Invalid option entered.");
		System.err.println("Exiting...");
		System.exit(-1);
		return -1;
	}
	
	/**
	 * Prompts the user to enter a filename to load a world from.
	 * @return
	 * 	The loaded world.
	 * @throws FileNotFoundException
	 */
	private static World promptFile() throws FileNotFoundException
	{
		System.out.print("File name: ");
		
		Scanner s = new Scanner(System.in); 
		String in = s.next();
		
		return new World(in);
	}
	
	/**
	 * Prompt the user for the number of cycles to run in a simulation
	 * @return
	 * 	The number of cycles as specified by the user.
	 */
	private static int promptNumCycles()
	{
		System.out.print("Enter the number of cycles: ");
		
		Scanner s = new Scanner(System.in);
		if (s.hasNextInt()) {
			int i = s.nextInt();
			if (i > 0)
				return i;
		}
		
		System.err.println("Invalid option entered.");
		System.err.println("Exiting...");
		System.exit(-1);
		return -1;
	}
}
