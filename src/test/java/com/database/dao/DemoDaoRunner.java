package com.database.dao;

import java.sql.SQLException;

import com.database.model.CustomerDBModel;
import com.database.model.JobHeadModel;

public class DemoDaoRunner {
	public static void main(String[] args) throws SQLException {
		JobHeadModel customerDBModel=  JobHeadDao.getDataFromJobHead(123697);
		System.out.println(customerDBModel);
	}
}
