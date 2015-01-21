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
public class PredatorPrey {
	/**
	 * Update the new world from the old world in one cycle. 
	 * @param wOld  old world
	 * @param wNew  new world 
	 */
	public static void updateWorld(World wOld, World wNew)
	{
		// For every life form (i.e., a Living object) in the grid wOld, generate  
		// a Living object in the grid wNew at the corresponding location such that 
		// the former life form changes into the latter life form. 
		// 
		// Employ the method next() of the Living class. 
		
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
		while (runTrial(++trial)) { }
	}
	
	/**
	 * Run everything needed to do one simulation.
	 */
	private static boolean runTrial(int Trial) throws FileNotFoundException
	{
		System.out.println();
		
		int cycles = 0;
		World current = generateWorld(Trial);
		if (current == null) {
			return false;
		}
		
		World next = new World(current.getWidth());
		cycles = promptNumCycles();
		if (cycles == -1) {
			return false;
		}

		System.out.println("\nInitial World:\n");
		System.out.println(current.toString());
		
		// update every cycle
		for (int i=0; i<cycles; i++) {
			// will overwrite next
			updateWorld(current, next);
			
			// next now holds the current state
			current = next;
		}
		
		System.out.println("\nFinal World:\n");
		System.out.println(current.toString());
		
		return true;
	}
	
	/**
	 * Generate the world to be used for a trial from user input.
	 * @param Trial
	 *  The trial number to be used.
	 * @return
	 *  The generated world.
	 */
	private static World generateWorld(int Trial) throws FileNotFoundException
	{
		// load the world from the selected option
		switch ( prompt(Trial) ) {
		case 1:
			System.out.println("Random World");
			int width = promptWidth();
			if (width == -1) {
				// invalid option received, exit
				return null;
			}

			World w = new World(width);
			w.randomInit();
			return w;

		case 2:
			System.out.println("World input from file");
			return promptFile();

		default:
			return null;
		}
	}
	
	/**
	 * Prompt the user for a selection on how to generate the world.
	 */
	private static int prompt(int Trial)
	{
		System.out.print("Trial " + Trial + ": ");
		Scanner s = new Scanner(System.in);
		int ret = 0;
		
		if (s.hasNextInt()) {
			ret = s.nextInt();
		} 
		
		// only values of 1-3 are allowed
		if (ret < 1 || ret > 3) {
			System.err.println("Error - Invalid option.");
			System.err.println("Exiting...");
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
			return s.nextInt();
		}
		
		System.err.println("Invalid option entered.");
		System.err.println("Exiting...");
		return -1;
	}
}
