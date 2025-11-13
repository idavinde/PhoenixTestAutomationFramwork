package com.api.tests.datadriven;

import static io.restassured.RestAssured.*;


import static org.hamcrest.Matchers.*;

import java.io.IOException;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;
import com.dataproviders.api.bean.UserBean;

import static com.api.utils.SpecUtil.*;


import static io.restassured.module.jsv.JsonSchemaValidator.*;


public class LoginAPIExcelDataDrivenTest {
	
	
	@Test(description="Verify if login is working ", 
			groups= {"api","regression","datadriven"},
			dataProviderClass = com.dataproviders.DataProviderUtils.class,
			dataProvider = "LoginAPIExcelDataProvider")
	public void loginAPITest(UserBean userBean) {
	
	
		given()
			.spec(requestSpec(userBean))
		.when()
			.post("login")
		.then()
			.spec(responsSpec_OK())
			.body("message", equalTo("Success"))
			.and()
			.body(matchesJsonSchemaInClasspath("response-schema/LoginSchema.json"));
			
	}
}
