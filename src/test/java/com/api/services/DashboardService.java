package com.api.services;

import static com.api.constants.Role.FD;
import static com.api.utils.SpecUtil.requestSpec;
import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static io.restassured.RestAssured.given;

import com.api.constants.Role;

import io.restassured.response.Response;

public class DashboardService {
	
	private static final String COUNT_ENDPOINT= "/dashboard/count";
	
	public Response count(Role role) {
		
		return given()
		.spec(requestSpecWithAuth(role))
		.when()
		.get("/dashboard/count");
	}
	
public Response countWithNoAuth( ) {
		
		return given()
				.spec(requestSpec())
		.when()
		.get("/dashboard/count");
	}
	
}
