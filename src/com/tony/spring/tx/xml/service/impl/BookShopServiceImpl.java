package com.tony.spring.tx.xml.service.impl;

import com.tony.spring.tx.xml.BookShopDao;
import com.tony.spring.tx.xml.service.BookShopService;

public class BookShopServiceImpl implements BookShopService {

	private BookShopDao bookshopDao;
	
	public void setBookshopDao(BookShopDao bookshopDao) {
		this.bookshopDao = bookshopDao;
	}

	@Override
	public void purchase(String username, String isbn) {


		
		//1. Gain book's price
		int price = bookshopDao.findBookPriceByIsbn(isbn);
		
		//2. Update books' amount
		bookshopDao.updateBookStock(isbn);
		
		//3. update users' balance
		bookshopDao.updateUserAccount(username, price);
		
		
	}

}
