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
import com.api.response.model.CreateJobResponseModel;
import com.database.dao.CustomerAddressDao;
import com.database.dao.CustomerDao;
import com.database.dao.CustomerProductDao;
import com.database.dao.MapjobProblemDao;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;
import com.database.model.CustomerProductDBModel;
import com.database.model.MapJobProblemModel;

import io.restassured.response.Response;

import static com.api.utils.DateTimeUtil.*;
import static com.api.utils.SpecUtil.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

import static io.restassured.RestAssured.*;

public class CreateJobAPIWithDBValidationTest2 {
	
	private CreateJobPayload createJobPayload;
	private Customer customer;
	private CustomerAddress customerAddress;
	private CustomerProduct customerProduct ;
	
	@BeforeMethod (description= "Creating creatjob api request payload")
	public void setup() {
		
		 customer = new Customer("Jatin", "Sharma", "7293847564", "", "jatin@gmail.com", "");
		 customerAddress = new CustomerAddress("D404", "Vasant galaxy", "Bangur", "Inorbit", "Mumbai", "411039", "INDIA", "MAHARASHTRA");
		
		 customerProduct = new CustomerProduct(getTimeWithDaysAgo(10), "19510430154232", "19510430154232", "19510430154232", getTimeWithDaysAgo(10), Product.Nexus_2.getCode(), Model.NEXUS_2_BLUE.getCode());
		Problems problems = new Problems(Problem.SMARTPHONE_IS_RUNNING_SLOW.getCode(),"Battery Issue");
		List<Problems> problemList = new ArrayList<Problems>();
		problemList.add(problems);
		
	   createJobPayload = new CreateJobPayload(Service_Location.SERVICE_LOCATION_A.getCode(), Platform.FRONT_DESK.getCode(), Warranty_Status.IN_WARRANTY.getCode(), OEM.GOOGLE.getCode(), customer, customerAddress, customerProduct, problemList);
		
	}
	
	
	
	@Test (description="Verify if the createJob API response is able to create Unwarrranty job ", groups= {"api","regression","smoke"})
	public void createJobAPITEst() {
		
	CreateJobResponseModel createJobResponseModel=	given()
			.spec(requestSpecWithAuth(Role.FD, createJobPayload))
			.when()
			.post("/job/create")
			.then()
			.spec(responsSpec_OK())
			.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
			.body("message", equalTo("Job created successfully. "))
			.body("data.mst_service_location_id", equalTo(1))
			.body("data.job_number", startsWith("JOB_"))
			.extract().as(CreateJobResponseModel.class);
	
		int customerId = createJobResponseModel.getData().getTr_customer_id();
		
		CustomerDBModel customerDataFromDB = CustomerDao.getCustomerInfo(customerId);
		System.out.println(customerDataFromDB);
		
		Assert.assertEquals(customer.first_name(), customerDataFromDB.getFirst_name());
		Assert.assertEquals(customer.last_name(), customerDataFromDB.getLast_name());
		Assert.assertEquals(customer.mobile_number(), customerDataFromDB.getMobile_number());
		Assert.assertEquals(customer.mobile_number_alt(), customerDataFromDB.getMobile_number_alt());
		Assert.assertEquals(customer.email_id(), customerDataFromDB.getEmail_id());
		Assert.assertEquals(customer.email_id_alt(), customerDataFromDB.getEmail_id_alt());
		
		CustomerAddressDBModel customerAddressfromDB = CustomerAddressDao.getCustomerAddressData(customerDataFromDB.getTr_customer_address_id());
		System.out.println(customerAddressfromDB);
		Assert.assertEquals(customerAddressfromDB.getFlat_number(), customerAddress.flat_number());
		Assert.assertEquals(customerAddressfromDB.getApartment_name(), customerAddress.apartment_name());
		Assert.assertEquals(customerAddressfromDB.getArea(), customerAddress.area());
		Assert.assertEquals(customerAddressfromDB.getLandmark(), customerAddress.landmark());
		Assert.assertEquals(customerAddressfromDB.getState(), customerAddress.state());
		Assert.assertEquals(customerAddressfromDB.getStreet_name(), customerAddress.street_name());
		Assert.assertEquals(customerAddressfromDB.getCountry(), customerAddress.country());
		Assert.assertEquals(customerAddressfromDB.getPincode(), customerAddress.pincode());
		
		int tr_job_head_id = createJobResponseModel.getData().getId();

		MapJobProblemModel JobDataFromDB = MapjobProblemDao.getProblemDetails(tr_job_head_id);
		Assert.assertEquals(JobDataFromDB.getMst_problem_id(), createJobPayload.problems().get(0).id());
		Assert.assertEquals(JobDataFromDB.getRemark(), createJobPayload.problems().get(0).remark());
		
		int product_Id = createJobResponseModel.getData().getTr_customer_product_id();
		
		CustomerProductDBModel customerProductDBData = CustomerProductDao.getProductInfoFromDB(product_Id);
		Assert.assertEquals(customerProductDBData.getImei1(), customerProduct.imei1());
		Assert.assertEquals(customerProductDBData.getImei2(), customerProduct.imei2());
		Assert.assertEquals(customerProductDBData.getSerial_number(), customerProduct.serial_number());
		Assert.assertEquals(customerProductDBData.getDop(), customerProduct.dop());
		Assert.assertEquals(customerProductDBData.getPopurl(), customerProduct.popurl());
		Assert.assertEquals(customerProductDBData.getMst_model_id(), customerProduct.mst_model_id());
		
		
	
		
		
	}
}
