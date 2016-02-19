package com.tony.spring.tx;

public interface BookShopDao {
	
	//Gain price base on book id
	public int findBookPriceByIsbn(String isbn);
	
	//update books number. 
	public void updateBookStock(String isbn);
	
	//update user account
	public void updateUserAccount(String username, int price);
	
	
	
	
}
