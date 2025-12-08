package com.api.response.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CreateJobResponseModel {

	private String message;
	private CreateJobDataModel data;

}
