package com.api.tests;

import static io.restassured.RestAssured.*;

import static io.restassured.http.ContentType.*;

import static org.hamcrest.Matchers.*;

import java.io.IOException;

import org.testng.annotations.Test;

import com.api.pojo.UserCredentials;
import static com.api.utils.ConfigManager.*;

import io.restassured.module.jsv.JsonSchemaValidator;


public class LoginAPITest {
	
	@Test
	public void loginAPITest() throws IOException {
	
	UserCredentials usercredentials = new UserCredentials("iamfd", "password");	
		given()
			.baseUri(getProperty("BASE_URI"))
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
			.time(lessThan(10000L))
			.and()
			.body("message", equalTo("Success"))
			.and()
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/LoginSchema.json"));
			
	}
}
