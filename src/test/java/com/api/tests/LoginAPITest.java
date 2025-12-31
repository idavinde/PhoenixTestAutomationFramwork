package com.api.tests;

import static io.restassured.RestAssured.*;


import static org.hamcrest.Matchers.*;

import java.io.IOException;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;
import com.api.services.AuthService;

import static com.api.utils.SpecUtil.*;


import static io.restassured.module.jsv.JsonSchemaValidator.*;


public class LoginAPITest {

	private UserCredentials usercredentials;
	private AuthService authService;

	@BeforeMethod(description = "Create the Payload for the Login API")
	public void setup() {

		usercredentials = new UserCredentials("iamfd", "password");
		authService = new AuthService();
	}

	@Test(description = "Verify if login is working for user iamfd", groups = { "api", "regression", "smoke" })
	public void loginAPITest() throws IOException {
	
	
			authService.login(usercredentials)
			.then()
			.spec(responsSpec_OK())
			.body("message", equalTo("Success"))
			.and()
			.body(matchesJsonSchemaInClasspath("response-schema/LoginSchema.json"));
			
	}
}
