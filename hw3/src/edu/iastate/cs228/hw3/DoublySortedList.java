package edu.iastate.cs228.hw3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
	 private int size;     			// number of different kinds of fruits [ counted by insertN() and initWithHead() ]
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
	  * @param inventoryFile
	  * 	The name of the file used to initialize the lists.
	  * @throws FileNotFoundException
	  * 	Thrown when the inventoryFile could not be found.
	  */
	 public DoublySortedList(String inventoryFile) throws FileNotFoundException
	 {
		 // open the target file
		 File f = new File(inventoryFile);
		 Scanner s = new Scanner(f);
		 while (s.hasNextLine()) {
			 String line = s.nextLine();
			 Node next = procLine(line);
			
			 if (headN == null) {
				 // if the list is empty, initialize it
				 initWithHead(next);
				 
			 } else {
				 // otherwise insert the node to the end of the list
				 insertN(next, headN.previousN, headN);
				 insertB(next, headB.previousB, headB);
				 
			 }
		 }
		 
		 // sort the newly created lists
		 insertionSort(true, new NameComparator());
		 insertionSort(false, new BinComparator());
		 
		 // close the file
		 s.close();
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
		 // make sure a valid quantity is provided
		 if (n < 0) 
			 throw new IllegalArgumentException();
		 
		 if (n == 0) 
			 return;
		 
		 // if the list is empty, add a new node as the only node and the head
		 Node tmp = new Node(fruit, n, 1, null, null, null, null);
		 if (headN == null) {
			 initWithHead(tmp);
			 
			 return;
		 }
		 
		 // if not found, keep track of where this node will go (initialize to the front of the list)
		 Node prevN = headN.previousN;
		 Node prevB = headB.previousB;
		 
		 NameComparator compN = new NameComparator();
		
		 // traverse the n-list to see if the bin already exists
		 Node next = headN;
		 do {
			 if (next.fruit.equals(fruit)) {
				 // a bin was found, add the fruit and leave the method
				 next.quantity += n;
				 return;
			 }
			 
			 // if this node is less than tmp, and the next node is greater than tmp (or the head node)
			 // we will insert it after this node
			 if (compN.compare(next, tmp) < 0 && (compN.compare(next.nextN, tmp) > 0 || next.nextN == headN)) {
				 prevN = next;
				 
				 // the name falls between two nodes, therefore, we will not find it further down the list
				 break;
			 } 
			 
			 next = next.nextN;
		 } while (next != headN);
		 
		 // the fruit does not yet exist, traverse the b-list and find the first available bin
		 int bin = 1;
		 next = headB;
		 do {
			 if (next.bin == bin) {
				 // keep looking for the node
				 bin++;
				 
				 next = next.nextB;
				 
				 // end of the list reached, append the element
				 if (next == headB)
					 prevB = next.previousB;
				 
				 continue;
				 
			 } if (next.bin > bin){
				 // bin will be smaller than next.bin, therefore next is our next node, and bin is our bin number
				 prevB = next.previousB;
				 break;
				 
			 } else {
				 // the bins are not in order, if my code works, this error will not surface
				 System.err.println("Error - Trying to add a node when the bins are not order.");
				 throw new IllegalStateException();
				 
			 }
			 
		 } while (next != headB);
		 
		 // the node was not found, and we now know where to place it, insert it into the lists, and we're finally done
		 tmp.bin = bin;
		 insertN(tmp, prevN, prevN.nextN);
		 insertB(tmp, prevB, prevB.nextB);
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
		 // retrieve data in the format of dynamic arrays
		 ArrayList<String> fruit = new ArrayList<String>();
		 ArrayList<Integer> quantities = new ArrayList<Integer>();
		 readStockFile(fruitFile, fruit, quantities);
		 
		 // create static arrays and sort them
		 String[] fruitArr = fruit.toArray(new String[fruit.size()]);
		 Integer[] quantArr = quantities.toArray(new Integer[quantities.size()]);
		 quickSort(fruitArr, quantArr, fruitArr.length);
		 
		 int node = 0;
		 Node N = headN;
		 Node B = headB;
		 
		 do {
			 // this fruit already exists, add the quantity and move to the next node
			 if (N.fruit.equals(fruitArr[node])) {
				 N.quantity += quantArr[node];
				 node++;
				 
			 } else if (N.fruit.compareTo(fruitArr[node]) > 0) {
				 // the node was not found, find out where in the B-list to put it
				 Node n = new Node(fruitArr[node], quantArr[node], 1, null, null, null, null);
				 System.out.println("Attempting to place: " + n.fruit);
				 
				 if (B == headB && headB.bin > 1) {
					 System.out.println("Inserting: " + n.fruit);
					 insertN(n, N.previousN, N);
					 insertB(n, headB.previousB, headB);
					 N = n;
					 B = n;
					 node++;
					 continue;
					
				 } else {
					 System.out.println("Inserting: " + n.fruit);
					 
					 // find the next available bin
					 while (B.nextB.bin > B.bin + 1 && B.nextB == headB) {
						 B = B.nextB;
					 }
					
					 // add the node, and move to the next element to add
					 insertN(n, N.previousN, N);
					 insertB(n, B, B.nextB);
					 N = n;
					 B = n;
					 node++;
					 continue;
					 
					 // TODO
				 }
				 
			 }
			 
			 N = N.nextN;
		 } while (N != headN);
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
		 Node next = headN;
		 
		 do {
			 if (next.fruit.equals(fruit)) {
				 // remove the node and return
				 remove(next);
				 return;
				 
			 } else if (next.fruit.compareTo(fruit) > 0) {
				 // we've passed where the node would be, if it existed, return early
				 return;
				 
			 }
			 
			 next = next.nextN;
		 } while (next != headN);
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
		 if (bin < 1) throw new IllegalArgumentException();
		 
		 Node next = headB;
		 do {
			 if (next.bin == bin) {
				 // remove the node and return
				 remove(next);
				 return;
				 
			 } else if (next.bin > bin) {
				 // we've passed where the node would be, if it existed, return early
				 return;
				 
			 }
			 
			 next = next.nextB;
		 } while (next != headB);
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
		 if (n < 0)
			 throw new IllegalArgumentException();
		 
		 // we don't need to do anything if n is 0
		 if (n == 0)
			 return;
		 
		 Node next = headN;
		 
		 do {
			 if (next.fruit.equals(fruit)) {
				 if (n >= next.quantity)
					 remove(next);
				 else
					 next.quantity -= n;
				 
			 } else if (next.fruit.compareTo(fruit) > 0) {
				 // we passed where the fruit should be, return early
				 return;
				 
			 }
			 
			 next = next.nextN;
		 } while (next != headN);
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
	 public void bulkSell(String fruitFile) throws FileNotFoundException
	 {
		 // retrieve data in the format of dynamic arrays
		 ArrayList<String> fruit = new ArrayList<String>();
		 ArrayList<Integer> quantities = new ArrayList<Integer>();
		 readStockFile(fruitFile, fruit, quantities);
		 
		 // create static arrays and sort them
		 String[] fruitArr = fruit.toArray(new String[fruit.size()]);
		 Integer[] quantArr = quantities.toArray(new Integer[quantities.size()]);
		 quickSort(fruitArr, quantArr, fruitArr.length);
		 
		 Node next = headN;
		 
		 int node = 0;
		 do {
			 // we are done once we have sold all of our fruit
			 if (node >= fruitArr.length)
				 return;
			 
			 if (next.fruit.equals(fruitArr[node])) {
				 int m = quantArr[node];
				 if (m < 0) {
					 // TODO - make sure this is the correct exception to throw
					 throw new IllegalArgumentException();
					 
				 } else if (m >= next.quantity) {
					 remove(next);
					 
				 } else if (0 < m  && m < next.quantity) {
					 next.quantity -= m;
					 
				 }
				 
				 node++;
				 
			 } else if (next.fruit.compareTo(fruitArr[node]) > 0) {
				 // the node does not exist within this subset, skip it and continue
				 node++;
				 continue;
			 }
			 
			 next = next.nextN;
		 } while (next != headN);
		 
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
		 String output	=	"fruit          quantity       bin\n";
		 output 		+=	"----------------------------------\n";
		 
		 // traverse the linked list by fruit name
		 Node next = headN;
		 do {
			 output += getNodeDataN(next);
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
		 String output	=	"bin            fruit          quantity\n";
		 output 		+=	"---------------------------------------\n";
		 
		 // traverse the linked list by bin number
		 Node next = headB;
		 do {
			 output += getNodeDataB(next);
			 next = next.nextB;
			 
		 } while (next != headB);
		 
		 return output; 
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
		 // traverse the B-list and reset bin numbers
		 Node next = headB;
		 int i = 0;
		 
		 do {
			 next.bin = ++i;
			 
			 next = next.nextB;
		 } while (next != headB);
	 }
	 
	 /**
	  *  Remove all nodes by setting headN = null and headB = null.
	  */
	 public void clearStorage()
	 {
		 size = 0;
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
		 // traverse the N-list
		 Node median = headN;
		 int index = 0;
		 
		 do {
			 // find the median point
			 if (index == size/2) {
				 // break up the first half from the list
				 Node end = headN.previousN;
				 median.previousN.nextN = headN;
				 headN.previousN = median.previousN;
				 
				 // break up the second half from the list
				 median.previousN = end;
				 end.nextN = median;
				 
				 break;
			 }
			 
			 median = median.nextN;
			 index++;
		 } while (median != headN);
		 
		 // loop through the b-list
		 Node next = headB;
		 Node headB0 = null, headB1 = null, curB0 = null, curB1 = null;
		 
		 // sort the b-list
		 do {
			 boolean first = next.fruit.compareTo(median.fruit) < 0;
			 Node current = first ? curB0 : curB1;
			 
			 if (first && headB0 == null) {
				 headB0 = next;
				 curB0 = next;
				 
			 } else if (!first && headB1 == null) {
				 headB1 = next;
				 curB1 = next;
				 
			 } else {
				 current.nextB = next;
				 next.previousB = current;
				 current = next;
				 
			 }
			 
			 next = next.nextB;
		 } while (next != headB);
		 
		 // link the front of the each b-list to the back
		 headB0.previousB = curB0;
		 curB0.nextB = headB0;
		 
		 headB1.previousB = curB1;
		 curB1.nextB = headB1;
		 
		 return new Pair<DoublySortedList>(
				 	new DoublySortedList(size/2, headN, headB0), 
				 	new DoublySortedList(size - size/2, median, headB1) );
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
	  * @param size		
	  * 	number of fruit names 
	  * @param fruit   	
	  * 	array of fruit names 
	  * @param quant	
	  * 	array of fruit quantities 
	  */
	 public void quickSort(String fruit[], Integer quant[], int size)
	 {
		 quickSort(fruit, quant, 0, size-1);
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
	  * Utility method to assign proper links from a node when the list is empty.
	  * 
	  * @param node
	  * 	The node to initialize the list with.
	  * 
	  * @throws IllegalArgumentException
	  * 	headN or headB are not null
	  */
	 private void initWithHead(Node node) throws IllegalArgumentException
	 {
		 if (headN != null || headB != null) {
			 System.err.println("Error - trying to initialize a non-empty list.");
			 throw new IllegalArgumentException();
		 }
		 
		 // the node should reference itself
		 node.nextB = node.nextN = node.previousB = node.previousN = node;
		 headN = node;
		 headB = node;
		 size = 1;
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
		 size++;
		 
		 // set this node as the head
		 if (headN == null) {
			 initWithHead(node);
			 return;
		 }
		 
		 prev.nextN = node;
		 node.previousN = prev;
		 
		 next.previousN = node;
		 node.nextN = next;
		 
		 // if next was the headN, node is now headN, else, next was never headN
		 if (new NameComparator().compare(node, next) < 0)
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
		 if (headB == null) {
			 initWithHead(node);
			 return;
		 }
		 
		 prev.nextB = node;
		 node.previousB = prev;
		 
		 next.previousB = node;
		 node.nextB = next;
		 
		 // if next was the headB, node is now headB, else, next was never headB
		 if (new BinComparator().compare(node, next) < 0)
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
		 
		 size--;
	 }
	 
	 /**
	  *	Sort an array of fruit using quicksort. After sorting, bin[i] is the
	  *		storage for fruit name[i]. 
	  *
	  * @param fruit
	  * 	Array of fruit to be sorted.
	  * @param quant
	  * 	Array of quant to be sorted alongside fruit.
	  * @param first
	  * 	First subset of the array.
	  * @param last
	  */
	 private void quickSort(String[] fruit, Integer[] quant, int first, int last)
	 {
		 // we are done sorting
		 if (first >= last)
			 return;

		 // partition this subset
		 int p = partition(fruit, quant, first, last);

		 // recurse
		 quickSort(fruit, quant, first, p-1);
		 quickSort(fruit, quant, p+1, last);
	 }
	 
	 /**
	  * Sorts fruit and bin from first to last so everything before the pivot is smaller than the pivot
	  * and everything after the pivot is larger, the separation point for the arrays is then returned.
	  * 
	  * @param first	
	  * 	name[first, last] is the subarray of fruit names 
	  * @param quantity
	  * 	quantity[first, last] is the subarray of bins storing the fruits.
	  * @param first
	  * 	The first index of the subarray [inclusive].
	  * @param last
	  * 	The last index of the subarray [inclusive].
	  */
	 private int partition(String fruit[], Integer quantity[], int first, int last)
	 {
		 if (first > last || first < 0 || last < 0)
			 throw new IllegalArgumentException("Invalid First: " + first + " or Last: " + last + " argumnent.");
		 
		 // pick a random pivot, and set it to the last position
		 int r = (int)(Math.random() * (last-first+1));
		 swap(fruit, first + r, last);
		 swap(quantity, first + r, last);
		 
		 String p = fruit[last];
		 int i = first-1;
		 
		// move all elements smaller than or equal to p to the left of p, and all elements greater, to the right
		for (int j=first; j<last; j++) {
			if (fruit[j].compareTo(p) <= 0) {
				i++;
				
				swap(fruit, i, j);
				swap (quantity, i, j);
			}
		}
		
		// swap the last element with j, so that elements < j are smaller than p
		i++;
		swap(fruit, last, i);
		swap(quantity, last, i);
		return i;
	 }
	 
	 /**
	  * Fill out a string with a Node's data, Fruit first.
	  * 
	  * @param addTo
	  * 	The string to add the Node's data to.
	  * @param withData
	  * 	The node who's data to use.
	  */
	 private String getNodeDataN(Node withData)
	 {
		 // take advantage of the Node's toString method
		 return withData.toString() + '\n';
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
	 private String getNodeDataB(Node withData)
	 {
		 String ret;
		 
		 ret  = withData.bin	+ new String( new char[15  - (int)Math.log10(withData.bin) + 1] ).replace('\0', ' ')
		 						+ withData.fruit	+ new String( new char[15 - withData.fruit.length()] ).replace('\0', ' ')
		 						+ withData.quantity	+ new String( new char[4 - (int)Math.log10(withData.quantity) + 1] ).replace('\0', ' ')
		 						+ '\n';
		 return ret;
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
	  * Takes a file, and appends all data to the supplies ArrayLists.
	  * 
	  * @param file
	  * 	Input file to read data from.
	  * @param fruit
	  * 	Empty ArrayList of fruits to fill with data.
	  * @param quantities
	  * 	Empty ArrayList of integers to fill with data.
	  * @throws IllegalArgumentExcpetion
	  * 	The caller provided null references for either of the ArrayLists.
	  * @throws FileNotFoundException
	  */
	 private void readStockFile(String file, ArrayList<String> fruit, ArrayList<Integer> quantities) throws FileNotFoundException
	 {
		 if (fruit == null || quantities == null)
			 throw new IllegalArgumentException();
		 
		 Scanner s = new Scanner(new File(file));
		 while (s.hasNext()) {
			 Scanner tmp = new Scanner( s.nextLine() );
			 fruit.add( tmp.next() );
			 quantities.add( tmp.nextInt() );
		 }

		 s.close();
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
