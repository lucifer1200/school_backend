package com.org.apiservices.service;


import com.org.apiservices.dto.MasterResponse;
import com.org.apiservices.dto.MasterReq;


public interface CommonMasterService {

	MasterResponse getMasterData(MasterReq masterReq);

}
