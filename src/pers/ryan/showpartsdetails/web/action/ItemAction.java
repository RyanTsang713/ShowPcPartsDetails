package pers.ryan.showpartsdetails.web.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import pers.ryan.showpartsdetails.domain.Item;
import pers.ryan.showpartsdetails.service.ItemService;

public class ItemAction extends ActionSupport implements ModelDriven<Item> {

	private Item item = new Item();
	List<Item> items;
	private ItemService itemService;
	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}
	
	private String partsId;
	public void setPartsId(String partsId) {
		this.partsId = partsId;
	}
	
	@Override
	public Item getModel() {
		return item;
	}
	
	public String findItem(){
		item = itemService.findItemById(partsId);
		items.add(item);
//		Map request = (Map) ActionContext.getContext().get("request");
//		request.put("items", items);
		return SUCCESS;
	}
	
	public String findItems(){
		items = itemService.findItemsByIds(partsId);
		//Map request = (Map) ActionContext.getContext().get("request");
		//request.put("items", items);
		return SUCCESS;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	
}
