package com.api.tests.datadriven;


import static org.hamcrest.Matchers.*;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.services.AuthService;
import com.dataproviders.api.bean.UserBean;

import static com.api.utils.SpecUtil.*;


import static io.restassured.module.jsv.JsonSchemaValidator.*;

@Listeners(com.listener.APITestListerner.class)
public class LoginAPIJSONDataDrivenTest2 {
	
private AuthService authService;
	
	@BeforeMethod(description="Intializing the auth services")
	public void setup() {
		authService = new AuthService();
	}
	
	@Test(description="Verify if login is working for user iamfd", 
			groups= {"api","regression","datadriven"},
			dataProviderClass = com.dataproviders.DataProviderUtils.class,
			dataProvider = "LoginAPIJsonDataProvider")
	public void loginAPITest(UserBean userCredentials) {
	
	
		authService.login(userCredentials)
		.then()
			.spec(responsSpec_OK())
			.body("message", equalTo("Success"))
			.and()
			.body(matchesJsonSchemaInClasspath("response-schema/LoginSchema.json"));
			
	}
}
