package edu.iastate.cs228.hw3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for cs228 homework 3.
 * 
 * @author imalerich
 *
 */
public class ListTests 
{
	private DoublySortedList testList;
	
	/**
	 * Test loading from an input file.
	 */
	@Test
	public void testInputFile() 
	{
		testFile("tests/sample0.txt", 4);
		testFile("tests/sample1.txt", 10);
		testFile("tests/sample2.txt", 1);
		testFile("tests/sample3.txt", 2);
	}
	
	/**
	 * Test the add method.
	 */
	@Test
	public void testAdd()
	{
		testList = new DoublySortedList();
		Assert.assertEquals("Expected 0 elements in the list.", 0, testList.size());
		
		testList.add("banana", 4);
		Assert.assertEquals("Expected 1 element in the list.", 1, testList.size());
		Assert.assertEquals("Expected 4 bananas in the list.", 4, testList.inquire("banana"));
		Assert.assertEquals("Expected 0 apples in the list.", 0, testList.inquire("apple"));
		
		testList.add("apple", 18);
		Assert.assertEquals("Expected 18 apples in the list.", 18, testList.inquire("apple"));
		Assert.assertEquals("Expected 0 apricots in the list.", 0, testList.inquire("apricot"));
		
		testList.add("banana", 16);
		Assert.assertEquals("Expected 20 bananas in the list.", 20, testList.inquire("banana"));
		Assert.assertEquals("Adding a banana should not change the state of the apple.", 18, testList.inquire("apple"));
		
		testToString(testList);
		testCompactStorage(testList);
		testClearStorage(testList);
	}
	
	/**
	 * Test the restock method.
	 */
	@Test
	public void testRestock0()
	{
		testList = new DoublySortedList();
		testList.add("banana", 20);
		testList.add("pear", 20);
		
		try {
			testList.restock("tests/restock0.txt");
			
			Assert.assertEquals("After restocking 40 pears, expected 60 total pears.", 60, testList.inquire("pear"));
			Assert.assertEquals("After restocking 100 grapes, expected 100 total grapes.", 100, testList.inquire("grape"));
			Assert.assertEquals("After restocking 50 apples, expected 50 total apples.", 50, testList.inquire("apple"));
			Assert.assertEquals("After restocking 20 bananas, expected 40 total bananas.", 40, testList.inquire("banana"));
		
		} catch (FileNotFoundException e) {
			Assert.fail("Could not find the file tests/restock0.txt, are you sure this file exists?");
			
		}
		
		testList = new DoublySortedList();
		testList.add("apple", 20);
		testList.add("banana", 20);
		
		try {
			testList.restock("tests/restock0.txt");
			
			Assert.assertEquals("After restocking 40 pears, expected 40 total pears.", 40, testList.inquire("pear"));
			Assert.assertEquals("After restocking 100 grapes, expected 100 total grapes.", 100, testList.inquire("grape"));
			Assert.assertEquals("After restocking 50 apples, expected 70 total apples.", 70, testList.inquire("apple"));
			Assert.assertEquals("After restocking 20 bananas, expected 40 total bananas.", 40, testList.inquire("banana"));
		
		} catch (FileNotFoundException e) {
			Assert.fail("Could not find the file tests/restock0.txt, are you sure this file exists?");
			
		}
	}
	
	/**
	 * Test the bulk sell method.
	 */
	@Test
	public void testBulkSell()
	{
		testList = new DoublySortedList();
		testList.add("watermelon", 26);
		testList.add("pear", 50);
		testList.add("apple", 65);
		testList.add("grape", 50);
		testList.add("banana", 21);
		testList.add("pineapple", 33);
		
		try {
			testList.bulkSell("tests/restock0.txt");
			
			Assert.assertEquals("After bulkSell, expected 10 pears.", 10, testList.inquire("pear"));
			Assert.assertEquals("After bulkSell, expected 16 apples.", 15, testList.inquire("apple"));
			Assert.assertEquals("After bulkSell, expected, 0 grapes.", 0, testList.inquire("grape"));
			Assert.assertEquals("After bulkSell, expected 1 banana.", 1, testList.inquire("banana"));
			Assert.assertEquals("After bulkSell, expected 33 pineapples", 33, testList.inquire("pineapple"));
			Assert.assertEquals("After bulkSell, expected 26 watermelons", 26, testList.inquire("watermelon"));
		
		} catch (FileNotFoundException e) {
			Assert.fail("Could not find the file tests/restock0.txt, are you sure this file exists?");
			
		}
	}
	
