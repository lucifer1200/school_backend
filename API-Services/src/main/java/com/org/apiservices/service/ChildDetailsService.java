package com.org.apiservices.service;



import com.org.apiservices.dto.ChildReq;
import com.org.apiservices.dto.ChildResponse;


public interface ChildDetailsService {

	ChildResponse ChildRegistration(ChildReq reqObj);
	
	ChildResponse FetchChildDetails(ChildReq reqObj);
	
	//CommonResponse InsertShortedSchools(ShortListReq reqObj);
	
	
	
}
