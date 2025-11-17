package com.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.internal.invokers.ConfigMethodArguments;

import com.api.utils.ConfigManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariCPDemo {
	
	public static void main(String[] args) throws SQLException {
		
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setJdbcUrl(ConfigManager.getProperty("DB_URL"));
		hikariConfig.setUsername(ConfigManager.getProperty("DB_USER"));
		hikariConfig.setPassword(ConfigManager.getProperty("DB_PASSWORD"));
		hikariConfig.setMaximumPoolSize(10);
		hikariConfig.setMinimumIdle(2);
		hikariConfig.setConnectionTimeout(10000); //10 sec
		hikariConfig.setIdleTimeout(10000);
		hikariConfig.setMaxLifetime(1800000); // 30*60*1000
		hikariConfig.setPoolName("Phoniex Test Automation Framework Pool");
		
		HikariDataSource ds = new HikariDataSource(hikariConfig);
		Connection conn = ds.getConnection();
		Statement statement= conn.createStatement();
		
		ResultSet result = statement.executeQuery("SELECT first_name,last_name ,mobile_number from tr_customer;");
		
		while(result.next()) {
			
			System.out.println(result.getString("first_name"));
		}
		
		ds.close();
	}

}
