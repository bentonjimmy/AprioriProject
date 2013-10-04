package edu.njit.cs634.tests;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeMap;

import edu.njit.cs634.apriori.FileHandler;
import junit.framework.TestCase;

public class FileHandlerTests extends TestCase {
	
	private FileHandler handler;

	protected static void setUpBeforeClass() throws Exception {
	}

	protected static void tearDownAfterClass() throws Exception {
	}

	protected void setUp() throws Exception {
		handler = new FileHandler("/Users/jmb66/Documents/NJIT/CS634/D1.txt");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testReadFile()
	{
		LinkedList ll;
		
		handler.readFile();
		Hashtable<String, LinkedList<String>> ht = handler.getTable();
		assertTrue("Check size of ht", ht.size() == 20);
		ll = ht.get("2");
		assertTrue("Transaction 2 Item 1", ll.get(0).equals("Mining the Social Web - Matthew Russell"));
		assertTrue("Transaction 2 Item 2", ll.get(1).equals("The Hunger Games"));
		Set s = ht.entrySet();
		Iterator iter = s.iterator();
		while(iter.hasNext())
		{
			System.out.println(iter.next());
		}
		
		assertTrue("Test word occurrences", handler.getParser().getOccurrenceTable().size() == 10);
		TreeMap tm = handler.getParser().getOccurrenceTable();
		s = tm.entrySet();
		iter = s.iterator();
		while(iter.hasNext())
		{
			System.out.println(iter.next());
		}
		
	}
}