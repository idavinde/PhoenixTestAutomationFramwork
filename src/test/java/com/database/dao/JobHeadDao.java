package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.database.DatabaseManager;
import com.database.model.JobHeadModel;

public class JobHeadDao {
	private static final String JOB_HEAD_QUERY = """
			SELECT * from tr_job_head tjh
			where tr_customer_id= ?
			""";

	private JobHeadDao() {

	}

	public static JobHeadModel getDataFromJobHead(int tr_customer_id) {
		Connection conn;
		PreparedStatement preparedStatement;
		ResultSet resultSet;

		JobHeadModel jobHeadModel = null;

		try {

			conn = DatabaseManager.getConnection();
			preparedStatement = conn.prepareStatement(JOB_HEAD_QUERY);
			preparedStatement.setInt(1, tr_customer_id);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				jobHeadModel = new JobHeadModel(resultSet.getString("job_number"), resultSet.getInt("tr_customer_id"),
						resultSet.getInt("tr_customer_product_id"), resultSet.getInt("mst_service_location_id"),
						resultSet.getInt("mst_platform_id"), resultSet.getInt("mst_warrenty_status_id"),
						resultSet.getInt("mst_oem_id"));

			}
		}

		catch (SQLException e) {

			System.err.println(e.getMessage());
		}

		return jobHeadModel;
	}
}
