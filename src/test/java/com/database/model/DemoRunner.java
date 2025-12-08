package com.database.model;

import com.database.dao.CustomerProductDao;

public class DemoRunner {
	public static void main(String[] args) {
			
		CustomerProductDBModel customerProductDBModel = CustomerProductDao.getProductInfoFromDB(123704);
		System.out.println(customerProductDBModel);
	}
}
