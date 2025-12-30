package com.api.tests;


import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.services.AuthService;
import com.api.services.UserService;

import static com.api.constants.Role.*;

import static com.api.utils.SpecUtil.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

import static io.restassured.RestAssured.*;

public class UserDetailsAPITest {
	private UserService userService;
	
	@BeforeMethod(description="Intializing the userdetails services")
	public void setup() {
		userService = new UserService();
	}
	
	@Test (description="Verify if the Userdetails API response is shown correctly", groups= {"api","regression","smoke"})
	public void userDetailsAPIRequest() throws IOException{
		
		userService.userDetauils(FD)
		.then()
			.spec(responsSpec_OK())
		.and()
			.body(matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"));
			
	}
}
