package edu.iastate.edu.cs228.hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

/**
 * 
 * The world is represented as a square grid of size width X width. 
 *
 * @author Ian Malerich
 */
public class World 
{
	/**
	 * The grid that represents the simulated world.
	 */
	public Living[][] grid; 
	
	/**
	 * Create a new world using the specified file as input.
	 * @param inputFileName
	 *  The input file to be used to create this world.
	 * @throws FileNotFoundException
	 */
	public World(String inputFileName) throws FileNotFoundException
	{
		File f = new File(inputFileName);
		Scanner s = new Scanner(f);
		
		int w = s.nextInt();
		s.nextLine();
		grid = new Living[w][w];
		
		int row = 0;
		while (s.hasNextLine()) {
			procRow(s.nextLine(), row);
			row++;
		}
		
		s.close();
	}
	
	/**
	 * Read a row from a file.
	 * @param data
	 *  The data to be parsed.
	 * @param R
	 *  The row this will be added at.
	 */
	private void procRow(String data, int r)
	{
		Scanner s = new Scanner(data);
		
		int c = 0;
		while (s.hasNext()) {
			String n = s.next();
			
			if (n.equals("B")) {
				new Badger(this, r, c, 0);
				
			} else if (n.equals("E")) {
				new Empty(this, r, c);
				
			} else if (n.equals("F")) {
				new Fox(this, r, c, 0);
				
			} else if (n.equals("G")) {
				new Grass(this, r, c);
				
			} else if (n.equals("R")) {
				new Rabbit(this, r, c, 0);
				
			}
			
			c++;
		}
			
		s.close();
	}
	
	/**
	 * Constructor that builds a w X w grid without initializing it. 
	 * @param width  
	 *  The width to be used for the newly created world.
	 */
	public World(int w)
	{
		grid = new Living[w][w];
	}
	
	/**
	 * Get the width of the grid.
	 * @return
	 *  The width of the grid.
	 */
	public int getWidth()
	{
		return grid.length; 
	}
	
	/**
	 * Initialize the world by randomly assigning to every square of the grid  
	 * one of BADGER, FOX, RABBIT, GRASS, or EMPTY.
	 */
	public void randomInit()
	{
		Random rand = new Random(); 
 
		for (int r=0; r<grid.length; r++) {
			for (int c=0; c<grid[0].length; c++) {
				
				// the constructors will add each entity to the grid
				initLiving(rand.nextInt(Living.NUM_LIFE_FORMS), r, c);
			}
		}
	}
	
	/**
	 * Creates an instance of living based on a key.
	 * @param Key
	 *  0 - Badger, 1 - Fox, 2 - Rabbit, 3 - Grass, Default - Empty
	 * @return
	 *  The created living object.
	 */
	private Living initLiving(int Key, int R, int C)
	{
		switch (Key) {
		case Living.BADGER:
			return new Badger(this, R, C, 0);
			
		case Living.FOX:
			return new Fox(this, R, C, 0);
			
		case Living.RABBIT:
			return new Rabbit(this, R, C, 0);
			
		case Living.GRASS:
			return new Grass(this, R, C);
			
		default:
			return new Empty(this, R, C);
		}
	}
	
	/**
	 * Write the world grid as a string according to the output format.
	 */
	@Override
	public String toString()
	{
		String output = "";
		for (int r=0; r<grid.length; r++) {
			for (int c=0; c<grid[0].length; c++) {
				output += grid[r][c].toString();
				
				if (c != grid[0].length-1) {
					output += ' ';
				}
			}
			
			if (r != grid.length-1) {
				output += '\n';
			}
		}
		
		return output; 
	}
	
	/**
	 * Write the world grid to an output file.  Useful for a randomly generated world. 
	 * @throws FileNotFoundException
	 */
	public void write(String outputFileName) throws FileNotFoundException
	{
		File f = new File(outputFileName);
		PrintWriter p = new PrintWriter(f);
		p.print(toString());
		p.close();
	}			
}
