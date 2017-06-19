package pers.ryan.showpartsdetails.dao.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import org.hibernate.jdbc.Work;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import pers.ryan.showpartsdetails.dao.ItemDataFeed;
import pers.ryan.showpartsdetails.domain.Item;
import pers.ryan.showpartsdetails.domain.Supplier;

public class ItemDataFeedMyImpl extends HibernateDaoSupport implements ItemDataFeed {

	@Override
	public Item findItemById(String myItemId) {
		HibernateTemplate hibernateTemplat = this.getHibernateTemplate();
		Item item = hibernateTemplat.get(Item.class, myItemId);
		return item;
	}

	@Override
	public void save(Item item) {
		HibernateTemplate hibernateTemplat = this.getHibernateTemplate();
		hibernateTemplat.save(item);
	}

}
