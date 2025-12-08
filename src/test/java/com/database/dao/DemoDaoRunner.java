package com.database.dao;

import java.sql.SQLException;

import com.database.model.CustomerDBModel;

public class DemoDaoRunner {
	public static void main(String[] args) throws SQLException {
		CustomerDBModel customerDBModel=  CustomerDao.getCustomerInfo(111648);
		System.out.println(customerDBModel);
	}
}
