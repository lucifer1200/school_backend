package com.org.apiservices.service;



import com.org.apiservices.dto.SchoolResponse;
import com.org.apiservices.dto.SchoolSearchReq;


public interface FetchSchoolsService {

	SchoolResponse getAllSchools(SchoolSearchReq schoolReq);

}
