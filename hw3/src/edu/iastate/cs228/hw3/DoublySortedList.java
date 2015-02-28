package edu.iastate.cs228.hw3;

import java.io.FileNotFoundException; 
import java.util.Scanner;
import java.util.Comparator;

/**
 * IMPORTANT: In the case of any minor discrepancy between the comments before a method
 * and its description in the file proj3.pdf, use the version from the file. 
 *
 */


/**
 * TODO - add class description
 * 
 * @author Ian Malerich
 *
 */
public class DoublySortedList 
{
	 private int size;     			// number of different kinds of fruits
	 private Node headN; 			// first node of the sorted linked list by fruit name
	 private Node headB; 			// first node of the sorted linked list by bin number
 
	 /**
	  *  default constructor 
	  */
	 public DoublySortedList()
	 {
		 size = 0; 
		 headN = null; 
		 headB = null; 
	 }
	 
	 /**
	  * Constructor over an inventory file consists of lines in the following format  
	  * 
	  * <fruit>  <quantity>  <bin> 
	  * 
	  * Throws an exception if the file is not found. 
	  * 
	  * You are asked to carry out the following operations: 
	  * 
	  *     1. Scan line by line to construct one Node object for each fruit.  
	  *     2. Create the two doubly-linked lists, by name and by bin number, respectively,
	  *        on the fly as the scan proceeds. 
	  *     3. Perform insertion sort on the two lists. Use the provided BinComparator and 
	  *        NameComparator classes to generate comparator objects for the sort.
	  *        
	  * @inventoryFile    name of the file 
	  */
	 public DoublySortedList(String inventoryFile) throws FileNotFoundException
	 {
		 // TODO 
	 }
	 
	 public int size()
	 {
		 return size; 
	 }

	 /**
	  * Called by split() and also used for testing.  The doubly sorted list has already been created. 
	  * 
	  * @param size
	  * @param headN
	  * @param headB
	  */
	 public DoublySortedList(int size, Node headN, Node headB)
	 {
		 this.size = size; 
		 this.headN = headN; 
		 this.headB = headB; 
	 }

	 /**
	  * Add one type of fruits in given quantity (n). 
	  * 
	  *     1. Search for the fruit. 
	  *     2. If already stored in some node, simply increase the quantity by n
	  *     3. Otherwise, create a new node to store the fruit at the first available bin.
	  *        add it to both linked lists by calling the helper methods insertN() and insertB(). 
	  *     4. Modify the link headN and/or headB if the newly inserted node becomes the 
	  *        first of either DCL. (This is carried out by insertN() or insertB().)
	  *        
	  * The case n == 0 should result in no operation.  The case n < 0 results in an 
	  * exception thrown. 
	  * 
	  * @param fruit  name of the fruit to be added 
	  * @param n	  quantity of the fruit
	  */
	 public void add(String fruit, int n) throws IllegalArgumentException
	 {
		 // TODO 
	 }
	 
	 /**
	  * The fruit list is not sorted.  For efficiency, you need to sort by name using quickSort. 
	  * Maintain two arrays fruitName[] and fruitQuant[].  
	  * 
	  * After sorting, add the fruits to the doubly-sorted list (see project description) using 
	  * the linear time algorithm described in Section 3.2 of the notes. 
	  * 
	  * @param fruitFile  list of fruits with quantities. one type of fruit followed by its quantity per line.
	  */
	 public void restock(String fruitFile) throws FileNotFoundException
	 {
		 // TODO
	 }

	 /**
	  * Remove a fruit from the inventory. 
	  *  
	  *     1. Search for the fruit on the N-list.  
	  *     2. If no existence, make no change. 
	  *     3. Otherwise, call the private method remove() on the node that stores 
	  *        the fruit to remove it. 
	  * 
	  * @param fruit
	  */
	 public void remove(String fruit)
	 {
		 // TODO
	 }
	 
	 /**
	  * Remove all fruits stored in the bin.  Essentially, remove the node.  The steps are 
	  * as follows: 
	  *     1. Search for the node with the bin in the B-list.
	  *     2. No change if it is not found.
	  *     3. Otherwise, call remove() on the found node. 
	  * 
	  * @param bin   >= 1 (otherwise, throw an exception)
	  */
	 public void remove(int bin) throws IllegalArgumentException
	 {
		 // TODO 
	 }
	 
	 /**
	  * Sell n units of a fruit. 
	  * 
	  * Search the N-list for the fruit.  Return in the case no fruit is found.  Otherwise, 
	  * a Node node is located.  Perform the following: 
	  * 
	  *     1. if n >= node.quantity, call remove(node). 
	  *     2. else, decrease node.quantity by n. 
	  *     
	  * Throw an exception if n < 0. 
	  * 
	  * @param fruit
	  * @param n	
	  */
	 public void sell(String fruit, int n) throws IllegalArgumentException 
	 {
		 // TODO
	 }
	 
	 /** 
	  * Process an order for multiple fruits as follows.  
	  * 
	  *     1. Sort the ordered fruits and their quantities by fruit name using the private method 
	  *        quickSort(). 
	  *     2. Traverse the N-list. Whenever a node with the next needed fruit is encountered, 
	  *        let m be the total number of a type of fruit to be ordered, and do the following: 
	  *        a) if m < 0, throw an exception; 
	  *        b) if m == 0, ignore. 
	  *        c) if 0 < m < node.quantity, decrease node.quantity by n. 
	  *        d) if m >= node.quanity, call remove(node).
	  * 
	  * @param fruitFile
	  */
	 public void bulkSell(String fruitFile)
	 {
		 // TODO 
	 }
	 
