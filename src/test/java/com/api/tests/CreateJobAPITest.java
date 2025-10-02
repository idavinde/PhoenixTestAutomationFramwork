package com.api.tests;

import org.testng.annotations.Test;

import com.api.constants.Role;
import com.api.pojo.CreateJobPayload;
import com.api.pojo.Customer;
import com.api.pojo.CustomerAddress;
import com.api.pojo.CustomerProduct;
import com.api.pojo.Problems;
import com.api.utils.AuthTokenProvider;
import com.api.utils.ConfigManager;
import com.api.utils.SpecUtil;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;

public class CreateJobAPITest {
	
	@Test
	public void createJobAPITEst() {
		// Create job payload Object 
		
		
		Customer customer = new Customer("Jatin", "Sharma", "7293847564", "", "jatin@gmail.com", "");
		CustomerAddress customerAddress = new CustomerAddress("D404", "Vasant galaxy", "Bangur", "Inorbit", "Mumbai", "411039", "INDIA", "MAHARASHTRA");
		
		CustomerProduct customerProduct = new CustomerProduct("2025-08-05T07:00:00.000Z", "19771332084332", "19771332084332", "19771332084332", "2025-08-05T07:00:00.000Z", 1, 1);
		Problems problems = new Problems(1,"Battery Issue");
		Problems[] problemsArray = new Problems[1];
		problemsArray[0]=problems;
		
		CreateJobPayload createJobPayload = new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct, problemsArray);
		
		given()
			.spec(SpecUtil.requestSpecWithAuth(Role.FD, createJobPayload))
			.when()
			.post("/job/create")
			.then()
			.spec(SpecUtil.responsSpec_OK());
	}
}
