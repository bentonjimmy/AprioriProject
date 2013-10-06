package edu.njit.cs634.apriori;

import java.util.Comparator;

public class ItemList implements Comparable<ItemList>, Cloneable{

	private StringBuffer joinString;
	private String item;
	private Long itemsNumber;
	
	public ItemList()
	{
		joinString = new StringBuffer();
		item = null;
		itemsNumber = 0L;
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
	public Object clone()
	{
		ItemList theClone = new ItemList();
		theClone.item = new String(this.item);
		theClone.itemsNumber = this.itemsNumber;
		theClone.joinString = new StringBuffer(this.joinString);
		return theClone;
	}
	
	@Override
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
	
	public void addItem(String item, int itemPos)
	{
		addItem(item);
		addItem(itemPos);
	}
	
	public void addItem(String item)
	{
		if(this.item == null) //No item in this object yet
		{
			setItem(item);
		}
		else if(this.item != null && joinString.length() == 0)
		{
			joinString.append(this.item);
			setItem(item);
		}
		else //There is already one item
		{
			joinString.append(", " + this.item);
			setItem(item);
		}
	}
	
	public void addItem(int itemPos)
	{
		this.itemsNumber  = this.itemsNumber | (int)Math.pow(2, itemPos); 
	}

	@Override
	public int compareTo(ItemList b) 
	{
		if(this.getJoinString().length() == 0 && b.getJoinString().length() == 0)
		{
			return this.getItem().compareToIgnoreCase(b.getItem());
		}
		else if(this.getJoinString().length() == 0 || b.getJoinString().length() == 0)
		{
			return this.getJoinString().toString().compareToIgnoreCase(b.getJoinString().toString());
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
