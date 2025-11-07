package com.api.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;

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

	private FakerDataGenerator() {

	}

	public static CreateJobPayload generateFakeCreateJobData() {

		Customer customer = generateFakeCustomerData();
		CustomerAddress customerAddress = generateFakeCustomerAddressData();
		CustomerProduct customerProduct = generateFakeCustomerProductData();
		List<Problems> problemList = generateFakeProblemsList();
		CreateJobPayload payload = new CreateJobPayload(MST_SERVICE_LOCATION_ID, MST_PLAFORM_ID, MST_WARRANTY_STATUS_ID,
				MST_OEM_ID, customer, customerAddress, customerProduct, problemList);
		return payload;
	}

	public static Iterator<CreateJobPayload> generateFakeCreateJobData(int count) {
		
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

		String fakeRemark = faker.lorem().sentence(5);
		int problemId = RANDOM.nextInt(27) + 1;

		Problems problems = new Problems(problemId, fakeRemark);

		List<Problems> problemList = new ArrayList<Problems>();
		problemList.add(problems);

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
