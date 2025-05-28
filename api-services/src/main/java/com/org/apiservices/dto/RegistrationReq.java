package com.org.apiservices.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationReq {
	@NotEmpty(message = "Service name should not be empty")
	public String serviceName;

	private String email;
	private String mobNumber;
	private String firstName;
	private String lastName;
	private String pin;
	private String appId;
	private String userPic;
	private String userDob;
	private String userAppVersion;
	private String maker;
	private String checker;
	private String userId;
	private String gender;

}
