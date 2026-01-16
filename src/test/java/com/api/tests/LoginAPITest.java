package com.api.tests;

import static io.restassured.RestAssured.*;


import static org.hamcrest.Matchers.*;

import java.io.IOException;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;
import com.api.services.AuthService;
import com.dataproviders.api.bean.UserBean;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

import static com.api.utils.SpecUtil.*;


import static io.restassured.module.jsv.JsonSchemaValidator.*;

@Listeners(com.listener.APITestListerner.class)
@Epic("User Managment")
@Feature("Authentication")
public class LoginAPITest {

	private UserBean usercredentials;
	private AuthService authService;

	@BeforeMethod(description = "Create the Payload for the Login API")
	public void setup() {

		usercredentials = new UserBean("iamfd", "password");
		authService = new AuthService();
	}
	@Story("Valid User should be able to login into the system")
	@Description("Verify if FD user is able to login via api")
	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Verify if login is working for user iamfd", groups = { "api", "regression", "smoke" })
	public void loginAPITest() throws IOException {
	
	
			authService.login(usercredentials)
			.then()
			.spec(responsSpec_OK())
			.body("message", equalTo("Success"))
			.and()
			.body(matchesJsonSchemaInClasspath("response-schema/LoginSchema.json"));
			
	}
}
