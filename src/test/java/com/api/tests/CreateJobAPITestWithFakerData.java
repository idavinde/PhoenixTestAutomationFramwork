package com.api.tests;

import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
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
import com.api.utils.FakerDataGenerator;
import com.database.dao.CustomerAddressDao;
import com.database.dao.CustomerDao;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;

import static com.api.utils.DateTimeUtil.*;
import static com.api.utils.SpecUtil.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

import static io.restassured.RestAssured.*;

public class CreateJobAPITestWithFakerData {
	
	private JobService jobService;
	private CreateJobPayload createJobPayload;

	@BeforeMethod(description = "Creating creatjob api request payload and Instantiating the job Service")
	public void setup() {

		createJobPayload = FakerDataGenerator.generateFakeCreateJobData();
		jobService = new JobService();

	}

	@Test(description = "Verify if the createJob API response is able to create Unwarrranty job ", groups = { "api",
			"regression", "smoke" })
	public void createJobAPITEst() {

		int customerId = jobService.createJob(Role.FD, createJobPayload).then()
				.spec(responsSpec_OK())
				.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
				.body("message", equalTo("Job created successfully. ")).body("data.mst_service_location_id", equalTo(1))
				.body("data.job_number", startsWith("JOB_")).extract().body().jsonPath().getInt("data.tr_customer_id");
		Customer expectedCustomerData = createJobPayload.customer();

		CustomerDBModel actualCustomerDataInDB = CustomerDao.getCustomerInfo(customerId);
		
		Assert.assertEquals(actualCustomerDataInDB.getFirst_name(), expectedCustomerData.first_name());
		Assert.assertEquals(actualCustomerDataInDB.getLast_name(), expectedCustomerData.last_name());
		Assert.assertEquals(actualCustomerDataInDB.getMobile_number(), expectedCustomerData.mobile_number());
		Assert.assertEquals(actualCustomerDataInDB.getMobile_number_alt(), expectedCustomerData.mobile_number_alt());
		Assert.assertEquals(actualCustomerDataInDB.getEmail_id(), expectedCustomerData.email_id());
		Assert.assertEquals(actualCustomerDataInDB.getEmail_id_alt(), expectedCustomerData.email_id_alt());
		
CustomerAddressDBModel customerAddressfromDB = CustomerAddressDao.getCustomerAddressData(actualCustomerDataInDB.getTr_customer_address_id());
		
		Assert.assertEquals(customerAddressfromDB.getFlat_number(), createJobPayload.customer_address().flat_number());
		Assert.assertEquals(customerAddressfromDB.getApartment_name(), createJobPayload.customer_address().apartment_name());
		Assert.assertEquals(customerAddressfromDB.getArea(), createJobPayload.customer_address().area());
		Assert.assertEquals(customerAddressfromDB.getLandmark(), createJobPayload.customer_address().landmark());
		Assert.assertEquals(customerAddressfromDB.getState(), createJobPayload.customer_address().state());
		Assert.assertEquals(customerAddressfromDB.getStreet_name(), createJobPayload.customer_address().street_name());
		Assert.assertEquals(customerAddressfromDB.getCountry(), createJobPayload.customer_address().country());
		Assert.assertEquals(customerAddressfromDB.getPincode(), createJobPayload.customer_address().pincode());
	}
}
