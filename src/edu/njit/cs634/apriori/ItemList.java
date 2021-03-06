package edu.njit.cs634.apriori;

import java.util.Comparator;

/**
 * This class represents the list of items
 * @author Jim Benton
 *
 */
public class ItemList implements Comparable<ItemList>, Cloneable{

	private StringBuffer joinString;
	private String item;
	private Long itemsNumber;
	private int size;
	
	public ItemList()
	{
		joinString = new StringBuffer();
		item = null;
		itemsNumber = 0L;
		size = 0;
	}
	
	public int size()
	{
		return size;
	}
	
	public StringBuffer getJoinString() {
		return joinString;
	}

	public void setJoinString(StringBuffer joinString) {
		this.joinString = joinString;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Long getItemsNumber() {
		return itemsNumber;
	}

	public void setItemsNumber(Long itemsNumber) {
		this.itemsNumber = itemsNumber;
	}
	
	@Override
	/**
	 * Creates a copy of the ItemList.
	 */
	public Object clone()
	{
		ItemList theClone = new ItemList();
		theClone.item = new String(this.item);
		theClone.itemsNumber = this.itemsNumber;
		theClone.joinString = new StringBuffer(this.joinString);
		theClone.size = this.size;
		return theClone;
	}
	
	@Override
	/**
	 * Tests the equality of two ItemLists.
	 */
	public boolean equals(Object o)
	{
		if(o instanceof ItemList)
		{
			if(this.compareTo((ItemList)o) == 0)
				return true;
			else
				return false;
		}
		else
			return false;
	}
	
	@Override
	/**
	 * Returns a String representation of the items in the ItemList
	 */
	public String toString()
	{
		if(getJoinString().length() != 0)
		{
			return getJoinString() + ", " + getItem();
		}
		else
		{
			return getItem();
		}
	}
	
	/**
	 * Add an item to the itemList
	 * @param item - the String representation of the item to be added to the ItemList
	 * @param itemPos - the numerical representation of the item to be added to the ItemList
	 */
	public void addItem(String item, int itemPos)
	{
		addItem(item);
		addItem(itemPos);
	}
	
	/**
	 * Adds the item to the ItemList
	 * @param item - the String representation of the item to be added to the ItemList
	 */
	public void addItem(String item)
	{
		if(this.item == null) //No item in this object yet
		{
			setItem(item);
		}
		else if(this.item != null && joinString.length() == 0)//There is 1 item in the list
		{
			joinString.append(this.item);
			setItem(item);
		}
		else //There are several items in the list
		{
			joinString.append(", " + this.item);
			setItem(item);
		}
		addOne();
	}
	
	/**
	 * Adds the item to the ItemList based off of its numerical representation
	 * @param itemPos - the numerical representation of the item to be added to the ItemList
	 */
	public void addItem(int itemPos)
	{
		this.itemsNumber  = this.itemsNumber | (int)Math.pow(2, itemPos); 
	}

	@Override
	/**
	 * This is used to compare two itemLists
	 */
	public int compareTo(ItemList b) 
	{
		//If both joinStrings are empty
		if(this.getJoinString().length() == 0 && b.getJoinString().length() == 0)
		{
			return this.getItem().compareToIgnoreCase(b.getItem());
		}
		//If one joinString is empty
		else if(this.getJoinString().length() == 0 || b.getJoinString().length() == 0)
		{
			return this.getJoinString().toString().compareToIgnoreCase(b.getJoinString().toString());
		}
		//If neither joinString is empty
		else 
		{
			if(this.joinString.length() > b.getJoinString().length())
			{
				return 1;
			}
			else if(this.joinString.length() < b.getJoinString().length())
			{
				return -1;
			}
			else
			{
				int jsComp = this.getJoinString().toString().compareToIgnoreCase(b.getJoinString().toString());
				if(jsComp == 0)
				{
					return this.getItem().compareToIgnoreCase(b.getItem());
				}
				else
				{
					return jsComp;
				}
			}
		}
	}
	
	/**
	 * Tracks the size of the itemList.  This is incremented when a String item is added
	 * to the object.
	 */
	private void addOne()
	{
		size++;
	}

}