	/**
	 * Test both of the removal methods.
	 */
	@Test
	public void testRemove()
	{
		testList = new DoublySortedList();
		
		testList.add("tangerine", 6);
		Assert.assertEquals("Expected 6 tangerines.", 6, testList.inquire("tangerine"));
		
		testList.add("lychee", 22);
		Assert.assertEquals("Expected 22 lychees.", 22, testList.inquire("lychee"));
		
		testList.add("prune", 87);
		Assert.assertEquals("Expected 87 prunes", 87, testList.inquire("prune"));
		
		testToString(testList);
		
		testList.remove("prune");
		Assert.assertEquals("Expected 6 tangerines after prune removal.", 6, testList.inquire("tangerine"));
		Assert.assertEquals("Expected 22 lychees after prune removal.", 22, testList.inquire("lychee"));
		Assert.assertEquals("Expected 0 prunes after prune removeal.", 0, testList.inquire("prune"));
		
		testToString(testList);
		
		testList.remove(1);
		Assert.assertEquals("Expected 22 lychees after bin 1 removal.", 22, testList.inquire("lychee"));
		Assert.assertEquals("Expected 0 tangerines after bin 1 removal.", 0, testList.inquire("tangerine"));
		
		testList.add("lemon", 27);
		testList.add("durian", 88);
		testList.add("fig", 2);
		
		testToString(testList);
		
		Assert.assertEquals("Expected 22 lychees.", 22, testList.inquire("lychee"));
		Assert.assertEquals("Expected 27 lemons.", 27, testList.inquire("lemon"));
		Assert.assertEquals("Expected 88 durians.", 88, testList.inquire("durian"));
		Assert.assertEquals("Expected 2 figs.", 2, testList.inquire("fig"));
		
		testToString(testList);
		
		testList.remove("durian");
		Assert.assertEquals("Expected 0 durians after removal.", 0, testList.inquire("durian"));
		Assert.assertEquals("Expected 22 lychees after durian removal.", 22, testList.inquire("lychee"));
		Assert.assertEquals("Expected 27 lemons after durian removal.", 27, testList.inquire("lemon"));
		Assert.assertEquals("Expected 2 figs after durian removal.", 2, testList.inquire("fig"));
		
		testToString(testList);
	}
	
	/**
	 * Test the sale methods.
	 */
	@Test
	public void testSell()
	{
		testList = new DoublySortedList();
		
		testList.add("strawberry", 18);
		testList.add("mango", 36);
		testList.add("pomelo", 22);
		testList.add("watermelon", 10);
		testList.add("banana", 42);
		testList.add("apple", 67);
		
		testToString(testList);
		
		Assert.assertEquals("Expected 10 watermleons pre-sale.", 10, testList.inquire("watermelon"));
		testList.sell("watermelon", 8);
		Assert.assertEquals("Expected 2 watermleons post-sale.", 2, testList.inquire("watermelon"));
		
		Assert.assertEquals("Expected 36 mangos pre-sale.", 36, testList.inquire("mango"));
		testList.sell("mango", 12);
		Assert.assertEquals("Expected 24 mangos post-sale.", 24, testList.inquire("mango"));
		
		Assert.assertEquals("Expected 22 pomelos pre-sale.", 22, testList.inquire("pomelo"));
		testList.sell("pomelo", 12);
		Assert.assertEquals("Expected 10 pomelos post-sale.", 10, testList.inquire("pomelo"));
		
		testToString(testList);
		
		testList.sell("pomelo", 12);
		Assert.assertEquals("Expected 0 pomelos post-sale.", 0, testList.inquire("pomelo"));
		
		testList.sell("mango", 36);
		Assert.assertEquals("Expected 0 mangos post-sale.", 0, testList.inquire("mango"));
		
		testToString(testList);
	}
	
