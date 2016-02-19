package com.tony.spring.tx.xml;

public interface BookShopDao {
	
	//Gain price base on book id
	public int findBookPriceByIsbn(String isbn);
	
	//update books number. 
	public void updateBookStock(String isbn);
	
	//update user account
	public void updateUserAccount(String username, int price);
	
	
	
	
}
