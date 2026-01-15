package com.api.tests;

import static io.restassured.RestAssured.*;


import static org.hamcrest.Matchers.*;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.services.DashboardService;
import com.api.services.UserService;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

import static com.api.constants.Role.*;
import static com.api.utils.SpecUtil.*;


import static io.restassured.module.jsv.JsonSchemaValidator.*;


@Listeners(com.listener.APITestListerner.class)
@Epic("Job Managment")
@Feature("Job Count")
public class CountAPITest {
	
	private DashboardService dashboardService;

	@BeforeMethod(description="Intializing the userdetails services")
	public void setup() {
		dashboardService = new DashboardService();
	}
	
	@Story("Job Count Data is shown correctly for FD")
	@Description("Verify if the count API response is shown correctly")
	@Severity(SeverityLevel.CRITICAL)
	@Test (description="Verify if the count API response is shown correctly", groups= {"api","regression","smoke"})
	public void verifyCountAPIResponse() {
			
			dashboardService.count(FD)
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
		
		dashboardService.countWithNoAuth()
			.then()
			.spec(responsSpec_TEXT(401));
	}
	
	
}
