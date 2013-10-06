package edu.njit.cs634.apriori;

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
		this.commonItems = new TreeMap<ItemList, Integer>();
		this.itemTable = null;
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

	public TreeMap<ItemList, Integer> getCommonItems() {
		return commonItems;
	}

	public void setCommonItems(TreeMap<ItemList, Integer> commonItems) {
		this.commonItems = commonItems;
	}

	public void parseFile()
	{
		if(handler.getFilePath() != null)
		{
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
				positionMap.put(iter.next().getItem(), Integer.valueOf(i));
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
			if(i.intValue() < support) //This may need to be changed
			{
				//Remove anything under support
				nonCommonItems.put(items, "No");
				//itemOccurrences.remove(items);
				iter.remove();
			}
			else
			{
				//Make note of common sets so far
				commonItems.put(items, i);
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
					int posi = positionMap.get(keys[i].getItem());
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
	
	private float support, confidence;
	private int setSize;
	private String file;
	private FileHandler handler;
	private TreeMap<ItemList, Integer> itemOccurrences;
	private Hashtable<ItemList, String> nonCommonItems;
	private TreeMap<ItemList, Integer> commonItems;
	private Hashtable<String, Integer> positionMap;
	private Hashtable<String, LinkedList<String>> itemTable;
	private Long[][] itemSetTable;
}
