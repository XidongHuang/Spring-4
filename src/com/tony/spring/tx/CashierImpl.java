package com.tony.spring.tx;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service("cashier")
public class CashierImpl implements Cashier {

	@Autowired
	private BookShopService bookShopService;
	
	
	
	@Transactional
	@Override
	public void checout(String username, List<String> isbns) {

		for(String isbn: isbns){
			
			bookShopService.purchase(username, isbn);
			
		}
		
	}

}
