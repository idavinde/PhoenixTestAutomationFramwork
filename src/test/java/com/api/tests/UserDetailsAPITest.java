package com.api.tests;


import java.io.IOException;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


import com.api.services.UserService;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

import static com.api.constants.Role.*;

import static com.api.utils.SpecUtil.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;


@Listeners(com.listener.APITestListerner.class)
@Epic("User Managment")
@Feature("User Details")
public class UserDetailsAPITest {
	private UserService userService;
	
	@BeforeMethod(description="Intializing the userdetails services")
	public void setup() {
		userService = new UserService();
	}
	@Story("UserDetails should be shown")
	@Description("Verify if the Userdetails API response is shown correctly")
	@Severity(SeverityLevel.CRITICAL)
	@Test (description="Verify if the Userdetails API response is shown correctly", groups= {"api","regression","smoke"})
	public void userDetailsAPIRequest() throws IOException{
		
		userService.userDetauils(FD)
		.then()
			.spec(responsSpec_OK())
		.and()
			.body(matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"));
			
	}
}
