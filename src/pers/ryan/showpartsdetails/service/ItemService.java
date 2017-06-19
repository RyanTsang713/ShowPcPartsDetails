package pers.ryan.showpartsdetails.service;

import java.util.List;

import pers.ryan.showpartsdetails.domain.Item;

public interface ItemService {

	Item findItemById(String id);
	
	List<Item> findItemsByIds(String ids);
}
