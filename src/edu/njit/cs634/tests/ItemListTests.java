package edu.njit.cs634.tests;

import org.junit.Test;
import edu.njit.cs634.apriori.ItemList;
import static org.junit.Assert.*;
import org.junit.Before;


public class ItemListTests {
	
	ItemList item1, item2, item3;

	@Before
	public void setUp() throws Exception {
		item1 = new ItemList();
		item2 = new ItemList();
		item3 = new ItemList();
	}

	@Test
	public void testCompare() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testAddItem() {
		assertTrue("Test it is set to 0", item1.getItemsNumber() == 0);
		item1.addItem(2);
		assertTrue("Test first item", item1.getItemsNumber() == 4);
		item1.addItem(4);
		assertTrue("Test second item", item1.getItemsNumber() == 20);
		item1.addItem(4);
		assertTrue("Should not cause change", item1.getItemsNumber() == 20);
		item1.addItem(10);
		assertTrue("Test third item", item1.getItemsNumber() == 1044);
		
		item2.addItem("Test one");
		assertTrue("Test item in item2 after Test one", item2.getItem().equals("Test one"));
		assertTrue("Test joinString in item2 after Test one", item2.getJoinString().length() == 0);
		item2.addItem("Test two");
		assertTrue("Test item in item2 after Test two", item2.getItem().equals("Test two"));
		assertTrue("Test joinString in item2 after Test two - length", item2.getJoinString().length() == 8);
		assertTrue("Test joinString in item2 after Test two - String", item2.getJoinString().toString().equals("Test one"));
		item2.addItem("Test three");
		assertTrue("Test item in item2 after Test three", item2.getItem().equals("Test three"));
		assertTrue("Test joinString in item2 after Test three - length", item2.getJoinString().length() == 18);
		assertTrue("Test joinString in item2 after Test three - String", item2.getJoinString().toString().equals("Test one, Test two"));
	}

}