	/**
	 * Test the split method.
	 */
	@Test
	public void testSplit() 
	{
		testFileWithSplit("tests/split0.txt", 4);
		testFileWithSplit("tests/split1.txt", 5);
		testFileWithSplit("tests/split2.txt", 6);
	}
	
	/**
	 * Test the to string method of list, this will test to make sure all data is present,
	 * 	and in the correct order for both printInventoryN and printInventoryB.
	 * 
	 * @param list
	 * 	The list object loaded with data from source.
	 * @param source
	 * 	The source string used to generate list.
	 */
	private void testToString(DoublySortedList list) 
	{
		Scanner s = new Scanner( list.printInventoryN() );
		testHeaders(true, s);
		testPrintInventoryN(s, "printInventoryN is not printing in alphabetical order");
		
		s = new Scanner( list.toString() );
		testHeaders(true, s);
		testPrintInventoryN(s, "toString is not printing in alphabetical order");
		
		s = new Scanner( list.printInventoryB() );
		testHeaders(false, s);
		
		int prevBin = -1;
		while (s.hasNextLine()) {
			Scanner tmp = new Scanner(s.nextLine());
			int bin = tmp.nextInt();
			
			// no check required on the first element
			if (prevBin == -1) {
				prevBin = bin;
				continue;
			}
			
			// each consecutive bin should be evaluated as larger than the previous string
			Assert.assertTrue("printInventoryB is not printing in chronological order.", bin > prevBin);
			prevBin = bin;
		}
		
		testToStringAgainstCount(list);
	}
	
	/**
	 * Counts up the number of elements retrieved from the toString method, then compares that to
	 * 	the amount returned by the size method.
	 * 
	 * @param list
	 * 	The list we wish to test.
	 */
	private void testToStringAgainstCount(DoublySortedList list)
	{
		Scanner s = new Scanner( list.toString() );
		testHeaders(true, s);
		
		int count = 0;
		while (s.hasNextLine()) {
			s.nextLine();
			count++;
		}
		
		Assert.assertEquals("toString outputed: " + count + " elements: while size returned: " + list.size() + " elements", 
				list.size(), count);
	}
	
	/**
	 * Going to be using this twice for printInventoryN and toString,
	 * 	just saving lines.
	 * 
	 * @param s	
	 * 	The scanner containing the data we are testing.
	 */
	private void testPrintInventoryN(Scanner s, String error)
	{
		// test to make sure the output information is in alphabetical order
		String prevName = null;
		while (s.hasNextLine()) {
			Scanner tmp = new Scanner(s.nextLine());
			String name = tmp.next();
			
			// no check required on the first element
			if (prevName == null) {
				prevName = name;
				continue;
			}
			
			// each consecutive name should be evaluated as larger than the previous string
			Assert.assertTrue(error, name.compareTo(prevName) > 0);
			prevName = name;
		}
	}
	
	/**
	 * Test the header provided by an output method, and moves the scanners
	 * 	cursor to the first elements position.
	 * 
	 * @param s
	 * 	Scanner with the string data loaded we wish to test.
	 */
	private void testHeaders(boolean testN, Scanner s)
	{
		if (testN) {
			String line0 =	"fruit           quantity        bin";
			String line1 =	"-----------------------------------";
		 
			Assert.assertTrue("printInventoryN - unexpected results in first line.", line0.equals(s.nextLine()));
			Assert.assertTrue("printInventoryN - unexpected results in second line.", line1.equals(s.nextLine()));
		}
		 
		 if (!testN) {
			 String line0 =	"bin             fruit           quantity";
			 String line1 =	"----------------------------------------";
		 
			Assert.assertTrue("printInventoryB - unexpected results in first line.", line0.equals(s.nextLine()));
			Assert.assertTrue("printInventoryB - unexpected results in second line.", line1.equals(s.nextLine()));
		 }
	}
	
