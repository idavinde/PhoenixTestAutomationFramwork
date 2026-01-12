package com.api.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.github.javafaker.Faker;

public class FakerDataGenerator {
	
	private static final Faker faker = new Faker(new Locale("en-IND"));
	private static final String COUNTRY = "India";
	private static final Random RANDOM = new Random();
	private static final int MST_SERVICE_LOCATION_ID = 0;
	private static final int MST_PLAFORM_ID = 2;
	private static final int MST_WARRANTY_STATUS_ID = 1;
	private static final int MST_OEM_ID = 1;
	private static final int PRODUCT_ID = 1;
	private static final int MST_MODEL_ID = 1;
	private static final int [] VALID_PROBLEM_ID = {1,2,3,4,5,6,7,8,9,10,11,12,15,16,17,19,20,22,24,26,27,28,29};
	private static final Logger LOGGER = LogManager.getLogger(FakerDataGenerator.class);
	private FakerDataGenerator() {

	}

	public static CreateJobPayload generateFakeCreateJobData() {
         LOGGER.info("Generating the fake payload for create job");
		Customer customer = generateFakeCustomerData();
		CustomerAddress customerAddress = generateFakeCustomerAddressData();
		CustomerProduct customerProduct = generateFakeCustomerProductData();
		List<Problems> problemList = generateFakeProblemsList();
		CreateJobPayload payload = new CreateJobPayload(MST_SERVICE_LOCATION_ID, MST_PLAFORM_ID, MST_WARRANTY_STATUS_ID,
				MST_OEM_ID, customer, customerAddress, customerProduct, problemList);
		return payload;
	}

	public static Iterator<CreateJobPayload> generateFakeCreateJobData(int count) {
		
		LOGGER.info("Generating the fake {} payload for create job" , count);
		List<CreateJobPayload> payloadList = new ArrayList<>();
		
		
		for (int i = 0; i < count; i++) {
			Customer customer = generateFakeCustomerData();
			CustomerAddress customerAddress = generateFakeCustomerAddressData();
			CustomerProduct customerProduct = generateFakeCustomerProductData();
			List<Problems> problemList = generateFakeProblemsList();
			CreateJobPayload payload = new CreateJobPayload(MST_SERVICE_LOCATION_ID, MST_PLAFORM_ID,
					MST_WARRANTY_STATUS_ID, MST_OEM_ID, customer, customerAddress, customerProduct, problemList);
			payloadList.add(payload);
		}
		return payloadList.iterator();
	}

	private static List<Problems> generateFakeProblemsList() {
		
		int count = RANDOM.nextInt(3)+1;
		String fakeRemark;
		int randomIndex;
		int problemId;
		Problems problems;
		List<Problems> problemList = new ArrayList<Problems>();
		for(int i=1;i<=count;i++) {
		 fakeRemark = faker.lorem().sentence(5);
		 randomIndex= RANDOM.nextInt(VALID_PROBLEM_ID.length);
		 problemId = VALID_PROBLEM_ID[randomIndex];

		 problems = new Problems(problemId, fakeRemark);
		 problemList.add(problems);
		}
		return problemList;
	}

	private static CustomerProduct generateFakeCustomerProductData() {

		String dop = DateTimeUtil.getTimeWithDaysAgo(0);
		String imeiSerialNumber = faker.numerify("###############");
		String popUrl = faker.internet().url();

		CustomerProduct customerProduct = new CustomerProduct(dop, imeiSerialNumber, imeiSerialNumber, imeiSerialNumber,
				popUrl, PRODUCT_ID, MST_MODEL_ID);

		return customerProduct;
	}

	private static CustomerAddress generateFakeCustomerAddressData() {
		String flatNumber = faker.numerify("###");
		String apartmentName = faker.address().streetName();
		String streetName = faker.address().streetName();
		String landmark = faker.address().streetName();
		String area = faker.address().streetName();
		String pincode = faker.numerify("#####");

		String state = faker.address().state();

		CustomerAddress customerAddress = new CustomerAddress(flatNumber, apartmentName, streetName, landmark, area,
				pincode, COUNTRY, state);

		return customerAddress;
	}

	private static Customer generateFakeCustomerData() {

		String firstName = faker.name().firstName();
		String lastName = faker.name().lastName();
		String mobileNumber = faker.numerify("##########");
		String alternateMobileNumber = faker.numerify("##########");
		String customerEmailAddres = faker.internet().emailAddress();
		String alternativecustomerEmailAddres = faker.internet().emailAddress();
		Customer customer = new Customer(firstName, lastName, mobileNumber, alternateMobileNumber, customerEmailAddres,
				alternativecustomerEmailAddres);

		return customer;
	}
}
