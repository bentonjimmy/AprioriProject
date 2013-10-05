package edu.njit.cs634.apriori;

import java.util.Comparator;

public class ItemList implements Comparator<ItemList>{

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
	public int compare(ItemList a, ItemList b) {
		if(a.getJoinString().length() == 0 && b.getJoinString().length() == 0)
		{
			return a.getItem().compareToIgnoreCase(b.getItem());
		}
		else if(a.getJoinString().length() == 0 || b.getJoinString().length() == 0)
		{
			return a.getJoinString().toString().compareToIgnoreCase(b.getJoinString().toString());
		}
		else
		{
			int jsComp = a.getJoinString().toString().compareToIgnoreCase(b.getJoinString().toString());
			if(jsComp == 0)
			{
				return a.getItem().compareToIgnoreCase(b.getItem());
			}
			else
			{
				return jsComp;
			}
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
	

}
