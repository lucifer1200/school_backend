package com.org.apiservices.service;

import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.org.apiservices.constants.MessageConstants;
import com.org.apiservices.dto.MasterReq;
import com.org.apiservices.dto.MasterResponse;
import com.org.apiservices.dto.MasterResponseModel;

@Service
@Slf4j
public class CommonMasterServiceImpl implements CommonMasterService {

	private final JdbcTemplate jdbcTemplate;

	public CommonMasterServiceImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		new MessageConstants();
	}

	@Override
	public MasterResponse getMasterData(MasterReq masterReq) {

		MasterResponse masterResponse = new MasterResponse();
		List<MasterResponseModel> masterDetailsListTemp = new ArrayList<>();

		String query;
		if ("AllMasterData".equals(masterReq.serviceName)) {
			query = "select * from tb_common_master";
			System.out.println("query----" + query);
		} else {
			query = "select * from tb_common_master where PROP_NAME= '" + masterReq.serviceName + "'";
			System.out.println("query----" + query);
		}

		try {
			List<Map<String, Object>> masterData = jdbcTemplate.queryForList(query);
			masterResponse.setStatusCode(MessageConstants.SUCCESS_STATUSCODE);
			masterResponse.setStatusDesc(MessageConstants.SUCCESS);
			masterResponse.setMessage(MessageConstants.REC_SUCCESS_MSG);

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			for (Map<String, Object> row : masterData) {
				MasterResponseModel tempResponse = new MasterResponseModel();
				tempResponse.setPropetyName((String) row.get("PROP_NAME"));
				tempResponse.setPropVal((String) row.get("PROP_VALUE"));
				tempResponse.setPropDesc((String) row.get("PROP_DESC"));

				Timestamp timestamp = (Timestamp) row.get("DATE_OF_UPDATE");
				if (timestamp != null) {
					tempResponse.setPropUpdateDate(dateFormat.format(timestamp));
				} else {
					tempResponse.setPropUpdateDate(null);
				}

				masterDetailsListTemp.add(tempResponse);
			}
			masterResponse.setMasterDetailsList(masterDetailsListTemp);

		} catch (Exception e) {
			masterResponse.setStatusCode(MessageConstants.TECH_ERROR_STATUSCODE);
			masterResponse.setStatusDesc(MessageConstants.ERROR);
			masterResponse.setTechErrorMsg(e.toString());
		}

		return masterResponse;
	}
}