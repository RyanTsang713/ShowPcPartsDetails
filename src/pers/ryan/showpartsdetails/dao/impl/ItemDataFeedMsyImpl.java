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

public class ItemDataFeedMsyImpl extends HibernateDaoSupport implements ItemDataFeed {

	private Supplier supplier = new Supplier();

	{
		supplier.setName("Msy");
	}

	@Override
	public Item findItemById(String myItemId) {
		String msyIndex = "";
		Item item = new Item();
		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();
		String sql = "SELECT msy_item_index FROM msy_items WHERE item_id = :id";
		SQLQuery query = this.getSession().createSQLQuery(sql);
		query.setParameter("id", myItemId);
		List result = query.list();
		if (result != null && result.size() > 0) {
			msyIndex = result.get(0).toString();
		} else {
			return null;
		}

		try {
			Document document = Jsoup.connect("http://www.msy.com.au/qldonline/" + msyIndex).get();
			String stringPrice = document.getElementById("our_price_display").text();
			Double price = parsePrice(stringPrice);
			item.getPrices().put(supplier, price);
			
			Elements stockElements = document.getElementById("idTabStock").getElementsByTag("tr");
			
			for(Element element : stockElements){
				Map<Store, String> map = parseStockStatus(element);
				if(map != null && map.size() > 0){
					item.getStockStatus().putAll(map);
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

	private Map<Store, String> parseStockStatus(Element element) {
		// <tr>
		// <td>NSW_Ultimo</td>
		// <td style="background-color:silver;color:green;">
		// <strong>In Stock</strong>
		// </td>
		// </tr>
		Map<Store, String> ret = new HashMap<Store, String>();
		String[] ssStore = element.child(0).text().split("_");
		String sStockStatus = element.child(1).text();

		String sRegion = ssStore[0];

		if ("QLD".equals(sRegion)) {
			String sStoreName = ssStore[1];
			Store store = new Store();
			store.setName("MSY:" + sStoreName);
			ret.put(store, sStockStatus);
		} else {
			return null; 
		}
		return ret;
	}

	@Override
	public void save(Item item) {
		// TODO Auto-generated method stub
		
	}
}
