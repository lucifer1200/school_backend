package com.org.apiservices.service;



import com.org.apiservices.dto.CommonResponse;
import com.org.apiservices.dto.ShortListReq;
import com.org.apiservices.dto.ShortListResponse;


public interface ShortListSchoolService {

	ShortListResponse FetchShortedSchools(ShortListReq reqObj);
	
	CommonResponse InsertShortedSchools(ShortListReq reqObj);
	
	CommonResponse RemoveSchoolFromList(ShortListReq reqObj);
	
}
