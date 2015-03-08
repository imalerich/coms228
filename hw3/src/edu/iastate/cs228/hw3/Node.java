package edu.iastate.cs228.hw3;

/**
 * Represents a single node to be used in the doubly linked list.
 * 
 * @author Ian Malerich
 *
 */
public class Node 
{
	/* Descriptive data for this node. */
    public String fruit;   		// name of the fruit (singular form) 
    public int quantity; 		// quantity of the fruit in the bin
    public int bin; 			// number of storage bin 
    
    /* Forward and backward list references. */
    public Node nextN;			// a reference to the next node in the list
    public Node previousN;
    public Node nextB;			// a reference to the previous node in the list
    public Node previousB; 
    
    /**
     * @param fruit			
     * 	name of this type of fruit 
     * @param quantity		
     * 	e.g., number of units (i.e., # apples, # bunches of grapes, etc.)
     * @param bin			
     * 	number of the storage bin for the fruit
     * @param nextN         
     * 	next node in the N-list sorted by name 
     * @param previousN 	
     * 	previous node in the N-list 
     * @param nextB			
     * 	next node in the B-list sorted by storage bin number 
     * @param previousB		
     * 	previous node in the B list 
     */
    public Node(String fruit, int quantity, int bin, 
    		Node nextN, Node previousN, Node nextB, Node previousB)
    {
    	// data for this node
    	this.fruit = fruit;
    	this.quantity = quantity; 
    	this.bin = bin;

    	// node references
    	this.nextN = nextN;
    	this.previousN = previousN; 
    	this.nextB = nextB; 
    	this.previousB = previousB; 
    }
    
    /**
     * Write out the fruit name, quantity, and bin number stored at the node.
     */
    public String toString() 
    {
    	String ret;
    	
    	ret  = fruit	+ (new String( new char[15 - fruit.length()] )).replace('\0', ' ')
    					+ quantity	+ (new String( new char[15 - (int)Math.log10(quantity) + 1] )).replace('\0', ' ')
    					+ bin		+ (new String( new char[4  - (int)Math.log10(bin) + 1] )).replace('\0', ' ');
    	
    	return ret;
    }
}
