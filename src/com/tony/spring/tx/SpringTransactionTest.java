package com.tony.spring.tx;


import java.sql.SQLException;
import java.util.Arrays;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTransactionTest {

	private ApplicationContext ctx = null;
	private BookShopDao bookShopDao = null;
	private BookShopService bookShopService = null;
	private Cashier cashier = null;
	
	{
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		bookShopDao = ctx.getBean(BookShopDao.class);
		
		bookShopService = ctx.getBean(BookShopService.class);
		cashier = ctx.getBean(Cashier.class);
	}
	
	
	
	@Test
	public void testBookShopDaoFindPriceByIsbn(){
		
		System.out.println(bookShopDao.findBookPriceByIsbn("1001"));
		
	
	
	}
	
	@Test
	public void testBookShopDaoUpdateBookStock(){
		
		bookShopDao.updateBookStock("1001");
		
		
	}
	
	
	@Test
	public void testBookShopUpdateUserAccount(){
		
		bookShopDao.updateUserAccount("AA", 101);
		
	}
	
	@Test
	public void testBookShopService(){
		
		
		bookShopService.purchase("AA", "1001");
		
	}
	
	
	
	@Test
	public void testTransactionlPropagation(){
		
		cashier.checout("AA", Arrays.asList("1001", "1002"));
		
		
	}
	

}