package edu.njit.cs634.tests;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeMap;

import edu.njit.cs634.apriori.FileHandler;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class FileHandlerTests {
	
	private FileHandler handler;

	@Before
	public void setUp() throws Exception {
		handler = new FileHandler("/Users/jmb66/Documents/NJIT/CS634/D1.txt");
	}

	@Test
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
		System.out.println("Printing Transaction Table");
		while(iter.hasNext())
		{
			System.out.println(iter.next());
		}
		
		assertTrue("Test word occurrences", handler.getParser().getOccurrenceTable().size() == 10);
		TreeMap tm = handler.getParser().getOccurrenceTable();
		s = tm.entrySet();
		iter = s.iterator();
		System.out.println("Printing Word Occurrence Table");
		while(iter.hasNext())
		{
			System.out.println(iter.next());
		}
		
	}
}
