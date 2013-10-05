package edu.njit.cs634.tests;

import org.junit.Test;
import edu.njit.cs634.apriori.ItemList;
import static org.junit.Assert.*;
import org.junit.Before;


public class ItemListTests {
	
	ItemList item;

	@Before
	public void setUp() throws Exception {
		item = new ItemList();
	}

	@Test
	public void testCompare() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testAddItem() {
		assertTrue("Test it is set to 0", item.getItemsNumber() == 0);
		item.addItem(2);
		assertTrue("Test first item", item.getItemsNumber() == 4);
		item.addItem(4);
		assertTrue("Test second item", item.getItemsNumber() == 20);
		item.addItem(4);
		assertTrue("Should not cause change", item.getItemsNumber() == 20);
		item.addItem(10);
		assertTrue("Test third item", item.getItemsNumber() == 1044);
	}

}
