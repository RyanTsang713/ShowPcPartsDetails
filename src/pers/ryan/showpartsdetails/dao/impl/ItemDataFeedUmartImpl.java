package pers.ryan.showpartsdetails.dao.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import pers.ryan.showpartsdetails.dao.ItemDataFeed;
import pers.ryan.showpartsdetails.domain.Item;
import pers.ryan.showpartsdetails.domain.Store;
import pers.ryan.showpartsdetails.domain.Supplier;

public class ItemDataFeedUmartImpl extends HibernateDaoSupport implements ItemDataFeed {

	private Supplier supplier = new Supplier();

	{
		supplier.setName("Umart");
	}

	@Override
	public Item findItemById(String myItemId) {
		String umartIndex = "";
		Item item = new Item();
		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();
		String sql = "SELECT umart_item_index FROM umart_items WHERE item_id = :id";
		SQLQuery query = this.getSession().createSQLQuery(sql);
		query.setParameter("id", myItemId);
		List result = query.list();
		if (result != null && result.size() > 0) {
			umartIndex = result.get(0).toString();
		} else {
			return null;
		}

		try {
			Document document = Jsoup.connect("http://www.umart.com.au/newsite/goods.php?id=" + umartIndex).get();

			// Get price
			String stringPrice = document.getElementById("ECS_GOODS_AMOUNT").text();
			Double price = parsePrice(stringPrice);
			Map priceMap = new HashMap<Supplier, Double>();
			priceMap.put(supplier, price);
			item.setPrices(priceMap);

			// Get stock status
			Elements citybox_childmenu = document.getElementById("citybox_childmenu").child(1).children();
			for (Element e : citybox_childmenu) {
				// System.out.print(e.getElementsByClass("region_name").text());
				Map stockMap = parseStockStatus(e.getElementsByClass("region_name").text());
				if (stockMap != null) {
					item.getStockStatus().putAll(stockMap);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return item;
	}

	private double parsePrice(String stringPrice) {
		stringPrice = stringPrice.replace("$", "");
		stringPrice = stringPrice.trim();

		return Double.parseDouble(stringPrice);
	}

	private Map<Store, String> parseStockStatus(String region_name) {
		// (QLD) Gold Coast (Out Of Stock)
		String[] ss = region_name.split("[()]");
		Store store = null;
		String stockStatus = null;
		for (int i = 0; i < ss.length; i++) {
			String string = ss[i].trim();
			if ("".equals(string)) {
				continue;
			} else {
				// Use property file to config
				if ("QLD".equals(string)) {
					store = new Store();
					store.setName("Umart:" + ss[i + 1].trim());
					stockStatus = ss[i + 2].trim();
					i += 2;
				} else {
					return null;
				}
			}
		}

		Map<Store, String> ret = new HashMap<Store, String>();
		ret.put(store, stockStatus);
		return ret;
	}

	@Override
	public void save(Item item) {
		// TODO Auto-generated method stub
		
	}

}
