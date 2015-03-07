package edu.iastate.cs228.hw3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Represents the current stock of fruit of a particular grocery store.
 * 	The stock is represented by two sorted doubly linked lists,
 * 	one sorting the stock alphabetically by fruit,
 * 	the other sorting stock alphabetically by bin number.
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
		 // open the target file
		 File f = new File(inventoryFile);
		 Scanner s = new Scanner(f);
		 while (s.hasNextLine()) {
			 String line = s.nextLine();
			 Node next = procLine(line);
			 
			 // TODO insert next to the linked list
		 }
		 
		 // close the file
		 s.close();
	 }
	 
	 /**
	  * Get the information from the line, and return it as a Node.
	  * The node will only contain the data represeted by the file:
	  * 	Fruit	Quantity	Bin
	  * And will not contain references to the rest of the linked list.
	  * 
	  * @return
	  * 	The data from the input file, contained in the Node class.
	  */
	 private Node procLine(String line) {
		 Scanner s = new Scanner(line);
		 String name = s.next();
		 int quantity = s.nextInt();
		 int bin = s.nextInt();
		 
		 return new Node(name, quantity, bin, null, null, null, null);
	 }
	 
	 /**
	  * Requests the number of nodes present in the linked lists.
	  * 
	  * @return
	  * 	The number of nodes in the linked list.
	  */
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
	  * @param fruit  
	  * 	name of the fruit to be added 
	  * @param n	  
	  * 	quantity of the fruit
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
	  * Request the number of fruit stored in the linked list.
	  * 
	  * @param 
	  * 	fruit
	  * @return 
	  * 	quantity of the fruit (zero if not on stock)
	  */
     public int inquire(String fruit)
     {
    	 Node next = headN;
    	 do {
    		 if (next.fruit.equals(fruit)) {
    			 return next.quantity;
    		 }
    		 
    		 next = next.nextN;
    		 
    	 } while (next != headN);
    	 
    	 // fruit was not found to be in stock
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
		 // print the header
		 String output	=	"fruit          quantity       bin";
		 output 		+=	"----------------------------------";
		 
		 // traverse the linked list by fruit name
		 Node next = headN;
		 do {
			 addNodeDataN(output, next);
			 next = next.nextN;
			 
		 } while (next != headN);
		 
		 return output; 
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
		 // print the header
		 String output	=	"bin            fruit          quantity";
		 output 		+=	"---------------------------------------";
		 
		 // traverse the linked list by bin number
		 Node next = headB;
		 do {
			 addNodeDataB(output, next);
			 next = next.nextB;
			 
		 } while (next != headB);
		 
		 return null; 
	 }
	 
	 /**
	  * Fill out a string with a Node's data, Fruit first.
	  * 
	  * @param addTo
	  * 	The string to add the Node's data to.
	  * @param withData
	  * 	The node who's data to use.
	  */
	 private void addNodeDataN(String toString, Node withData)
	 {
		 // take advantage of the Node's toString method
		 toString += withData.toString();
		 toString += '\n';
	 }
	 
	 /**
	  * Fill out a string with a Node's data, Bin first.
	  * 
	  * @param toString
	  * 	The string to add the Node's data to.
	  * 
	  * @param withData
	  * 	The node who's data to use.
	  */
	 private void addNodeDataB(String toString, Node withData)
	 {
		 toString += withData.bin		+ new String( new char[15  - (int)Math.log10(withData.bin) + 1] );
		 toString += withData.fruit		+ new String( new char[15 - withData.fruit.length()] );
		 toString += withData.quantity	+ new String( new char[4 - (int)Math.log10(withData.quantity) + 1] );
		 toString += '\n';
	 }
	 
	 @Override
	 /**
	  *  The returned string should be printed out according to the format in Section 5.1, 
	  *  exactly the same required for printInventoryN(). 
	  */
	 public String toString()
	 {
		 return printInventoryN(); 
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
		 headN = null;
		 headB = null;
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
		 // if this is the only element, return, the array is already sorted
		 if (headN.nextN == headN)
			 return;
		 
		 // the current element we are sorting (do not need to sort the first element
		 Node next = NList ? headN : headB;
		 next = NList ? next.nextN : next.nextB;
		 
		 // traverse forward for each Node in the list
		 do {
			 Node prev = NList ? next.previousN : next.previousB;
			 
			 // while the next node is less then the previous node
			 while (comp.compare(next, prev) < 0) {
				 if (NList)
					 swapN(next, prev);
				 else
					 swapB(next, prev);
				 
				 // next is in the prev position, use the next previous of next
				 prev = NList ? next.previousN : next.previousB;
			 }
			 
			 next = NList ? next.nextN : next.nextB;
		 } while (next != (NList ? headN : headB));
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
	  * Swap the position of two nodes in the name list.
	  * 
	  * @param first
	  * 	The first Node to be swapped.
	  * @param second
	  * 	The second Node to be swapped.
	  */
	 private void swapN(Node first, Node second)
	 {
		 // swap the forward pointing reference
		 Node tmp = first.nextN;
		 first.nextN = second.nextN;
		 second.nextN = tmp;
		 
		 first.nextN.previousN = first;
		 second.nextN.previousN = second;
		 
		 // swap the backward pointing reference
		 tmp = first.previousN;
		 first.previousN = second.previousN;
		 second.previousN = tmp;
		 
		 first.previousN.nextN = first;
		 second.previousN.nextN = second;
		 
	 }
	 
	 /**
	  * Swap the position of two nodes in the bin list.
	  * 
	  * @param first
	  * 	The first Node to be swapped.
	  * @param second
	  * 	The second Node to be swapped.
	  */
	 private void swapB(Node first, Node second)
	 {
		 // swap the forward pointing reference
		 Node tmp = first.nextB;
		 first.nextB = second.nextB;
		 second.nextB = tmp;
		 
		 first.nextB.previousB = first;
		 second.nextB.previousB = second;
		 
		 // swap the backward pointing reference
		 tmp = first.previousB;
		 first.previousB = second.previousB;
		 second.previousB = tmp;
		 
		 first.previousB.nextB = first;
		 second.previousB.nextB = second;
		 
	 }
	 
	 /**
	  * Add a node between two nodes prev and next in the N-list.   
	  * Update headN if the added node becomes the first node on the list in the 
	  * alphabetical order of fruit name. 
	  * 
	  * @param node
	  * 	The inserted node.
	  * @param prev
	  * 	The node that will exist AFTER the inserted node, if this is headN, node will now be second.
	  * @param next
	  * 	The node that will exist BEFORE the inserted node, if this is headN, node will now be headN.
	  */
	 private void insertN(Node node, Node prev, Node next)
	 {
		 prev.nextN = node;
		 node.previousN = prev;
		 
		 next.previousN = node;
		 node.nextN = next;
		 
		 // if next was the headN, node is now headN, else, next was never headN
		 headN = (next == headN) ? node : headN;
	 }
	
	 /**
	  * Add a node between two nodes prev and next in the B-list.  
	  * Update headB if the added node becomes the first node on the list in 
	  * the order of bin number. 
	  * 
	  * @param node
	  * 	The inserted node.
	  * @param prev
	  * 	The node that will exist AFTER the inserted node, if this is headB, node will now be second.
	  * @param next
	  * 	THe node that will exist BEFORE the inserted node, if this is headB, node will no be headB.
	  */
	 private void insertB(Node node, Node prev, Node next)
	 {
		 prev.nextB = node;
		 node.previousB = prev;
		 
		 next.previousB = node;
		 node.nextB = next;
		 
		 // if next was the headB, node is now headB, else, next was never headB
		 headB = (next == headB) ? node : headB;
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
		 // if node references itself, it is the only node in the list, clear the entire storage
		 if (node.nextN == node) {
			 clearStorage();
			 
			 return;
		 }
		
		 // otherwise, link next and prev together, remove the link to node from the list
		 node.nextN.previousN = node.previousN;
		 node.previousN.nextN = node.nextN;
		 
		 node.nextB.previousB = node.previousB;
		 node.previousB.nextB = node.nextB;
		 
		 // if this node is head, node.next is now head, else, node was never head
		 headN = (node == headN) ? node.nextN : headN;
		 headB = (node == headB) ? node.nextB : headB;
	 }
	 
	 /**
	  * Sorts fruit and bin from first to last so everything before the pivot is smaller than the pivot
	  * and everything after the pivot is larger, the separation point for the arrays is then returned.
	  * 
	  * @param first	
	  * 	name[first, last] is the subarray of fruit names 
	  * @param bin		
	  * 	bin[first, last] is the subarray of bins storing the fruits.
	  * @param first
	  * 	The first index of the subarray [inclusive].
	  * @param last
	  * 	The last index of the subarray [inclusive].
	  */
	 private int partition(String fruit[], Integer bin[], int first, int last)
	 {
		 if (first > last || first < 0 || last < 0)
			 throw new IllegalArgumentException("Invalid First: " + first + " or Last: " + last + " argumnent.");
		 
		 // pick a random pivot, and set it to the last position
		 swap(fruit, first + (int)(Math.random() * (last-first+1)), last);
		 swap(bin, first + (int)(Math.random() * (last-first+1)), last);
		
		 // TODO
		 return 0;
	 }
	 
	 /**
	  * Swap two elements in an array.
	  * 
	  * @param arr
	  * 	The input array in which the swap will occur.
	  * @param i0
	  * 	The first index of the swap.
	  * @param i1
	  * 	The second index of the swap.
	  */
	 private <T> void swap(T[] arr, int i0, int i1)
	 {
		 T tmp = arr[i0];
		 arr[i0] = arr[i1];
		 arr[i1] = tmp;
	 }
}
