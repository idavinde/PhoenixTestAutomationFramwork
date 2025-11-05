package com.api.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.github.javafaker.Faker;

public class FakerDemo {
	private static final String COUNTRY=  "India";
	
	public static void main(String[] args) {
		//I want to create a Fake customer Object!!
		//I want create faker Customer Object
		
		Faker faker = new Faker(new Locale("en-IND"));
		String firstName = faker.name().firstName();
		String lastName = faker.name().lastName();
		String mobileNumber = faker.numerify("##########");
		String alternateMobileNumber = faker.numerify("##########");
		String customerEmailAddres = faker.internet().emailAddress();
		String alternativecustomerEmailAddres = faker.internet().emailAddress();
		Customer customer = new Customer(firstName, lastName, mobileNumber, alternateMobileNumber, customerEmailAddres, alternativecustomerEmailAddres);
	System.out.println(customer);
	
	
	
	
		String flatNumber =	faker.numerify("###");
		String apartmentName = faker.address().streetName();
		String streetName = faker.address().streetName();
		String landmark = faker.address().streetName();
		String area = faker.address().streetName();
		String pincode = faker.numerify("#####");
		
		String state = faker.address().state();
		
		CustomerAddress customerAddress = new CustomerAddress(flatNumber, apartmentName, streetName, landmark, area, pincode, COUNTRY, state);
		System.out.println(customerAddress);
		
		String dop  = DateTimeUtil.getTimeWithDaysAgo(0);
		String imeiSerialNumber = faker.numerify("###############");
		String popUrl = faker.internet().url();
		
		CustomerProduct customerProduct = new CustomerProduct(dop, imeiSerialNumber, imeiSerialNumber, imeiSerialNumber, popUrl, 0, 0);
		System.out.println(customerProduct);
		
		String fakeRemark = faker.lorem().sentence(5);
		Random random = new Random();
		int problemId = random.nextInt(27)+1;
		
		Problems problems = new Problems(problemId, fakeRemark);
		System.out.println(problems);
		
		List<Problems> problemList = new ArrayList<Problems>();
		problemList.add(problems);
		
		
		CreateJobPayload payload = new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct, problemList);
		
		System.out.println(payload);
	}
}
