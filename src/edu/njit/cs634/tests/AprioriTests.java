package edu.njit.cs634.tests;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.njit.cs634.apriori.Apriori;

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
		apriori.getHandler().setFilePath("/Users/jmb66/Documents/NJIT/CS634/D1.txt");
		apriori.parseFile();
		assertTrue("Test size of ItemTable after parseFile()", apriori.getItemTable().size() == 20);
		assertTrue("Test size of OccurrenceTable after parseFile()", apriori.getItemOccurrences().size() == 10);
		apriori.createPositionMap();
		assertTrue("Test size of PositionMap after createPositionMap()", apriori.getPositionMap().size() == 10);
		Set<String> s = apriori.getPositionMap().keySet();
		Iterator<String> iter = s.iterator();
		while(iter.hasNext())
		{
			String key = iter.next();
			System.out.println("Key: "+ key + "\tValue: " + apriori.getPositionMap().get(key));
		}
		
		apriori.createItemSetTable();
		Long[][] it = apriori.getItemSetTable();
		assertTrue("Test size of ItemSetTable after createItemSetTable()",it.length == 20);
		for(int i=0; i<it.length; i++)
		{
			System.out.println(i + ": " + it[i][0]);
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
