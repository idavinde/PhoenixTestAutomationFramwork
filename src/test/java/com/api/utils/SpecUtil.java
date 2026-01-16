package com.api.utils;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static com.api.utils.ConfigManager.*;

import org.hamcrest.Matchers;

import com.api.constants.Role;
import com.api.filters.SensitiveDataFilter;
import com.api.request.model.UserCredentials;

public class SpecUtil {
	@Step("Setting up the BaseURi, Content Type as Application/JSON and attaching the Sensitive Data Filter")
	public static RequestSpecification requestSpec() {
		//To take care of common request sections (methods)
		
		RequestSpecification requestSpecification = new RequestSpecBuilder()
		.setBaseUri(getProperty("BASE_URI"))
		.setContentType(ContentType.JSON)
		.setAccept(ContentType.JSON)
		.addFilter(new SensitiveDataFilter())
		.addFilter(new AllureRestAssured())
		.build();
		
		return requestSpecification;
		
		
	}
	@Step("Setting up the BaseURi, Content Type as Application/JSON and attaching the Sensitive Data Filter")
	public static RequestSpecification requestSpec(Object payload) {
		//To take care of common request sections (methods)
		
		RequestSpecification requestSpecification = new RequestSpecBuilder()
		.setBaseUri(getProperty("BASE_URI"))
		.setContentType(ContentType.JSON)
		.setAccept(ContentType.JSON)
		.setBody(payload)
		.addFilter(new SensitiveDataFilter())
		.addFilter(new AllureRestAssured())
		.build();
		
		return requestSpecification;
		
		
	}
	
	@Step("Expecting the response to have Content Type as Application/JSON, Statuc 200 and Response time is less than 2000 ms")
	public static ResponseSpecification responsSpec_OK() {
		
		ResponseSpecification responseSpecification =new ResponseSpecBuilder()
		.expectContentType(ContentType.JSON)
		.expectStatusCode(200)
		.expectResponseTime(Matchers.lessThan(2000L))
		
		.build();
		
		return responseSpecification;
	}
	
	@Step("Setting up the BaseURi, Content Type as Application/JSON and attaching the Sensitive Data Filter for role")
	public static RequestSpecification requestSpecWithAuth(Role role) {
		//To take care of common request sections (methods)
		
		RequestSpecification requestSpecification = new RequestSpecBuilder()
		.setBaseUri(getProperty("BASE_URI"))
		.setContentType(ContentType.JSON)
		.setAccept(ContentType.JSON)
		.addHeader("Authorization", AuthTokenProvider.getToken(role))
		.addFilter(new SensitiveDataFilter())
		.addFilter(new AllureRestAssured())
		.build();
		
		return requestSpecification;
		
		
	}
	
	@Step("Setting up the BaseURi, Content Type as Application/JSON and attaching the Sensitive Data Filter for role and attaching payload")
	public static RequestSpecification requestSpecWithAuth(Role role, Object Payload ) {
		//To take care of common request sections (methods)
		
		RequestSpecification requestSpecification = new RequestSpecBuilder()
		.setBaseUri(getProperty("BASE_URI"))
		.setContentType(ContentType.JSON)
		.setAccept(ContentType.JSON)
		.addHeader("Authorization", AuthTokenProvider.getToken(role))
		.setBody(Payload)
		.addFilter(new SensitiveDataFilter())
		.addFilter(new AllureRestAssured())
		.build();
		
		return requestSpecification;
		
		
	}
	
	@Step("Expecting the response to have Content Type as Text, Statuc 200 and Response time is less than 2000 ms and status code")
public static ResponseSpecification responsSpec_TEXT(int statusCode) {
		
		ResponseSpecification responseSpecification =new ResponseSpecBuilder()
		.expectStatusCode(statusCode)
		.expectResponseTime(Matchers.lessThan(2000L))
		
		.build();
		
		return responseSpecification;
	}
	
@Step("Expecting the response to have Content Type as Application/JSON, Statuc 200 and Response time is less than 2000 ms and status code")
public static ResponseSpecification responsSpec_JSON(int statusCode) {
		
		ResponseSpecification responseSpecification =new ResponseSpecBuilder()
		.expectContentType(ContentType.JSON)
		.expectStatusCode(statusCode)
		.expectResponseTime(Matchers.lessThan(2000L))
		.build();
		
		return responseSpecification;
	}

}
