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

	public TreeMap<String, Integer> getItemOccurrences() {
		return itemOccurrences;
	}

	public void setItemOccurrences(TreeMap<String, Integer> itemOccurrences) {
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
			Set<String> s = itemOccurrences.keySet();
			Iterator<String> iter = s.iterator();
			while(iter.hasNext())
			{
				positionMap.put(iter.next(), Integer.valueOf(i));
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
		//int power = (positionMapSize - itemNum) - 1;
		int power = itemNum;
		return items | (long)Math.pow(2, power);
	}
	
	public long addItemTest(long items, int itemNum)
	{
		return addItem(items, itemNum);
	}
	
	private float support, confidence;
	private int setSize;
	private String file;
	private FileHandler handler;
	private TreeMap<String, Integer> itemOccurrences;
	private Hashtable<String, Integer> positionMap;
	private Hashtable<String, LinkedList<String>> itemTable;
	private Long[][] itemSetTable;
}
