package com.tony.spring.tx.xml;

import org.springframework.jdbc.core.JdbcTemplate;

public class BookShopImpl implements BookShopDao {

	private JdbcTemplate jdbcTemplate;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public int findBookPriceByIsbn(String isbn) {

	
		
		String sql = "SELECT price FROM book WHERE isbn = ?";
		
		return jdbcTemplate.queryForObject(sql, Integer.class, isbn);
	}

	@Override
	public void updateBookStock(String isbn) {

		//Check the book stock whether enough, if not, then throw an exception
		
		String sql2 = "SELECT stock FROM book_stock WHERE isbn = ?";
		int stock = jdbcTemplate.queryForObject(sql2, Integer.class, isbn);
		if(stock == 0){
			
			throw new BookStockException("Not enough books");
			
		}
		
		
		String sql = "UPDATE book_stock SET stock = stock -  1 WHERE isbn = ?";
		
		jdbcTemplate.update(sql, isbn);
		
	}

	@Override
	public void updateUserAccount(String username, int price) {

		String sql2 = "SELECT balance FROM account WHERE username = ?";
		
		int balance = jdbcTemplate.queryForObject(sql2, Integer.class, username);
		System.out.println(balance < 80);
		
		if(balance < 80){
			
			throw new UserAccountException("Not enough money");
		}
		
		
		String sql = "UPDATE account SET balance = balance - ? WHERE username = ?";
		
		jdbcTemplate.update(sql, price, username);
				
				
		
	}

}
