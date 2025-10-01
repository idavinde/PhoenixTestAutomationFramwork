package com.api.tests;

import static io.restassured.RestAssured.*;


import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import static com.api.constants.Role.*;
import com.api.utils.SpecUtil;


import io.restassured.module.jsv.JsonSchemaValidator;

@Test
public class CountAPITest {

	public void verifyCountAPIResponse() {
			
		given()
			.spec(SpecUtil.requestSpecWithAuth(FD))
			.when()
			.get("/dashboard/count")
			.then()
			.spec(SpecUtil.responsSpec_OK())
			.body("message", equalTo("Success"))
			.body("data", notNullValue())
			.body("data.size()", equalTo(3))  
			.body("data.count", everyItem(greaterThanOrEqualTo(0)))
			.body("data.label", everyItem(not(blankOrNullString())))
			.body("data.key", containsInAnyOrder("pending_for_delivery","created_today","pending_fst_assignment"))
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/CountAPIResponseSchema.json"));
		}
	
	@Test
	public void countAPI_MisiingAuthToken() {
		
		given()
			.spec(SpecUtil.requestSpec())
			.when()
			.get("/dashboard/count")
			.then()
			.spec(SpecUtil.responsSpec_TEXT(401));
	}
	
	
}
