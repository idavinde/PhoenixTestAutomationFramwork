package com.api.tests;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.constants.Role;
import com.api.request.model.Search;
import com.api.services.JobService;
import com.api.utils.SpecUtil;

@Listeners(com.listener.APITestListerner.class)
public class SearchAPITest {

		private JobService jobService;
		private static final String JOB_NUMBER="JOB_131343";
		private Search searchPayload;
		
		@BeforeMethod(description="Instantiating the jobService and Creating the search payload")
		public void setup() {
			
			jobService = new JobService();
			searchPayload = new Search(JOB_NUMBER);
			
		}
		
		@Test(description="Verify if the search API is working properly", groups= {"e2e","smoke","api"})
		public void searchAPITest() {
			
			jobService.search(Role.FD, searchPayload)
			.then().spec(SpecUtil.responsSpec_OK())
			.body("message", Matchers.equalTo("Success"));
		}
		

}

