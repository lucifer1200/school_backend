package com.org.apiservices.service;

import com.org.apiservices.dto.CommonResponse;
import com.org.apiservices.dto.RegistrationReq;
import com.org.apiservices.dto.UserAdditionalDetailsReq;
import com.org.apiservices.dto.UserAdditionalDetailsResp;

public interface UserDetailsService {

	CommonResponse AddUserDetailsAdditional(UserAdditionalDetailsReq reqObj);

	UserAdditionalDetailsResp GetUserDetails(UserAdditionalDetailsReq reqObj);

	CommonResponse UpdateUserDetails(RegistrationReq reqObj);

}
