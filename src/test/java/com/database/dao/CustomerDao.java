package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.database.DatabaseManager;
import com.database.model.CustomerDBModel;

public class CustomerDao {

	private static final  String CUSTOMER_DETAIL_QUERY=""" 
			SELECT * from tr_customer where id= ?
			""";
	
	private CustomerDao() {}
	
	
	public static CustomerDBModel  getCustomerInfo(int customerID)  {
		Connection conn;
		CustomerDBModel customerDBModel = null;
		PreparedStatement prepstatement;
		ResultSet resultSet;
		
		try {
			conn = DatabaseManager.getConnection();
			prepstatement = conn.prepareStatement(CUSTOMER_DETAIL_QUERY);
			
			prepstatement.setInt(1, customerID);
		    resultSet = prepstatement.executeQuery();
			
			
			while(resultSet.next()) {
				
			   customerDBModel = new CustomerDBModel(
					   resultSet.getInt("id"), 
					  resultSet.getString("first_name"), 
					  resultSet.getString("last_name"), 
					  resultSet.getString("mobile_number"), 
					  resultSet.getString("mobile_number_alt"), 
					  resultSet.getString("email_id"), 
					  resultSet.getString("email_id_alt"),
					  resultSet.getInt("tr_customer_address_id")); 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return customerDBModel;
		
		
	}

}
