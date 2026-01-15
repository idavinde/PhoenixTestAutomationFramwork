package com.api.tests;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.api.constants.Role.*;
import com.api.request.model.Detail;
import com.api.services.DashboardService;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

import static com.api.utils.SpecUtil.*;
@Listeners(com.listener.APITestListerner.class)
@Epic("Job Managment")
@Feature("Job Details")
public class DetailsAPITest {
	
	private DashboardService dashboardService;
	private Detail detailPayload;
	
	
	@BeforeMethod(description="Instantiating the Dashboard Service and creating detail payload")
	public void SetUp() {
		dashboardService = new DashboardService();
		detailPayload = new Detail("created_today");
	}
	
	@Story("JobDetails is shown correctly for FD")
	@Description("Verify if Details API is working properly")
	@Severity(SeverityLevel.CRITICAL)
	@Test(description="Verify if Details API is working properly", groups= {"api","smoke", "e2e"})
	public void detailAPITest() {
		
		dashboardService.details(FD, detailPayload)
		.then()
		.spec(responsSpec_OK())
		.body("message", equalTo("Success"));
		
	}
}
