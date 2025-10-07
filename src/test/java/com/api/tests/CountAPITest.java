package com.api.tests;

import static io.restassured.RestAssured.*;


import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import static com.api.constants.Role.*;
import static com.api.utils.SpecUtil.*;


import static io.restassured.module.jsv.JsonSchemaValidator.*;

@Test (description="Verify if the count API response is shown correctly", groups= {"api","regression","smoke"})
public class CountAPITest {

	public void verifyCountAPIResponse() {
			
		given()
			.spec(requestSpecWithAuth(FD))
			.when()
			.get("/dashboard/count")
			.then()
			.spec(responsSpec_OK())
			.body("message", equalTo("Success"))
			.body("data", notNullValue())
			.body("data.size()", equalTo(3))  
			.body("data.count", everyItem(greaterThanOrEqualTo(0)))
			.body("data.label", everyItem(not(blankOrNullString())))
			.body("data.key", containsInAnyOrder("pending_for_delivery","created_today","pending_fst_assignment"))
			.body(matchesJsonSchemaInClasspath("response-schema/CountAPIResponseSchema.json"));
		}
	
	@Test (description="Verify if the count API response is shown correctly", groups= {"api","neagtive","regression","smoke"})
	public void countAPI_MisiingAuthToken() {
		
		given()
			.spec(requestSpec())
			.when()
			.get("/dashboard/count")
			.then()
			.spec(responsSpec_TEXT(401));
	}
	
	
}
