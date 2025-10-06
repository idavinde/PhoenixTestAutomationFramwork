package com.api.tests;

import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.api.constants.Role;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import static com.api.utils.DateTimeUtil.*;
import com.api.utils.SpecUtil;

import io.restassured.module.jsv.JsonSchemaValidator;

import static io.restassured.RestAssured.*;

public class CreateJobAPITest {
	
	@Test
	public void createJobAPITEst() {
		// Create job payload Object 
		
		
		Customer customer = new Customer("Jatin", "Sharma", "7293847564", "", "jatin@gmail.com", "");
		CustomerAddress customerAddress = new CustomerAddress("D404", "Vasant galaxy", "Bangur", "Inorbit", "Mumbai", "411039", "INDIA", "MAHARASHTRA");
		
		CustomerProduct customerProduct = new CustomerProduct(getTimeWithDaysAgo(10), "19771422154332", "19771422154332", "19771422154332", getTimeWithDaysAgo(10), 1, 1);
		Problems problems = new Problems(1,"Battery Issue");
		List<Problems> problemList = new ArrayList<Problems>();
		problemList.add(problems);
		
		CreateJobPayload createJobPayload = new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct, problemList);
		
		given()
			.spec(SpecUtil.requestSpecWithAuth(Role.FD, createJobPayload))
			.when()
			.post("/job/create")
			.then()
			.spec(SpecUtil.responsSpec_OK())
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
			.body("message", equalTo("Job created successfully. "))
			.body("data.mst_service_location_id", equalTo(1))
			.body("data.job_number", startsWith("JOB_"));
	}
}
