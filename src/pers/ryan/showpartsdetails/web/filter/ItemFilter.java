package pers.ryan.showpartsdetails.web.filter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import pers.ryan.showpartsdetails.domain.Item;
import pers.ryan.showpartsdetails.domain.Store;

/**
 * Servlet Filter implementation class ItemFilter
 */
public class ItemFilter implements Filter {

	private Properties pp = new Properties();

	private boolean onlyShowInStockStore = false;

	private Set<Store> onlyShowTheseStores = null;

	private boolean showAllStores = false;

	public ItemFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		List<Item> items = (List<Item>) request.getAttribute("items");
		if (items != null) {
			Iterator<Item> iterator = items.iterator();
			while (iterator.hasNext()) {
				Item currentItem = iterator.next();
				currentItem = filterItem(currentItem);
			}
		}
		request.setAttribute("items", items);

		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		String file = fConfig.getInitParameter("file");
		String realPath = fConfig.getServletContext().getRealPath(file);
		try {
			pp.load(new FileInputStream(realPath));
		} catch (Exception e) {
			fConfig.getServletContext().log("Fail to load myApplication.properties。", e);
		}

		if (pp.get("onlyShowInStockStore") != null) {
			onlyShowInStockStore = Boolean.parseBoolean(pp.get("onlyShowInStockStore").toString());
		}

		String sOnlyShowTheseStores = pp.getProperty("onlyShowTheseStores");
		if (sOnlyShowTheseStores != null) {
			if ("all".equals(sOnlyShowTheseStores)) {
				showAllStores = true;
			} else {
				String sStores = pp.getProperty("onlyShowTheseStores");
				String[] sStoresArray = sStores.split(",");
				onlyShowTheseStores = new HashSet<Store>();

				for (String sStore : sStoresArray) {
					Store store = new Store();
					store.setName(sStore);
					onlyShowTheseStores.add(store);
				}
			}
		}
	}

	private Item filterItem(Item item) {
		if (!showAllStores || onlyShowInStockStore) {
			if (item.getStockStatus() != null && item.getStockStatus().size() > 0) {
				Iterator<Entry<Store, String>> iterator = item.getStockStatus().entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<Store, String> entry = iterator.next();
					if (!onlyShowTheseStores.contains(entry.getKey()) || !"In Stock".equals(entry.getValue())) {
						iterator.remove();
					}
				}
			}
		}
		return item;
	}

}
