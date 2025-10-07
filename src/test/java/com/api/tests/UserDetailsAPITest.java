package com.api.tests;


import java.io.IOException;

import org.testng.annotations.Test;



import static com.api.constants.Role.*;

import static com.api.utils.SpecUtil.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

import static io.restassured.RestAssured.*;

public class UserDetailsAPITest {
	
	@Test (description="Verify if the Userdetails API response is shown correctly", groups= {"api","regression","smoke"})
	public void userDetailsAPIRequest() throws IOException{
		
		given()
			.spec(requestSpecWithAuth(FD))
		.when()
			.get("userdetails")
		.then()
			.spec(responsSpec_OK())
		.and()
			.body(matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"));
			
	}
}
