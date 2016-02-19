package com.tony.spring.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("bookShopService")
public class BookShopServiceImpl implements BookShopService {

	@Autowired
	private BookShopDao bookshopDao;
	
	
	//Use "propagation" to specific transaction's transportation
	//Default is "REQUIRED",
	@Transactional(propagation=Propagation.REQUIRES_NEW)
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
