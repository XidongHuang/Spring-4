package com.tony.spring.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("bookShopService")
public class BookShopServiceImpl implements BookShopService {

	@Autowired
	private BookShopDao bookshopDao;
	
	
	//Use "propagation" to specific transaction's transportation
	//1. Default is "REQUIRED", all transactions use same case
	//"REQUIRES_NEW": every transaction use itself new case
	//2. Use "isolation" to specific the isolation level, usually use "READ_COMMITTED"
	//3. In default, Spring set all transaction to be "roolback" when exception is thrown. But can specific classes to be roll back
	//4. "readOnly" specific transaction whether "readOnly". 
	//It means the transaction is not update database, but only for reading, which help to optimize database.
	//If it is a method for only reading, then set <readOnly=true>
	//5. Use "timeout" to specific how long for "roolback" can occur 
	@Transactional(propagation=Propagation.REQUIRES_NEW, 
			isolation=Isolation.READ_COMMITTED, 
			readOnly=false,
			timeout=3)
	@Override
	public void purchase(String username, String isbn) {

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//1. Gain book's price
		int price = bookshopDao.findBookPriceByIsbn(isbn);
		
		//2. Update books' amount
		bookshopDao.updateBookStock(isbn);
		
		//3. update users' balance
		bookshopDao.updateUserAccount(username, price);
		
		
	}

}
