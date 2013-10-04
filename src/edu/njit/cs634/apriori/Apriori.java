package edu.njit.cs634.apriori;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

public class Apriori {
	
	public Apriori()
	{
		this.setSize = 1;
		this.support = 0;
		this.confidence = 0;
		this.file = null;
		this.handler = null;
		this.itemOccurrences = null;
		this.positionMap = null;
		this.positionMapSize = 0;
	}

	public void setPositionMap()
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
			positionMapSize = positionMap.size();
		}
	}
	
	private float support, confidence;
	private int setSize;
	private String file;
	private FileHandler handler;
	private TreeMap<String, Integer> itemOccurrences;
	private Hashtable<String, Integer> positionMap;
	private int positionMapSize;
}
