package pers.ryan.showpartsdetails.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pers.ryan.showpartsdetails.dao.ItemDataFeed;
import pers.ryan.showpartsdetails.domain.Category;
import pers.ryan.showpartsdetails.domain.Item;
import pers.ryan.showpartsdetails.service.impl.ItemServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TestSSH {

	@Autowired
	private ItemServiceImpl itemServiceImpl;
	
	@Autowired
	private ItemDataFeed myItemDataFeed;
	
	@Test
	public void demo1(){
		String s = "\rs\r";
		s = s.trim();
	}
	
	@Test
	/***
	 * Add new item
	 */
	public void demo2(){
		Item item = new Item();
		item.setId("BX80677I57400");
		item.setName("Intel i5 7400");
		item.setImgUrl("https://cdn.pcpartpicker.com/static/img/vendor-logos/logo_merchant_umart.png");
		item.setCategory(Category.CPU);
		myItemDataFeed.save(item);
	}
}
