package com.api.tests;

import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.constants.Model;
import com.api.constants.OEM;
import com.api.constants.Platform;
import com.api.constants.Problem;
import com.api.constants.Product;
import com.api.constants.Role;
import com.api.constants.Service_Location;
import com.api.constants.Warranty_Status;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.api.services.JobService;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

import static com.api.utils.DateTimeUtil.*;
import static com.api.utils.SpecUtil.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

import static io.restassured.RestAssured.*;
@Listeners(com.listener.APITestListerner.class)
@Epic("Job Managment")
@Feature("Job creation")
public class CreateJobAPITest {
	
	private JobService jobService;
	private CreateJobPayload createJobPayload;
	
	@BeforeMethod (description= "Creating creatjob api request payload and Instantiating the job Service")
	public void setup() {
		
		Customer customer = new Customer("Jatin", "Sharma", "7293847564", "", "jatin@gmail.com", "");
		CustomerAddress customerAddress = new CustomerAddress("D404", "Vasant galaxy", "Bangur", "Inorbit", "Mumbai", "411039", "INDIA", "MAHARASHTRA");
		
		CustomerProduct customerProduct = new CustomerProduct(getTimeWithDaysAgo(10), "19700432156456", "19700432156456", "19700432156456", getTimeWithDaysAgo(10), Product.Nexus_2.getCode(), Model.NEXUS_2_BLUE.getCode());
		Problems problems = new Problems(Problem.SMARTPHONE_IS_RUNNING_SLOW.getCode(),"Battery Issue");
		List<Problems> problemList = new ArrayList<Problems>();
		problemList.add(problems);
		
	   createJobPayload = new CreateJobPayload(Service_Location.SERVICE_LOCATION_A.getCode(), Platform.FRONT_DESK.getCode(), Warranty_Status.IN_WARRANTY.getCode(), OEM.GOOGLE.getCode(), customer, customerAddress, customerProduct, problemList);
		jobService = new JobService();
	}
	
	@Story("FD should be able to create job")
	@Description("Verifying if FD is able to use create job api and In wraranty job is creaated")
	@Severity(SeverityLevel.BLOCKER)
	
	@Test (description="Verifying if FD is able to use create job api and In wraranty job is creaated ", groups= {"api","regression","smoke"})
	public void createJobAPITEst() {
		
		jobService.createJob(Role.FD, createJobPayload)
			.then()
			.spec(responsSpec_OK())
			.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
			.body("message", equalTo("Job created successfully. "))
			.body("data.mst_service_location_id", equalTo(1))
			.body("data.job_number", startsWith("JOB_"));
	}
}
