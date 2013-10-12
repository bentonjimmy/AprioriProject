package edu.njit.cs634.apriori;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeMap;

public class Apriori {
	
	public Apriori()
	{
		this.setSize = 1;
		this.support = 0;
		this.confidence = 0;
		this.file = null;
		this.handler = new FileHandler();
		this.itemOccurrences = null;
		this.positionMap = new Hashtable<String, Integer>();
		this.nonCommonItems = new Hashtable<ItemList, String>();
		this.commonItemsTable = new TreeMap<ItemList, Integer>();
		this.invertedPositionMap = new Hashtable<Long, ItemList>();
		this.itemTable = null;
		this.rules = new ArrayList<String>();
	}
	
	public float getSupport() {
		return support;
	}

	public void setSupport(float support) {
		this.support = support;
	}

	public float getConfidence() {
		return confidence;
	}

	public void setConfidence(float confidence) {
		this.confidence = confidence;
	}

	public int getSetSize() {
		return setSize;
	}

	public void setSetSize(int setSize) {
		this.setSize = setSize;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public FileHandler getHandler() {
		return handler;
	}

	public void setHandler(FileHandler handler) {
		this.handler = handler;
	}

	public TreeMap<ItemList, Integer> getItemOccurrences() {
		return itemOccurrences;
	}

	public void setItemOccurrences(TreeMap<ItemList, Integer> itemOccurrences) {
		this.itemOccurrences = itemOccurrences;
	}

	public Hashtable<String, Integer> getPositionMap() {
		return positionMap;
	}

	public void setPositionMap(Hashtable<String, Integer> positionMap) {
		this.positionMap = positionMap;
	}

	public Hashtable<String, LinkedList<String>> getItemTable() {
		return itemTable;
	}

	public void setItemTable(Hashtable<String, LinkedList<String>> itemTable) {
		this.itemTable = itemTable;
	}

	public Long[][] getItemSetTable() {
		return itemSetTable;
	}

	public void setItemSetTable(Long[][] itemSetTable) {
		this.itemSetTable = itemSetTable;
	}

	public Hashtable<ItemList, String> getNonCommonItems() {
		return nonCommonItems;
	}

	public void setNonCommonItems(Hashtable<ItemList, String> nonCommonItems) {
		this.nonCommonItems = nonCommonItems;
	}

	public TreeMap<ItemList, Integer> getCommonItemsTable() {
		return commonItemsTable;
	}

	public void setCommonItemsTable(TreeMap<ItemList, Integer> commonItems) {
		this.commonItemsTable = commonItems;
	}
	
	public String[] getCommonItems()
	{
		return rules.toArray(new String[0]);
		/*
		String[] items = new String[commonItemsTable.size()];
		int i = 0;
		Set<ItemList> s = commonItemsTable.keySet();
		Iterator<ItemList> iter = s.iterator();
		while(iter.hasNext())
		{
			items[i] = iter.next().toString();
			i++;
		}
		return items;
		*/
	}
	
	/**
	 * This method will create the association rules based off of the given database.  The
	 * method will run until the item set it no longer able to create new lists of items.
	 */
	public void run()
	{
		parseFile();
		createPositionMap();
		createItemSetTable();
		while(getItemOccurrences().size() > 0)
		{
			calculateCommonSets();
		}
		calculateConfidence();
	}

	public void parseFile()
	{
		if(file != null)
		{
			handler.setFilePath(file);
			itemTable = handler.readFile();
			itemOccurrences = handler.getParser().getOccurrenceTable();
		}
	}

	public void createPositionMap()
	{
		if(itemOccurrences != null)
		{
			int i = 0;
			Set<ItemList> s = itemOccurrences.keySet();
			Iterator<ItemList> iter = s.iterator();
			while(iter.hasNext())
			{
				ItemList temp = iter.next();
				positionMap.put(temp.getItem(), Integer.valueOf(i));
				i++;
			}
		}
	}
	
	public void createItemSetTable()
	{
		int width = 1;
		itemSetTable = new Long[itemTable.size()][width];
		String[] keys = itemTable.keySet().toArray(new String[0]);
		for(int i=0; i<itemTable.size(); i++)
		{
			if(width == 1)
			{
				itemSetTable[i][0] = getItemNumbers(keys[i]);
			}
		}
	}
	
	protected Long getItemNumbers(String key)
	{
		long items = 0;
		LinkedList<String> ll = itemTable.get(key);
		Iterator<String> iter = ll.iterator();
		while(iter.hasNext())
		{
			String s = iter.next();
			Integer position = positionMap.get(s);
			items = addItem(items, position.intValue());
		}
		
		return items;
	}
	
	protected long addItem(long items, int itemNum)
	{
		int power = itemNum;
		return items | (long)Math.pow(2, power);
	}
	
	public long addItemTest(long items, int itemNum)
	{
		return addItem(items, itemNum);
	}
	
	public void calculateCommonSets()
	{
		/*
		 * If the setSize is 1 then it's the first time through the item sets.  This means
		 * we have already calculated the number of occurrences for the items.
		 */
		if(setSize == 1) 
		{
			removeUnderThreshold();
		}
		else
		{
			//Loop through each item in itemOccurrences
			Set<ItemList> s = itemOccurrences.keySet();
			Iterator<ItemList> iter = s.iterator();
			while(iter.hasNext())
			{
				ItemList tempIL = iter.next();
				//Check tempIL against each item in itemSetTable
				for(int i=0; i<itemTable.size(); i++)
				{
					//Bitwise AND used to check if the items in tempIL are in the transaction in itemSetTable
					if((tempIL.getItemsNumber() & itemSetTable[i][0]) == tempIL.getItemsNumber()) //Should be changed
					{
						Integer io = itemOccurrences.get(tempIL);
						itemOccurrences.put(tempIL, io + 1);
					}
				}
			}
			//Check which items are above support, etc.
			//Create new item sets
			removeUnderThreshold();
			
		}
	}
	
	protected void removeUnderThreshold()
	{
		ItemList items;
		Set<ItemList> s = itemOccurrences.keySet();
		Iterator<ItemList> iter = s.iterator();
		
		while(iter.hasNext())
		{
			items = iter.next();
			/*
			 * So far the itemsNumber has not been calculated for the items in the first pass.
			 * This will fill in the itemNumber value
			 */
			int itemPosition = positionMap.get(items.getItem());
			items.addItem(itemPosition);
			Integer i = itemOccurrences.get(items);
			float threshold = ((float)i.intValue())/((float)itemTable.size());
			
			if(threshold < support) 
			{
				//Remove anything under support
				nonCommonItems.put(items, "No");
				iter.remove();
			}
			else
			{
				//Make note of common sets so far
				commonItemsTable.put(items, i);
				invertedPositionMap.put(items.getItemsNumber(), items);
			}
		}
		buildNextSet(s);
	}
	
	protected void buildNextSet(Set<ItemList> s)
	{
		TreeMap<ItemList, Integer> tempTM = new TreeMap<ItemList, Integer>();
		
		ItemList[] keys = s.toArray(new ItemList[0]);
		/*
		 * Loop through all keys in the itemOccurrences table to check if they should
		 * be joined up with new items in the next iteration of looking for common item sets
		 */
		for(int i=0; i<keys.length; i++)
		{
			int j = i+1;
			//Check all items greater than the ith key
			while(j < keys.length)
			{
				boolean toAdd = true;//Boolean tracking if the item set is common or not
				//Check if joinStrings are the same
				if(keys[i].getJoinString().toString().equals(keys[j].getJoinString().toString()))
				{
					int posj = positionMap.get(keys[j].getItem());
					//Make temp copy of items that will be joined
					ItemList tempIL = (ItemList) keys[i].clone();
					tempIL.addItem(new String(keys[j].getItem()), posj);
					
					if(setSize > 1)
					{
						//Check if it's a nonCommon set
						Set<ItemList> nonCommon = nonCommonItems.keySet();
						Iterator<ItemList> ncIter = nonCommon.iterator();
						while(ncIter.hasNext() && toAdd==true)
						{
							ItemList nc = ncIter.next();
							/*
							 * Here the values of the ItemsNumber are undergoing a bitwise AND.  If the resulting
							 * value is the same as the non-common item list then those items are in the temp item list
							 * and it cannot be a common item set
							 */
							if((nc.getItemsNumber() & tempIL.getItemsNumber()) == nc.getItemsNumber())
							{
								toAdd = false;
							}
						}
					}
					if(toAdd)//Is not a non-Common Set
					{
						tempTM.put(tempIL, 0);
					}
				}
				j++;
			}
		}
		itemOccurrences = tempTM; //these will be the next values to be tested as being common item sets
		setSize++;
	}
	
	public void calculateConfidence()
	{
		Set<ItemList> s = commonItemsTable.keySet();
		Iterator<ItemList> iterNum = s.iterator();
		while(iterNum.hasNext())
		{
			ItemList numeratorList = iterNum.next();
			if(numeratorList.size() > 1)
			{
				//get the numerator - support_count(A U B)
				int numerator = commonItemsTable.get(numeratorList);
				//Calculate denominators - support_count(A)
				Set<ItemList> s2 = commonItemsTable.keySet();
				Iterator<ItemList> iterDen = s2.iterator();
				ItemList denomList;
				/*
				 * This loop will iterator over all the Common Item sets or until the size of the denominator
				 * set is larger than the numerator set (support_count(A) cannot be smaller than support_count(A U B)).
				 */
				while(iterDen.hasNext() && (denomList = iterDen.next()).size() < numeratorList.size())
				{
					/*
					 * The below if statement checks to see that the denomList items are in the numeratorList items.  The 
					 * bitwise AND is used to check that the denomList items are in the numeratorList items.
					 * If they are in the numeratorList items then we will use the denomList support count to calculate 
					 * the confidence.
					 */
					if((numeratorList.getItemsNumber() & denomList.getItemsNumber()) == denomList.getItemsNumber())
					{
						int denominator = commonItemsTable.get(denomList);
						if(((float)numerator/(float)denominator) >= confidence)
						{
							long bLookup = numeratorList.getItemsNumber() ^ denomList.getItemsNumber();
							String B = invertedPositionMap.get(bLookup).toString();
							String A = denomList.toString();
							rules.add((new Rules(A, B, 
									Math.round(((float)numerator/(float)itemTable.size()) * 100.0), 
									Math.round(((float)numerator/(float)denominator) * 100.0)).toString()));
						}
					}
				}
			}
		}
	}
	
	private float support, confidence;
	private int setSize;
	private String file;
	private FileHandler handler;
	private TreeMap<ItemList, Integer> itemOccurrences;
	private Hashtable<ItemList, String> nonCommonItems;
	private TreeMap<ItemList, Integer> commonItemsTable;
	private Hashtable<String, Integer> positionMap;
	private Hashtable<Long, ItemList> invertedPositionMap;
	private Hashtable<String, LinkedList<String>> itemTable;
	private Long[][] itemSetTable;
	private ArrayList<String> rules;
}
