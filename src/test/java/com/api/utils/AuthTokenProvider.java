package com.api.utils;

import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.api.constants.Role.*;

import com.api.constants.Role;
import com.api.request.model.UserCredentials;

import static org.hamcrest.Matchers.*;

import io.restassured.http.ContentType;

public class AuthTokenProvider {
	private static Map<Role, String> tokenCache = new ConcurrentHashMap<Role ,String>();
	private static final Logger LOGGER = LogManager.getLogger(AuthTokenProvider.class);
			
	private AuthTokenProvider() {
		
	}
	
	
	public static String getToken(Role role) {
		
		LOGGER.info("Checking if the token for {} is present in the cache",  role);
		if(tokenCache.containsKey(role)) {
			
			LOGGER.info("token found to making the login request for the role {}",  role);
			return tokenCache.get(role);
		}
		
		LOGGER.info("token not found making the login request for the role {}",  role);
		UserCredentials userCredentials = null;
		
		
		if(role== FD) {
			userCredentials = new UserCredentials("iamfd", "password");
		}
		
		else if(role== SUP) {
			userCredentials = new UserCredentials("iamsup", "password");
		}
		
		else  if(role== ENG) {
			userCredentials = new UserCredentials("iameng", "password");
		}
		else  if(role== QC) {
			userCredentials = new UserCredentials("iamqc", "password");
		}
		
		String token= 	given()
					.baseUri(ConfigManager.getProperty("BASE_URI"))
					.and()
					.contentType(ContentType.JSON)
					.body(userCredentials)
					.when()
					.post("login")
					.then()
					.log().ifValidationFails()
					.statusCode(200)
					.body("message", equalTo("Success"))
					.extract().body()
					.jsonPath()
					.getString("data.token");
		
		LOGGER.info("token cached for the future request");
		tokenCache.put(role, token);
		
			return token;
					
	}

}
