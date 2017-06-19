package pers.ryan.showpartsdetails.domain;

import java.util.LinkedHashMap;
import java.util.Map;

public class Item {

	private String id;
	
	//Rules: Brand + Model number + other feature
	private String name;
	
	private String imgUrl;
	
	private Category category;
	
	private Map<Supplier, Double> prices = new LinkedHashMap<Supplier, Double>();
	
	private Map<Store, String> stockStatus = new LinkedHashMap<Store, String>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Map<Supplier, Double> getPrices() {
		return prices;
	}

	public void setPrices(Map<Supplier, Double> prices) {
		this.prices = prices;
	}

	public Map<Store, String> getStockStatus() {
		return stockStatus;
	}

	public void setStockStatus(Map<Store, String> stockStatus) {
		this.stockStatus = stockStatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
}
