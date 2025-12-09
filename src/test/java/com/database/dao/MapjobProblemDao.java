package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sound.midi.SysexMessage;

import com.database.DatabaseManager;
import com.database.model.MapJobProblemModel;

public class MapjobProblemDao {
	
	private static final String PROBLEM_QUERY= """
			Select * from map_job_problem where
			tr_job_head_id= ?;
			""" ;
	
	private MapjobProblemDao() {
		
	}
	
	public static MapJobProblemModel getProblemDetails(int tr_job_head_id) {
		Connection conn;
		PreparedStatement preparedStatement;
		MapJobProblemModel mapJobProblemModel = null;
		try {
			conn = DatabaseManager.getConnection();
			preparedStatement= conn.prepareStatement(PROBLEM_QUERY);
			preparedStatement.setInt(1, tr_job_head_id);
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				
				mapJobProblemModel = new MapJobProblemModel(rs.getInt("id"), rs.getInt("tr_job_head_id"), rs.getInt("mst_problem_id"), rs.getString("remark"));
			}
		}
		catch(SQLException e) {
			
			System.err.println(e.getMessage());
		}
		
		return mapJobProblemModel;
	}
}
