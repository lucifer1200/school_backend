package com.org.apiservices.service;



import com.org.apiservices.dto.CommonResponse;
import com.org.apiservices.dto.LoginReq;
import com.org.apiservices.dto.LoginResponse;
import com.org.apiservices.dto.RegistrationReq;


public interface LoginService {

	LoginResponse validateLogin(LoginReq reqObj);
	
	CommonResponse register(RegistrationReq reqObj);
	
}
