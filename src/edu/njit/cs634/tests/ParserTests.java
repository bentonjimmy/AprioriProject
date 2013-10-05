package edu.njit.cs634.tests;

import java.util.LinkedList;
import java.util.TreeMap;

import edu.njit.cs634.apriori.Parser;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ParserTests{
	
	private Parser parser;

	@Before
	public void setUp() throws Exception {
		parser = new Parser();
	}

	@Test
	public void testParseRow()
	{
		String[] s;
		s = parser.parseRow("This\tis\ttest\tone", "\t");
		assertTrue("Test one size check", s.length == 4);
		assertTrue("Test one occurrence table size", parser.getOccurrenceTable().isEmpty() == true);
		
		s = parser.parseRow("This\tis|test|two", "\t");
		assertTrue("Test two size check", s.length == 2);
		assertTrue("Test two occurrence table size", parser.getOccurrenceTable().isEmpty() == true);
		
		s = parser.parseRow("This|is|test|three", "\t");
		assertTrue("Test three size check", s.length == 1);
		assertTrue("Test three occurrence table size", parser.getOccurrenceTable().isEmpty() == true);
	}
	
	public void testParseItems()
	{
		LinkedList<String> s;
		s = parser.parseItems("This\tis\ttest\tone", "\t");
		assertTrue("Test one size check", s.size() == 4);
		assertTrue("Test one occurrence table size", parser.getOccurrenceTable().size() == 4);
		
		s = parser.parseItems("This\tis|test|two", "\t");
		assertTrue("Test two size check", s.size() == 2);
		assertTrue("Test two occurrence table size", parser.getOccurrenceTable().size() == 5);
		
		s = parser.parseItems("This|is|test|three", "\t");
		assertTrue("Test three size check", s.size() == 1);
		assertTrue("Test three occurrence table size", parser.getOccurrenceTable().size() == 6);
		
		TreeMap<String, Integer> tm = parser.getOccurrenceTable();
		assertTrue("Occurrence Table size check", tm.size() == 6);
		assertTrue("Check count: This", tm.get("This") == 2);
		assertTrue("Check count: is", tm.get("is") == 1);
		assertTrue("Check count: test", tm.get("test") == 1);
		assertTrue("Check count: one", tm.get("one") == 1);
		assertTrue("Check count: is|test|two", tm.get("is|test|two") == 1);
		assertTrue("Check count: This|is|test|three", tm.get("This|is|test|three") == 1);
	}
	
	

}
