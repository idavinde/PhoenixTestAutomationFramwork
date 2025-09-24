package com.api.tests;

import static io.restassured.RestAssured.*;


import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import com.api.constants.Role;
import static com.api.utils.AuthTokenProvider.*;
import static com.api.utils.ConfigManager.*;

import io.restassured.module.jsv.JsonSchemaValidator;

@Test
public class CountAPITest {

	public void verifyCountAPIResponse() {
			
		given()
			.baseUri(getProperty("BASE_URI"))
		.and()
			.header("Authorization", getToken(Role.FD))
			.log().uri()
			.log().method()
			.log().headers()
			.when()
			.get("/dashboard/count")
			.then()
			.log().all()
			.statusCode(200)
			.body("message", equalTo("Success"))
			.time(lessThan(5000L))   // Time should be less than 500ms 
			.body("data", notNullValue())
			
			.body("data.size()", equalTo(3))  
			.body("data.count", everyItem(greaterThanOrEqualTo(0)))
			.body("data.label", everyItem(not(blankOrNullString())))
			.body("data.key", containsInAnyOrder("pending_for_delivery","created_today","pending_fst_assignment"))
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/CountAPIResponseSchema.json"));
		}
	
	@Test
	public void countAPI_MisiingAuthToken() {
		
		given()
			.baseUri(getProperty("BASE_URI"))
			.and()
			.log().uri()
			.log().method()
			.log().headers()
			.when()
			.get("/dashboard/count")
			.then()
			.log().all()
			.statusCode(401);
	}
	
	
}
