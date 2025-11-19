package com.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.api.utils.ConfigManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DemoRunner_CheckingOfHikariDatabaseManager {
	
	public static void main(String[] args) throws SQLException {
		
		System.out.println(DatabaseManager.getConnection());
	}

}
