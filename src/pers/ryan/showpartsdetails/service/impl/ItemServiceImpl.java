package pers.ryan.showpartsdetails.service.impl;

import java.util.ArrayList;
import java.util.List;

import pers.ryan.showpartsdetails.dao.ItemDataFeed;
import pers.ryan.showpartsdetails.domain.Item;
import pers.ryan.showpartsdetails.domain.Supplier;
import pers.ryan.showpartsdetails.service.ItemService;

public class ItemServiceImpl implements ItemService {

	private List<ItemDataFeed> itemDataFeeds;

	public void setItemDataFeeds(List<ItemDataFeed> itemDataFeeds) {
		this.itemDataFeeds = itemDataFeeds;
	}

	@Override
	public Item findItemById(String id) {
		Item item = new Item();
		for (ItemDataFeed itemDataFeed : itemDataFeeds) {
			String supplierName = itemDataFeed.getClass().getName().split("ItemDataFeed")[1];
			supplierName = supplierName.substring(0, supplierName.length() - 4);
			if ("My".equals(supplierName)) {
				item = itemDataFeed.findItemById(id);
			} else {
				Item supplierItem = itemDataFeed.findItemById(id);
				if (supplierItem != null) {
					item.getPrices().putAll(supplierItem.getPrices());
					item.getStockStatus().putAll(supplierItem.getStockStatus());
				}
			}
		}
		return item;
	}

	@Override
	public List<Item> findItemsByIds(String ids) {
		List<Item> ret = new ArrayList<Item>();
		String[] idArray = parseId(ids);
		for (String id : idArray) {
			Item item = findItemById(id);
			if (item != null) {
				ret.add(item);
			}
		}

		return ret;
	}

	private String[] parseId(String _partsId) {
		String[] ret = _partsId.split("[\n,]");
		for (int i = 0; i < ret.length; i++) {
			ret[i] = ret[i].trim();
		}
		return ret;
	}

}
