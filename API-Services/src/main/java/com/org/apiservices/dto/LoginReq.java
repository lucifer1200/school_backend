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
public class LoginReq {
	@NotEmpty(message = "Service name should not be empty")
	public String serviceName;
	private String appId;
	private String appVersion;
	private String mobNumber;
	private String pin;
	private String userName;
	private String otp;
	private String roles;
	
}
