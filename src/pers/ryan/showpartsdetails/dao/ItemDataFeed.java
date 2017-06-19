package pers.ryan.showpartsdetails.dao;

import pers.ryan.showpartsdetails.domain.Item;

public interface ItemDataFeed {

	public Item findItemById(String myItemId);
	
	public void save(Item item);
}
