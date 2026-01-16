package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.database.DatabaseManager;
import com.database.model.MapJobProblemModel;

import io.qameta.allure.Step;

public class MapjobProblemDao {
	private static final Logger LOGGER = LogManager.getLogger(MapjobProblemDao.class);
	private static final String PROBLEM_QUERY= """
			Select * from map_job_problem where
			tr_job_head_id= ?;
			""" ;
	
	private MapjobProblemDao() {
		
	}
	
	@Step("Retriving the Problem details Information from DB for the specific job head id")
	public static MapJobProblemModel getProblemDetails(int tr_job_head_id) {
		Connection conn;
		PreparedStatement preparedStatement;
		MapJobProblemModel mapJobProblemModel = null;
		try {
			LOGGER.info("Getting the connection from the Database Manager");
			conn = DatabaseManager.getConnection();
			preparedStatement= conn.prepareStatement(PROBLEM_QUERY);
			preparedStatement.setInt(1, tr_job_head_id);
			LOGGER.info("Executing the SQL Query", PROBLEM_QUERY);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				
				mapJobProblemModel = new MapJobProblemModel(rs.getInt("id"), rs.getInt("tr_job_head_id"), rs.getInt("mst_problem_id"), rs.getString("remark"));
			}
		}
		catch(SQLException e) {
			
			LOGGER.error("Cannot Convert the ResultSet to the  MapJobProblemModel bean", e);
			
			System.err.println(e.getMessage());
		}
		
		return mapJobProblemModel;
	}
}
