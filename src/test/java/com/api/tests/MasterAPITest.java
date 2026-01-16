package com.api.tests;

import static org.hamcrest.Matchers.*;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.services.MasterService;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

import static com.api.constants.Role.*;
import static com.api.utils.SpecUtil.*;


import static io.restassured.module.jsv.JsonSchemaValidator.*;

import static io.restassured.RestAssured.*;
@Listeners(com.listener.APITestListerner.class)
@Epic("Job Managment")
@Feature("Master API")
public class MasterAPITest {
	private MasterService masterService;
	
	@BeforeMethod(description="Intentiating the master service object")
	public void setUp() {
		
		masterService = new MasterService();
	}
	
	
	@Story("Master API Should bring OEM details, problem typr, warranty status")
	@Description("Verify if the master API is giving correct response")
	@Severity(SeverityLevel.BLOCKER)
	@Test (description="Verify if the master API is giving correct response", groups= {"api","regression","smoke"})
	public void verifyMasterAPIResponse() {
		
		masterService.master(FD)
			.then()
			.spec(responsSpec_OK())
			.body("message", equalTo("Success"))
			.body("data", notNullValue())
			.body("data", hasKey("mst_oem"))
			.body("$", hasKey("message"))
			.body("$", hasKey("data"))
			.body("data.mst_oem.size()", equalTo(2))
			.body("data.mst_model.size()", greaterThan(0))
			.body("data.mst_oem.id", everyItem(notNullValue()))
			.body("data.mst_oem.name", everyItem(notNullValue()))
			.body(matchesJsonSchemaInClasspath("response-schema/MasterAPIResponseSchema"));
		
	}
	
	@Test (description="Verify if the master API is giving correct status code for invalid token", groups= {"api","neagtive","regression","smoke"})
	public void invalidTokenMasterAPI() {
		
		given()
		.spec(requestSpec())
		.when()
		.post("master")
		.then()
		.spec(responsSpec_TEXT(401));
	}

}
