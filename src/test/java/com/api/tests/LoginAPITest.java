package com.api.tests;

import static io.restassured.RestAssured.*;

import static io.restassured.http.ContentType.*;

import static org.hamcrest.Matchers.*;

import java.io.File;

import org.testng.annotations.Test;

import com.api.pojo.UserCredentials;

import io.restassured.module.jsv.JsonSchemaValidator;


public class LoginAPITest {
	
	@Test
	public void loginAPITest() {
		
	UserCredentials usercredentials = new UserCredentials("iamfd", "password");	
		given()
			.baseUri("http://64.227.160.186:9000/v1")
		.and()
			.contentType(JSON)
		.and()
			.accept(JSON)
		.and()
			.body(usercredentials)
			.log().uri()
			.log().method()
			.log().headers()
			.log().body()
		.when()
			.post("login")
		.then()
			.log().all()
			.statusCode(200)
			.time(lessThan(9000L))
			.and()
			.body("message", equalTo("Success"))
			.and()
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/LoginSchema.json"));
			
	}
}