	/**
	 * Test the compact storage method of the DoublySortedList.
	 * 
	 * @param list
	 * 	The input list to be tested.
	 */
	private void testCompactStorage(DoublySortedList list)
	{
		// compact the storage, this should label the bins - 1, 2, 3... etc
		list.compactStorage();
		
		// get a scanner to read the data from printInventoryB
		Scanner s = new Scanner(list.printInventoryB());
		testHeaders(false, s);
		int index = 1;
		
		// for each line
		while (s.hasNextLine()) {
			Scanner tmp = new Scanner(s.nextLine());
			int bin = tmp.nextInt();
			
			Assert.assertEquals("CompactStorage() does not appear to be putting the bins in a compact numerical order.", index++, bin);
		}
	}
	
	/**
	 * Test the clearStorage() method.
	 * 
	 * @param list
	 * 	The list we are going to clear.
	 */
	private void testClearStorage(DoublySortedList list)
	{
		list.clearStorage();
		Assert.assertEquals("The size of the list is not 0 after clearStorage, don't forget to set the size of 0.", 0, list.size());
		
		list.add("banana", 4);
		Assert.assertEquals("After clearing and adding an element, the size should be 1.", 1, list.size());
		
		list.add("apple", 18);
		Assert.assertEquals("After clearing and adding two elements, the size should be 2.", 18, list.inquire("apple"));
	}
	
	/**
	 * Perform some basic tests on different files.
	 * 
	 * @param file
	 * 	The input file to test.
	 */
	private void testFile(String file, int linecount) 
	{
		try {
			testList = new DoublySortedList(file);
			Assert.assertEquals("Expected " + linecount + " elements in the list.", linecount, testList.size());
			
			Scanner s = new Scanner(new File(file));
			while (s.hasNext()) {
				testLine(s.nextLine(), testList);
			}
			
			s.close();
			
			testToString(testList);
			testCompactStorage(testList);
			testClearStorage(testList);
			
		} catch (FileNotFoundException e){
			Assert.fail("Error - FileNotFoundException, does the file " + file + " exist?");
		}
	}
	
	private void testFileWithSplit(String file, int linecount)
	{
		try {
			// run some simple tests on the array as is
			testList = new DoublySortedList(file);
			Assert.assertEquals("Expected " + linecount + " elements in the list.", linecount, testList.size());
			testToString(testList);
			
			// now split the array, and run tests on both of the output arrays
			Pair<DoublySortedList> p = testList.split();
			
			int size = linecount/2;
			Assert.assertEquals("Expected the first sublist to have a size of " + size + '.', size, p.getFirst().size());
			Assert.assertEquals("Expected the second sublist to have a size of " + (linecount - size) + '.', 
					linecount - size, p.getSecond().size());
			
			testToString(p.getFirst());
			testToString(p.getSecond());
			
			testCompactStorage(p.getFirst());
			testCompactStorage(p.getSecond());
			
			testClearStorage(p.getFirst());
			testClearStorage(p.getSecond());
			
		} catch (FileNotFoundException e) {
			Assert.assertTrue("Could not find " + file + '.', false);
			
		}
	}

	/**
	 * Test a line of data from an input file.
	 * @param line
	 * 	The input line used to get the data.
	 * @param list
	 * 	The list to search for the data from.
	 */
	private void testLine(String line, DoublySortedList list)
	{
		String name = getLineName(line);
		int quantity = getLineQuantity(line);
		
		Assert.assertEquals("Expected " + quantity + " of " + name + " in the list.", quantity, list.inquire(name));
	}
	
	/**
	 * Utility to get name from a line of data.
	 * 
	 * @param line
	 * 	The input line to get the data from.
	 * @return
	 * 	The output name of the fruit.
	 */
	private String getLineName(String line)
	{
		Scanner s = new Scanner(line);
		
		return s.next();
	}
	
	/**
	 * Utility to get the quantity from a aline of data.
	 * 
	 * @param line
	 * 	The input line to get the data from.
	 * @return
	 * 	The output quantity of the fruit.
	 */
	private int getLineQuantity(String line)
	{
		Scanner s = new Scanner(line);
		s.next();
		
		return s.nextInt();
	}
}
