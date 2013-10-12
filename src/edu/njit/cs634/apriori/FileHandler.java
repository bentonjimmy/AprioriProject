package edu.njit.cs634.apriori;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.LinkedList;

/**
 * This class will handle the parsing of the given file.  By default the delimiter between
 * Transaction ID and the items is a tab and the delimiter between items is a pipe (|).
 * @author Jim Benton
 *
 */
public class FileHandler{

	private String filePath;
	private String rowDelim, itemDelim;
	private Hashtable<String, LinkedList<String>> table;
	private Parser parser;
	
	public FileHandler()
	{
		filePath = null;
		table = new Hashtable<String, LinkedList<String>>();
		parser = new Parser();
		rowDelim = "\t";
		itemDelim = "|";
	}
	
	public FileHandler(String filePath)
	{
		this();
		this.filePath = filePath;
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getRowDelim() {
		return rowDelim;
	}

	public void setRowDelim(String rowDelim) {
		this.rowDelim = rowDelim;
	}

	public String getItemDelim() {
		return itemDelim;
	}

	public void setItemDelim(String itemDelim) {
		this.itemDelim = itemDelim;
	}

	public Hashtable<String, LinkedList<String>> getTable() {
		return table;
	}

	public void setTable(Hashtable<String, LinkedList<String>> table) {
		this.table = table;
	}

	public Parser getParser() {
		return parser;
	}

	public void setParser(Parser parser) {
		this.parser = parser;
	}

	/**
	 * Reads a file and parses it based off of the row delimiter and item delimiters
	 * that have been provided.  By default the row delimiter is set to <code>\t</code> and the item delimiter is set to <code>|</code>.  
	 * The data from the file fills a <code>HashTable</code> with the
	 * transaction ID as the <code>key</code> and a <code>Linked List</code> of the items as the 
	 * <code>value</code>.
	 * 
	 * @return A Hashtable<String, LinkedList<String>> containing the transaction ID as the key and a linked list of the items
	 * as the values.
	 */
	public Hashtable<String, LinkedList<String>> readFile()
	{
		if(filePath != null)
		{
			BufferedReader br;
			try 
			{
				String line;
				String key;
				LinkedList<String> list;
				String[] stringArray;
				br = new BufferedReader(new FileReader(filePath));
				//Loop while there is data to read
				while((line = br.readLine()) != null)
				{
					//Parses the transaction ID from the items in it
					stringArray = parser.parseRow(line, rowDelim);
					key = stringArray[0];
					//Parses the items in the transactions
					list = parser.parseItems(stringArray[1], itemDelim);
					table.put(key, list);
				}
				
				return table;
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return null;
	}
	
}

