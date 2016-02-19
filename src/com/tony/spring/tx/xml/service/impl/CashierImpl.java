package com.tony.spring.tx.xml.service.impl;

import java.util.List;

import com.tony.spring.tx.xml.service.BookShopService;
import com.tony.spring.tx.xml.service.Cashier;


public class CashierImpl implements Cashier {

	private BookShopService bookShopService;
	
	public void setBookShopService(BookShopService bookShopService) {
		this.bookShopService = bookShopService;
	}
	
	
	@Override
	public void checout(String username, List<String> isbns) {

		for(String isbn: isbns){
			
			bookShopService.purchase(username, isbn);
			
		}
		
	}

}
