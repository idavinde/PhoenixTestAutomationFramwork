package com.api.tests;

import static io.restassured.RestAssured.*;


import static org.hamcrest.Matchers.*;

import java.io.IOException;

import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;
import com.api.utils.SpecUtil;


import io.restassured.module.jsv.JsonSchemaValidator;


public class LoginAPITest {
	
	@Test
	public void loginAPITest() throws IOException {
	
	UserCredentials usercredentials = new UserCredentials("iamfd", "password");	
		given()
			.spec(SpecUtil.requestSpec(usercredentials))
		.when()
			.post("login")
		.then()
			.spec(SpecUtil.responsSpec_OK())
			.body("message", equalTo("Success"))
			.and()
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/LoginSchema.json"));
			
	}
}
