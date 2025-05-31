package com.org.apiservices.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseModel {
	
	private String mobNumber;
	private String userId;
	private String email;
	private String firstName;
	private String lastName;
	private String userPic;
	private String userMaker;
	private String userChecker;
	private String authToken;
	private String appVersion;
	private String lastLogin;
}
