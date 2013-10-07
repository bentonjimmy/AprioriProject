package edu.njit.cs634.tests;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.njit.cs634.apriori.Apriori;
import edu.njit.cs634.apriori.ItemList;

public class AprioriTests extends Apriori {
	
	public Apriori apriori;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		apriori = new Apriori();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSetup() {
		apriori.getHandler().setFilePath("/Users/jmb66/Documents/NJIT/CS634/Monkey_Test.txt");
		apriori.parseFile();
		assertTrue("Test size of ItemTable after parseFile()", apriori.getItemTable().size() == 5);
		assertTrue("Test size of OccurrenceTable after parseFile()", apriori.getItemOccurrences().size() == 11);
		apriori.createPositionMap();
		assertTrue("Test size of PositionMap after createPositionMap()", apriori.getPositionMap().size() == 11);
		Set<String> s = apriori.getPositionMap().keySet();
		Iterator<String> iter = s.iterator();
		System.out.println("Printing Position Map");
		while(iter.hasNext())
		{
			String key = iter.next();
			System.out.println("Key: "+ key + "\tValue: " + apriori.getPositionMap().get(key));
		}
		
		apriori.createItemSetTable();
		Long[][] it = apriori.getItemSetTable();
		assertTrue("Test size of ItemSetTable after createItemSetTable()",it.length == 5);
		System.out.println("Printing ItemSetTable");
		for(int i=0; i<it.length; i++)
		{
			System.out.println(i + ": " + it[i][0]);
		}
		
		apriori.setSupport(3);
		
		apriori.calculateCommonSets();
		assertTrue("Check size of occurrence table after calculateCommonSets()", apriori.getItemOccurrences().size() == 10);
		assertTrue("Check size of commonItems table after calculateCommonSets()", apriori.getCommonItemsTable().size() == 5);
		assertTrue("Check size of non-Common items table after calculateCommonSets()", apriori.getNonCommonItems().size() == 6);
		apriori.calculateCommonSets();
		/*
		TreeMap<ItemList, Integer> tm = apriori.getItemOccurrences();
		Set<Entry<ItemList, Integer>> ioSet = tm.entrySet();
		Iterator<Entry<ItemList, Integer>> ioIter = ioSet.iterator();
		System.out.println("Printing Word Occurrence Table");
		while(ioIter.hasNext())
		{
			System.out.println(ioIter.next());
		}
		*/
		assertTrue("Check size of occurrence table after calculateCommonSets() - 2nd", apriori.getItemOccurrences().size() == 1);
		assertTrue("Check size of commonItems table after calculateCommonSets() - 2nd", apriori.getCommonItemsTable().size() == 10);
		assertTrue("Check size of non-Common items table after calculateCommonSets() - 2nd", apriori.getNonCommonItems().size() == 11);
		
		while(apriori.getItemOccurrences().size() > 0)
		{
			apriori.calculateCommonSets();
		}
		Set<ItemList> sCI = apriori.getCommonItemsTable().keySet();
		Iterator<ItemList> iterCI = sCI.iterator();
		System.out.println("Printing Common Itemsets");
		while(iterCI.hasNext())
		{
			System.out.println(iterCI.next());
		}
	}

	@Test
	public void testMultipleFiles()
	{
		apriori.getHandler().setFilePath("/Users/jmb66/Documents/NJIT/CS634/D1.txt");
		apriori.parseFile();
		apriori.createPositionMap();
		apriori.createItemSetTable();
		apriori.setSupport(3);
		while(apriori.getItemOccurrences().size() > 0)
		{
			apriori.calculateCommonSets();
		}
		Set<ItemList> sCI = apriori.getCommonItemsTable().keySet();
		Iterator<ItemList> iterCI = sCI.iterator();
		System.out.println("Printing Common Itemsets");
		while(iterCI.hasNext())
		{
			System.out.println(iterCI.next());
		}
		
		apriori = new Apriori();
		apriori.getHandler().setFilePath("/Users/jmb66/Documents/NJIT/CS634/Book.txt");
		apriori.parseFile();
		apriori.createPositionMap();
		apriori.createItemSetTable();
		apriori.setSupport(2);
		while(apriori.getItemOccurrences().size() > 0)
		{
			apriori.calculateCommonSets();
		}
		sCI = apriori.getCommonItemsTable().keySet();
		iterCI = sCI.iterator();
		System.out.println("Printing Common Itemsets");
		while(iterCI.hasNext())
		{
			System.out.println(iterCI.next());
		}
	}

	@Test
	public void testAddItem() {
		long l1 = apriori.addItemTest(0, 2);
		assertTrue("AddItem - Add item 2", l1 == 4);
		l1 = apriori.addItemTest(l1, 4);
		assertTrue("AddItem - Add item 4", l1 == 20);
		l1 = apriori.addItemTest(l1, 4);
		assertTrue("AddItem - Add item 4 - Should still be the same.", l1 == 20);
		l1 = apriori.addItemTest(l1, 10);
		assertTrue("AddItem - Add item 10", l1 == 1044);
	}

}
