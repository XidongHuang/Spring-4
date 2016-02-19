package com.tony.spring.tx;

import java.util.List;

public interface Cashier {

	public void checout(String username, List<String> isbns);
}
