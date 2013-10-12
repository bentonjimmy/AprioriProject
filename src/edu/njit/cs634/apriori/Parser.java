package edu.njit.cs634.apriori;


import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * This class is used to parse the transactions in the given file/database.
 * @author Jim Benton
 *
 */
public class Parser {
	
	public Parser()
	{
		occurrenceTable = new TreeMap<ItemList, Integer>();
	}

	public TreeMap<ItemList, Integer> getOccurrenceTable() {
		return occurrenceTable;
	}

	public void setOccurrenceTable(TreeMap<ItemList, Integer> occurrenceTable) {
		this.occurrenceTable = occurrenceTable;
	}

	/**
	 * Used to parse a line of data.  This is used to parse the transaction ID from the items.
	 * @param line - the String to be parsed
	 * @param delims - the delimiter to parse the data
	 * @return a String array of the parsed line
	 */
	public String[] parseRow(String line, String delims)
	{
		return parse(line, delims, false).toArray(new String[0]);
	}
	
	/**
	 * Used to parse a line of data. This will parse the items in the transaction.
	 * @param line - the items that will be parsed
	 * @param delims - the delimiter to parse the data
	 * @return a Linked List containing the items in the transaction
	 */
	public LinkedList<String> parseItems(String line, String delims)
	{
		return parse(line, delims, true);
	}
	
	/**
	 * Parses the given <code>line</code> using the given delimiter.  If <code>count</code>
	 * is set to <code>true</code> the occurrences of the item will be tracked.
	 * @param line - the line to be parsed
	 * @param delims - the delimiter to use to parse the line
	 * @param count - a boolean used to count item occurrences
	 * @return a Linked List of the individual items contained in the line
	 */
	protected LinkedList<String> parse(String line, String delims, boolean count)
	{
		LinkedList<String> ll = new LinkedList<String>();
		StringTokenizer st = new StringTokenizer(line, delims);
		while(st.hasMoreElements())
		{
			ItemList word = new ItemList();
			word.addItem(st.nextToken());
			//Adds the item to a Linked List that will contain all the items in a transaction
			ll.addLast(word.getItem());
			if(count == true)
			{
				addWordOccurrence(word);
			}
		}
		
		return ll;
	}
	
	/**
	 * Used to track the occurrence of items in the data.  If <code>word</code> is not
	 * found in <code>occurrenceTable</code> then it is added and given a count of 1.  If
	 * it is found then it is incremented by 1.
	 * @param word - the word to be added to the occurrence tracking table
	 */
	protected void addWordOccurrence(ItemList word)
	{
		if(occurrenceTable.get(word) == null)
		{
			//The word is not already in the table
			occurrenceTable.put(word, 1);
		}
		else
		{
			//The word exists in the table
			Integer i = occurrenceTable.get(word);
			i++;
			occurrenceTable.put(word, i);
		}
	}
	
	private TreeMap<ItemList, Integer> occurrenceTable;
	
}
