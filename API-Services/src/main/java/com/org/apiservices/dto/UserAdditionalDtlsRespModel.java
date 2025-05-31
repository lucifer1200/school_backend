package com.org.apiservices.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAdditionalDtlsRespModel {

	private String serviceName;
	private String userId;
	private String qualification;
	private String addmissionCategory;
	private String userCat;
	private String occupation;
	private String annualIncome;
	private String sessionPeriod;
	private String state;
	private String city;
	private String locality;
	private String landmark;
	private String dob;
	private String createdOn;

}
