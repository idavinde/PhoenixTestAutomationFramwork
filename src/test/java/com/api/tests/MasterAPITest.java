package com.api.tests;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import com.api.constants.Role;
import com.api.utils.AuthTokenProvider;
import com.api.utils.ConfigManager;

import io.restassured.http.ContentType.*;
import io.restassured.module.jsv.JsonSchemaValidator;

import static io.restassured.RestAssured.*;

public class MasterAPITest {
	
	@Test
	public void verifyMasterAPIResponse() {
		
		given()
			.baseUri(ConfigManager.getProperty("BASE_URI"))
			.and()
			.header("Authorization" , AuthTokenProvider.getToken(Role.FD))
			.and()
			.contentType("")
			.log().all()
			.when()
			.post("master")
			.then()
			.log().all()
			.statusCode(200)
			.time(lessThan(2000L)) // time should be less than 500ms
			.body("message", equalTo("Success"))
			.body("data", notNullValue())
			.body("data", hasKey("mst_oem"))
			.body("$", hasKey("message"))
			.body("$", hasKey("data"))
			.body("data.mst_oem.size()", equalTo(2))
			.body("data.mst_model.size()", greaterThan(0))
			.body("data.mst_oem.id", everyItem(notNullValue()))
			.body("data.mst_oem.name", everyItem(notNullValue()))
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/MasterAPIResponseSchema"));
		
	}
	
	@Test
	public void invalidTokenMasterAPI() {
		
		given()
		.baseUri(ConfigManager.getProperty("BASE_URI"))
		.and()
		.header("Authorization" ," ")
		.and()
		.contentType("")
		.log().all()
		.when()
		.post("master")
		.then()
		.log().all()
		.statusCode(401);
	}

}
