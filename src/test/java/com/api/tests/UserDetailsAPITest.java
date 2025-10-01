package com.api.tests;


import java.io.IOException;

import org.testng.annotations.Test;



import static com.api.constants.Role.*;

import com.api.utils.SpecUtil;
import io.restassured.module.jsv.JsonSchemaValidator;

import static io.restassured.RestAssured.*;

public class UserDetailsAPITest {
	
	@Test
	public void userDetailsAPIRequest() throws IOException{
		
		given()
			.spec(SpecUtil.requestSpecWithAuth(FD))
		.when()
			.get("userdetails")
		.then()
			.spec(SpecUtil.responsSpec_OK())
		.and()
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"));
			
	}
}
