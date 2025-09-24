package com.api.tests;

import static org.hamcrest.Matchers. *;

import java.io.IOException;

import org.testng.annotations.Test;

import static com.api.utils.AuthTokenProvider.*;

import static com.api.constants.Role.*;
import com.api.utils.ConfigManager;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;

import static io.restassured.RestAssured.*;

public class UserDetailsAPITest {
	
	@Test
	public void userDetailsAPIRequest() throws IOException{
		Header header = new Header("Authorization" , getToken(SUP));
		
		given()
			.baseUri(ConfigManager.getProperty("BASE_URI"))
		.and()
			.header(header)
		.and()
			.accept(ContentType.JSON)
			.log().uri()
			.log().headers()
			.log().method()
			.log().body()
		.when()
			.get("userdetails")
		.then()
			.log().all()
			.statusCode(200)
			.time(lessThan(10000L))
		.and()
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"));
			
	}
}