	 /**
	  * 
	  * @param fruit
	  * @return quantity of the fruit (zero if not on stock)
	  */
     public int inquire(String fruit)
     {
    	 // TODO 
    	 
    	 return 0;   	 
      }
	 
	 /**
	  * Output a string that gets printed out as the inventory of fruits by names.  
	  * The exact format is given in Section 5.1.  Here is a sample:   
	  *
	  *  
	  * fruit   	quantity    bin
	  * ---------------------------
	  * apple  			50  	5
	  * banana    		20      9
	  * grape     		100     8
	  * pear      		40      3 
	 */
	 public String printInventoryN()
	 {	 
		 // TODO 
		 
		 return null; 
	 }
	 
	 /**
	  * Output a string that gets printed out as the inventory of fruits by storage bin. 
	  * Use the same formatting rules for printInventoryN().  Below is a sample:  
	  * 
	  * bin 	fruit     	quantity      
	  * ----------------------------
	  * 3		pear      	40             
	  * 5		apple     	50            
	  * 8		grape     	100           
	  * 9		banana    	20  
	  *           	 
	  */
	 public String printInventoryB()
	 {
		 // TODO 
		 
		 return null; 
	 }
	 
	 @Override
	 /**
	  *  The returned string should be printed out according to the format in Section 5.1, 
	  *  exactly the same required for printInventoryN(). 
	  */
	 public String toString()
	 {
		 // TODO 
		 
		 return null; 
	 }
	 
	 /**
	  *  Relocate fruits to consecutive bins starting at bin 1.  Need only operate on the
	  *  B-list. 
	  */
	 // 
	 public void compactStorage()
	 {
		 // TODO 
	 }
	 
	 /**
	  *  Remove all nodes by setting headN = null and headB = null.
	  */
	 public void clearStorage()
	 {
		 // TODO 
	 }
	 
	 /** 
	  * Split the list into two doubly-sorted lists DST1 and DST2.  Let N be the total number of 
	  * fruit types. Then DST1 will contain the first N/2 types fruits in the alphabetical order.  
	  * DST2 will contain the remaining fruit types.  The algorithm works as follows. 
	  * 
	  *     1. Traverse the N-list to find the median of the fruits by name. 
	  *     2. Split at the median into two lists: DST1 and DST2.  
	  *     3. Traverse the B-list.  For every node encountered add it to the B-list of DST1 or 
	  *        DST2 by comparing node.fruit with the name of the median fruit. 
	  *        
	  * @return   the two doubly-sorted lists DST1 and DST2 as a pair. 
	  */
	 public Pair<DoublySortedList> split()
	 {
		 // TODO 
		 
		 return null; 
	 }
	 
	 /**
	  * Perform insertion sort on the doubly linked list with head node using a comparator 
	  * object, which is of either the NameComparator or the Bincomparator class. 
	  * 
	  * Made a public method for testing by TA. 
	  * 
	  * @param name   sort the N-list if true and the B-list otherwise. 
	  * @param comp
	  */
	 public void insertionSort(boolean NList, Comparator<Node> comp)
	 {
		 // TODO 
	 }
	 
	 /**
	  * Sort an array of fruit names using quicksort.  After sorting, bin[i] is the 
	  * storage for fruit name[i].  
	  * 
	  * Made a public method for testing by TA. 
	  * 
	  * @param size		number of fruit names 
	  * @param fruit   	array of fruit names 
	  * @param quant	array of fruit quantities 
	  */
	 public void quickSort(String fruit[], Integer quant[], int size)
	 {
		 // TODO 
	 }

	 // --------------
	 // helper methods 
	 // --------------
	 
	 /**
	  * Add a node between two nodes prev and next in the N-list.   
	  * Update headN if the added node becomes the first node on the list in the 
	  * alphabetical order of fruit name. 
	  * 
	  * @param node
	  * @param prev
	  * @param next
	  */
	 private void insertN(Node node, Node prev, Node next)
	 {
		 // TODO 
	 }
	
	 /**
	  * Add a node between two nodes prev and next in the B-list.  
	  * Update headB if the added node becomes the first node on the list in 
	  * the order of bin number. 
	  * 
	  * @param node
	  * @param prev
	  * @param next
	  */
	 private void insertB(Node node, Node prev, Node next)
	 {	 
		 // TODO 
	 }
	 
	 /**
	  * Remove node from both linked lists. Check if node is headN or headB, and reset the
	  * corresponding link if yes.  If the both lists become empty, set headN and headB to 
	  * null. 
	  * 
	  * @param node
	  */
	 private void remove(Node node)
	 {
		 // TODO 
	 }
	 
	 /**
	  * 
	  * @param name		name[first, last] is the subarray of fruit names 
	  * @param bin		bin[first, last] is the subarray of bins storing the fruits.
	  * @param first
	  * @param last
	  */
	 private void partition(String fruit[], Integer quant[], int first, int last)
	 {
		 // TODO 
	 }
}
